package com.ssafy.piece.domain.users.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.ssafy.piece.domain.users.entity.Users;
import com.ssafy.piece.domain.users.repository.UsersRepository;
import com.ssafy.piece.global.response.code.ErrorCode;
import com.ssafy.piece.global.response.exception.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UsersService {

    private final UsersRepository usersRepository;

    @Value("${amazon.aws.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

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
}
