package com.htt.ecourse.controller;

import com.htt.ecourse.components.FirebaseMessagingService;
import com.htt.ecourse.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/notifications")
public class ApiFirebaseNotificationController {
    private final FirebaseMessagingService firebaseService;

    public ApiFirebaseNotificationController(FirebaseMessagingService firebaseService) {
        this.firebaseService = firebaseService;
    }

    @PostMapping("/send")
    public String sendNotification(
            @RequestParam String token,
            @RequestParam String title,
            @RequestParam String body) {
        try {
            return firebaseService.sendNotification(token, title, body);
        } catch (Exception e) {
            return "Failed to send notification: " + e.getMessage();
        }
    }
}
