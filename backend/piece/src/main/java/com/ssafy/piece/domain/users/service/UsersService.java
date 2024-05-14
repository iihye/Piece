package com.ssafy.piece.domain.users.service;

import com.ssafy.piece.domain.users.entity.Users;
import com.ssafy.piece.domain.users.repository.UsersRepository;
import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UsersService {

    private final UsersRepository usersRepository;

    public void setLabelId(Long userId, Long labelId) {
        Users users = findByUserId(userId);
        users.setLabelId(labelId);
    }

    public void setLabelIdNull(Long userId) {
        Users users = findByUserId(userId);
        users.setLabelId(null);
    }

    public Long findLabelId(Long userId) {
        Users users = findByUserId(userId);
        return users.getLabelId();
    }

    public Users findByUserId(Long userId) {
        return usersRepository.findByUserId(userId)
            .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다.")); // 추후 수정
    }


    /**
     *
     * @param prefix  userId
     * @param S3Path  이미지 저장된 s3 경로
     */
    public void updateUserProfileImage(String prefix, String S3Path) {
        Long userId = Long.parseLong(prefix);
        Users user = usersRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException(ErrorCode.USER_NOT_FOUND));
        user.setProfileImage(S3Path);
        usersRepository.save(user);
    }
}
