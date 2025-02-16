package com.htt.ecourse.controller;

import com.htt.ecourse.components.FirebaseMessagingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/notifications")
public class ApiNotificationController {
    private final FirebaseMessagingService firebaseService;

    public ApiNotificationController(FirebaseMessagingService firebaseService) {
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
