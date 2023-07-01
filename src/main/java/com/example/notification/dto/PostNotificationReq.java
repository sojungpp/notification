package com.example.notification.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Data
@NoArgsConstructor
public class PostNotificationReq {
    private String deviceToken;
}
