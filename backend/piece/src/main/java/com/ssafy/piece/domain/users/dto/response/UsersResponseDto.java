package com.ssafy.piece.domain.users.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsersResponseDto {
    //회원가입 후 반환되는 responseDto (제 생각은 아이디를 알려주고 회원가입 성공 혹은 실패를 보여준다?)
    private Long userId; //생성된 아이디의 고유 식별자
    private String message; //성공 or 실패 메시지


}
