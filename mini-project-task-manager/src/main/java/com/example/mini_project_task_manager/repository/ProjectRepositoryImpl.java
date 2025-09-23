package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> findAllProjectsByCreatedAt(boolean sortedBy) {

        StringBuilder jpql = new StringBuilder(
            "SELECT DISTINCT p " +
            "FROM Project p " +
                    "JOIN FETCH p.user u " +
            "WHERE 1 = 1"
        );

        Map<String, Object> params = new HashMap<>();

        if (!sortedBy) {
            jpql.append("ORDER BY p.createdAt asc");
        } else {
            jpql.append("ORDER BY p.createdAt desc");
        }

        TypedQuery<Project> query = em.createQuery(jpql.toString(), Project.class);

        for (Map.Entry<String , Object> entry : params.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        List<Project> results = query.getResultList();

        return results;
    }
}
