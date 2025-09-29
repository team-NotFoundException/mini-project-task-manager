package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.common.enums.Sorted;
import com.example.mini_project_task_manager.entity.Project;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class ProjectRepositoryImpl implements ProjectRepositoryCustom {
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Project> findAllProjectsByCreatedAt(Sorted sortedBy) {

        StringBuilder jpql = new StringBuilder(
            "SELECT DISTINCT p " +
            "FROM Project p " +
                    "JOIN FETCH p.user u " +
            "WHERE 1 = 1"
        );


        if (sortedBy == Sorted.ASC) {
            jpql.append("ORDER BY p.createdAt asc");
        } else if (sortedBy == Sorted.DESC){
            jpql.append("ORDER BY p.createdAt desc");
        } else if (sortedBy == Sorted.UPDATE) {
            jpql.append("ORDER BY p.updatedAt desc");
        } else {
            jpql.append("ORDER BY p.createdAt desc");
        }

        TypedQuery<Project> query = em.createQuery(jpql.toString(), Project.class);

        List<Project> result = query.getResultList();

        return result;
    }
}
