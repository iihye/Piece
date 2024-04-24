package com.ssafy.piece.domain.pieces.service;

import com.ssafy.piece.domain.pieces.dto.request.PiecesAddRequestDto;
import com.ssafy.piece.domain.pieces.dto.response.PiecesDetailResponseDto;
import com.ssafy.piece.domain.pieces.entity.Pieces;
import com.ssafy.piece.domain.pieces.exception.PiecesNotFoundException;
import com.ssafy.piece.domain.pieces.repository.PiecesRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PiecesService {

    private final PiecesRepository piecesRepository;

    // 조각 등록
    public Pieces addPieces(PiecesAddRequestDto piecesAddRequestDto) {
        // user 찾기

        Pieces pieces = Pieces.builder()
            .performanceType(piecesAddRequestDto.getPerformanceType())
            .title(piecesAddRequestDto.getTitle())
            .date(piecesAddRequestDto.getDate())
            .time(piecesAddRequestDto.getTime())
            .cast(piecesAddRequestDto.getCast())
            .supervision(piecesAddRequestDto.getSupervision())
            .seat(piecesAddRequestDto.getSeat())
            .price(piecesAddRequestDto.getPrice())
            .address(piecesAddRequestDto.getAddress())
            .score(piecesAddRequestDto.getScore())
            .comment(piecesAddRequestDto.getComment())
            .isOpen(piecesAddRequestDto.isOpen())
            .imageFront(piecesAddRequestDto.getImageFront())
            .imageBack(piecesAddRequestDto.getImageBack())
//            .user(users)
            .build();

        return piecesRepository.save(pieces);
    }


    // 조각 상세
    public PiecesDetailResponseDto findPieceDetail(Long pieceId) {
        // user 찾기
        // 조각 권한 있는지 확인

        Pieces pieces = piecesRepository.findById(pieceId)
            .orElseThrow(() -> new IllegalArgumentException("해당 조각이 없습니다."));

        PiecesDetailResponseDto piecesDetailResponseDto = PiecesDetailResponseDto.builder()
            .pieceId(pieces.getPieceId())
            .title(pieces.getTitle())
            .frontImg(pieces.getImageFront())
            .backImg(pieces.getImageBack())
            .build();
        return piecesDetailResponseDto;
    }

    // 조각 삭제
    public void deletePiece(Long pieceId) {
        // user 찾기
        // 조각 생성자인지 확인

        piecesRepository.deleteById(pieceId);
    }

    // 조각 조회
    public Pieces findById(Long pieceId) {
        return piecesRepository.findById(pieceId)
            .orElseThrow(PiecesNotFoundException::new);
    }
}
