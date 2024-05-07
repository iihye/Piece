package com.ssafy.piece.domain.users.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserPasswordChangeRequestDto {

    private String username; // 사용자의 아이디
    private String newPassword; // 사용자의 새로운 비밀번호
}
