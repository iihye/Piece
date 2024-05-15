package com.ssafy.user.dto.request;

import lombok.Data;

@Data
public class UserPasswordChangeRequestDto {

    private String newPassword; // 사용자의 새로운 비밀번호
}
