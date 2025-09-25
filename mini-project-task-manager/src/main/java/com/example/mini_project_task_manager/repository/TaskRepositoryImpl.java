package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.common.enums.Priority;
import com.example.mini_project_task_manager.common.enums.Status;
import com.example.mini_project_task_manager.entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskRepositoryImpl implements TaskRepositoryCustom{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Task> searchTasks(Long projectId, Status status, Priority priority, LocalDateTime from, LocalDateTime to,
                                  LocalDate dueFrom, LocalDate dueTo) {
        StringBuilder jpql = new StringBuilder(
                "SELECT t " +
                        "FROM Task t "+
                "WHERE 1 = 1 and t.project.id = :projectId"
        );

        Map<String, Object> params = new HashMap<>();

        params.put("projectId", projectId);

        if(status != null){
            jpql.append(" and t.status = :status");
            params.put("status", status);
        }

        if(priority != null){
            jpql.append(" and t.priority = :priority");
            params.put("priority", priority);
        }

//        if (author != null) {
//            jpql.append(" and lower(u.username) LIKE lower(:author)");
//            params.put("author", "%" + author + "%");
//        }

        if(from != null) {
            jpql.append(" and t.createdAt >= :from");
            params.put("from", from);
        }

        if(to != null) {
            jpql.append(" and t.createdAt <= :to");
            params.put("to", to);
        }

        if (dueFrom != null) {
            jpql.append(" and t.dueDate >= :dueFrom");
            params.put("dueFrom", dueFrom);
        }

        if (dueTo != null) {
            jpql.append(" and t.dueDate <= :dueTo");
            params.put("dueTo", dueTo);
        }

        jpql.append(" order by t.id desc");

        TypedQuery<Task> query = em.createQuery(jpql.toString(), Task.class);

        for (Map.Entry<String , Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        List<Task> results = query.getResultList();

        return results;
    }
}
