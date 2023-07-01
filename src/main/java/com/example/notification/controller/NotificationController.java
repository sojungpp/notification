package com.example.notification.controller;

import com.example.notification.dto.PostNotificationReq;
import com.example.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import okhttp3.Response;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RequiredArgsConstructor
@RequestMapping("/notifications")
@RestController
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping ("")
    public String pushNotification(@RequestBody PostNotificationReq request) {
        try {
            Response response =  notificationService.sendMessage(request.getDeviceToken());
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
