//package com.example.mini_project_task_manager.repository;
//
//import com.example.mini_project_task_manager.common.enums.Priority;
//import com.example.mini_project_task_manager.common.enums.Status;
//import com.example.mini_project_task_manager.dto.task.response.TaskResponse;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.TypedQuery;
//
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//public class TaskRepositoryImpl implements TaskRepositoryCustom{
//
//    @PersistenceContext
//    private EntityManager em;
//
//    @Override
//    public List<TaskResponse.TaskListResponse> searchTasks(Long projectId, Status status, Priority priority) {
//        StringBuilder jpql = new StringBuilder(
//                "SELECT DISTINCT t " +
//                        "FROM Task t "+
//                        "WHERE 1 = 1"
//        );
//
//        Map<String, Object> params = new HashMap<>();
//
//        params.put("projectId", projectId);
//
//        if(status != null){
//            jpql.append(" and t.status = :status");
//            params.put("status", status);
//        }
//
//        if(priority != null){
//            jpql.append(" and t.priority = :priority");
//            params.put("priority", priority);
//        }
//
//        jpql.append(" order by t.id desc");
//
//        TypedQuery<TaskResponse.TaskListResponse> query = em.createQuery(jpql.toString(), TaskResponse.TaskListResponse.class);
//
//        for (Map.Entry<String , Object> entry : params.entrySet()) {
//            query.setParameter(entry.getKey(), entry.getValue());
//        }
//
//        List<TaskResponse.TaskListResponse> results = query.getResultList();
//
//        return results;
//    }
//}
