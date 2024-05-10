package com.ssafy.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 이 DTO는 사용자가 일반 로그인 할 때 필요한 아이디와 비밀번호를 서버로 전송하는 데 사용되는 DTO입니다.
 */
@Data
public class UserLoginRequestDto {
    //    사용자가 일반 로그인 할 때 필요한 아이디와 비밀번호를 서버로 전송하는 데 사용되는 DTO입니다.
    @NotBlank(message = "이메일은 필수항목입니다.")
    private String email; // 사용자의 로그인 아이디

    @NotBlank(message = "비밀번호는 필수항목입니다.")
    private String password; // 사용자의 비밀번호


}