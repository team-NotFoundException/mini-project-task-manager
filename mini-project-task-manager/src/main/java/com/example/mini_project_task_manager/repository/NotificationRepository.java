package com.example.mini_project_task_manager.repository;

import com.example.mini_project_task_manager.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query(value = """
        select * from notifications
        where title like concat('%', :keyword, '%') or content like concat('%', :keyword, '%')
        order by id desc
""", nativeQuery = true)
    List<Notification> findByKeyWordContainingIgnoreCaseOrderByIdDesc(@Param("keyword") String keyword);
}
