package com.example.notification.service;


import com.example.notification.domain.FcmMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.messaging.FirebaseMessaging;
import lombok.RequiredArgsConstructor;
import okhttp3.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {
    private final String API_URL = "https://fcm.googleapis.com/v1/projects/pre-gwasuoneshot/messages:send";
    private final ObjectMapper objectMapper;

    public Response sendMessage(String deviceToken) throws IOException {
        OkHttpClient client = new OkHttpClient();
        FcmMessage message = FcmMessage.makeMessage(deviceToken, "제목", "내용");
        RequestBody requestBody = RequestBody.create(objectMapper.writeValueAsString(message),
                MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url(API_URL)
                .post(requestBody)
                .addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
                .addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
                .build();

        return client.newCall(request).execute();
    }

    private String getAccessToken() throws IOException {
        String firebaseConfigPath = "firebase_service_key.json";
        GoogleCredentials googleCredentials = GoogleCredentials
                .fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
                .createScoped(List.of("https://www.googleapis.com/auth/cloud-platform"));
        googleCredentials.refreshIfExpired();
        return googleCredentials.getAccessToken().getTokenValue();
    }
}
