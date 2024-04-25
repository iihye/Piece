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
    public void addHeart(Long pieceId) {
        // user 찾기
        Pieces piece = piecesService.findById(pieceId);

        Piecesheart piecesheart = Piecesheart.builder()
            .piece(piece)
//            .user(user)
            .build();

        heartRepository.save(piecesheart);
    }

    // 찜 삭제
    public void deleteHeart(Long heartId) {
        // user 찾기

        heartRepository.deleteById(heartId);
    }
}
