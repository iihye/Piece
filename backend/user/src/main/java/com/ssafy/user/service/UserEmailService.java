package com.ssafy.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ssafy.user.global.email.EmailSender;
import com.ssafy.user.global.email.Aes256;

@Service
@RequiredArgsConstructor
public class UserEmailService {

    private final EmailSender emailSender;
    private final Aes256 aes256;

    public String sendEmail(String email, String order) { //인증이메일
        String result = "";
        String title = "";
        String content = "";
        switch (order) {
            case "check_email":
                title = "Piece 이메일 인증 코드입니다.";
                content = "Piece에서 보낸 이메일 인증용 코드입니다.<br>아래의 인증번호를 입력해 주세요.<br><br>코드: ";
                // 인증 코드 생성
                result = emailSender.generateCode();

                //이메일 발송
                emailSender.sendMail(email, title, content + result);

                //인증 코드 암호화하여 결과를 저장.
                result = aes256.encrypt(result);
                break;
        }
        return result;
    }
}
