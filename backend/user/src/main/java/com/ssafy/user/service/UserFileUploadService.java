package com.ssafy.user.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.ssafy.user.entity.Users;
import com.ssafy.user.exception.UserNotFoundException;
import com.ssafy.user.global.response.code.ErrorCode;
import com.ssafy.user.repository.UsersRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserFileUploadService {

    @Value("${amazon.aws.bucket}")
    private String bucket;

    @Value("${amazon.aws.prefixAddress}")
    private String prefixAddress;

    private final AmazonS3 amazonS3;

    private final UsersRepository usersRepository;


    /**
     * user table에 profileIamge 컬럼에 imageURL 저장
     *
     * @param prefix  userID (PK)
     * @param S3Path  이미지가 저장되어 있는 s3 주소
     */
    public void saveUserProfileImage(String prefix, String S3Path) {
        Long userId = Long.parseLong(prefix);
        Users user = usersRepository.findByUserId(userId)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        user.setProfileImage(S3Path);
        usersRepository.save(user);
    }


    /**
     * user table에 profileIamge 컬럼에 imageURL 삭제
     * @param userId
     */
    @Transactional
    public void deleteProfileImage(Long userId) {
        Users user = usersRepository.findByUserId(userId)
            .orElseThrow(() -> new UserNotFoundException(ErrorCode.USER_NOT_FOUND));
        String replaceStr = "https://" + prefixAddress;
        String fileKey = user.getProfileImage().replace(replaceStr, "");
        amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileKey));  // S3 파일 삭제
        usersRepository.clearProfileImage(userId);  // DB profileImage 삭제
    }


}
