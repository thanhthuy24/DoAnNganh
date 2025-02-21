package com.htt.ecourse.service.impl;

import com.htt.ecourse.components.FirebaseMessagingService;
import com.htt.ecourse.pojo.Token;
import com.htt.ecourse.repository.TokenRepository;
import com.htt.ecourse.service.EnrollmentService;
import com.htt.ecourse.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final EnrollmentService enrollmentService;
    private final TokenRepository tokenRepository;
    private final FirebaseMessagingService firebaseMessagingService;

    @Override
    public void sendNotificationToEnrolledUsers(Long courseId, String title, String body) throws Exception {
        List<Long> enrolledUserIds = enrollmentService.getEnrolledUserIds(courseId);
        List<Token> tokens = tokenRepository.findByUserIdIn(enrolledUserIds);

        for (Token fcmToken : tokens) {
            firebaseMessagingService.sendNotification(fcmToken.getToken(), title, body);
        }
    }
}
