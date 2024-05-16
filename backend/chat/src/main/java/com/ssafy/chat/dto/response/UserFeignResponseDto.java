package com.ssafy.chat.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserFeignResponseDto {
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long userId;
    private String email;
    private String profileImage;
    private String nickname;
    private String socialId;
    private Long labelId;
    private String password;
    private boolean tutorial;
}