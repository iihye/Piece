package com.ssafy.user.dto.request;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 일반 회원가입시 사용되는 DTO입니다.
 */
@Data
public class UserRegistrationRequestDto {
    @NotBlank(message = "이메일은 필수항목입니다.")
    @Email(message = "유효한 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "아이디는 필수항목입니다.")
    @Size(max = 10, message = "아이디는 10자를 넘길 수 없습니다.")
    private String username; // 사용자의 로그인 아이디

    @NotBlank(message = "비밀번호는 필수항목입니다.")
    @Size(min = 10,  message = "비밀번호는 10자 이상이어야 합니다.")
    private String password;

    @NotBlank(message = "닉네임은 필수항목입니다.")
    @Size(max = 10, message = "닉네임은 10자를 넘길 수 없습니다.")
    private String nickname;

    private String profileImage; //(선택)
}
