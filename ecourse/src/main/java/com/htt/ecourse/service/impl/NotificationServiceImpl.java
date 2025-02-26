package com.htt.ecourse.service.impl;

import com.htt.ecourse.components.FirebaseMessagingService;
import com.htt.ecourse.dtos.NotificationDTO;
import com.htt.ecourse.pojo.Notification;
import com.htt.ecourse.pojo.Token;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.NotificationRepository;
import com.htt.ecourse.repository.TokenRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.EnrollmentService;
import com.htt.ecourse.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final EnrollmentService enrollmentService;
    private final TokenRepository tokenRepository;
    private final FirebaseMessagingService firebaseMessagingService;
    private final NotificationRepository notificationRepository;
    private final UserRepository userRepository;

    @Override
    public void sendNotificationToEnrolledUsers(Long courseId, String title, String body) throws Exception {
        List<Long> enrolledUserIds = enrollmentService.getEnrolledUserIds(courseId);
        List<Token> tokens = tokenRepository.findByUserIdIn(enrolledUserIds);

        for (Token fcmToken : tokens) {
            firebaseMessagingService.sendNotification(fcmToken.getToken(), title, body);
        }
    }

    @Override
    public Page<Notification> findNotificationsByUserId(Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.getUserByUsername(username).getId();
        return notificationRepository.findNotificationsByUserId(userId, pageable);
    }

    @Override
    public Notification createNotification(NotificationDTO notificationDTO) {
        User existingUser = userRepository.findById(notificationDTO.getUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!!"));
        Notification newNotification = Notification.builder()
                .title("New Lesson has been created!!")
                .message("Check now! ")
                .user(existingUser)
                .build();

        notificationRepository.save(newNotification);
        return newNotification;
    }

    @Override
    public Notification updateNotification(Long notificationId) {
        Notification existingNotification = notificationRepository.findNotificationById(notificationId);
        existingNotification.setIsRead(true);
        notificationRepository.save(existingNotification);
        return existingNotification;
    }
}
