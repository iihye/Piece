package com.ssafy.piece.domain.users.dto.request;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data

public class UserNicknameChangeRequestDto {
    private String username; // 사용자의 현재 닉네임
    private String newNickname; // 사용자의 새로운 닉네임
}
