package com.ssafy.piece.domain.pieces.service;

import com.ssafy.piece.domain.pieces.entity.Pieces;
import com.ssafy.piece.domain.pieces.entity.Piecesheart;
import com.ssafy.piece.domain.pieces.repository.HeartRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {

    private final HeartRepository heartRepository;
    private final PiecesService piecesService;

    // 찜 생성
    public void addHeart(Long userId, Long pieceId) {
        Pieces piece = piecesService.findById(pieceId);

        Piecesheart piecesheart = Piecesheart.builder()
            .piece(piece)
            .userId(userId)
            .build();

        heartRepository.save(piecesheart);
    }

    // 찜 삭제
    public void deleteHeart(Long userId, Long pieceId) {
        Long heartId = heartRepository.findByUserIdAndHeartId(userId, pieceId);

        heartRepository.deleteById(heartId);
    }

    // 찜 여부 확인
    public boolean isHeart(Long userId, Long pieceId) {
        boolean heart = heartRepository.findByPieceIdAndUserId(pieceId, userId) > 0;
        return heart;
    }
}
