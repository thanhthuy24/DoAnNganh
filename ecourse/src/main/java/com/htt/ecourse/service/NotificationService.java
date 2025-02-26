package com.htt.ecourse.service;

import com.htt.ecourse.dtos.NotificationDTO;
import com.htt.ecourse.pojo.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface NotificationService {
    void sendNotificationToEnrolledUsers(Long courseId, String title, String body) throws Exception;

    Page<Notification> findNotificationsByUserId(Pageable pageable);
    Notification createNotification(NotificationDTO notificationDTO);
//    Notification createNotification(String title, String message, Long userId);
    Notification updateNotification(Long notificationId);
}
