package com.htt.ecourse.service;

public interface NotificationService {
    void sendNotificationToEnrolledUsers(Long courseId, String title, String body) throws Exception;
}
