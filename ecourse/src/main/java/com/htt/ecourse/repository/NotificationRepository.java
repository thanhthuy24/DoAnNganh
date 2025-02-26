package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findNotificationsByUserId(Long userId, Pageable pageable);
    Notification findNotificationById(Long id);
}
