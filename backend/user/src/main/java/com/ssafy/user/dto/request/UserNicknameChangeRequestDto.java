package com.ssafy.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data

public class UserNicknameChangeRequestDto {
    @NotBlank(message = "새로운 닉네임을 입력해주세요")
    private String newNickname; // 사용자의 새로운 닉네임
}