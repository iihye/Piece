package com.ssafy.piece.domain.pieces.service;

import com.ssafy.piece.domain.pieces.dto.request.PiecesAddRequestDto;
import com.ssafy.piece.domain.pieces.dto.request.RecordUpdateRequestDto;
import com.ssafy.piece.domain.pieces.dto.response.PieceRecentResponseDto;
import com.ssafy.piece.domain.pieces.dto.response.PiecesDetailResponseDto;
import com.ssafy.piece.domain.pieces.dto.response.RecordDetailResponseDto;
import com.ssafy.piece.domain.pieces.entity.Pieces;
import com.ssafy.piece.domain.pieces.exception.PiecesNotFoundException;
import com.ssafy.piece.domain.pieces.exception.PiecesRecentNotFoundException;
import com.ssafy.piece.domain.pieces.repository.PiecesRepository;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
            .openYn(piecesAddRequestDto.getOpenYn())
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

        Pieces pieces = findById(pieceId);

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

    // 기록 수정
    public void updateRecord(RecordUpdateRequestDto recordUpdateRequestDto) {
        Pieces pieces = findById(recordUpdateRequestDto.getPieceId());

        pieces.setRecord(recordUpdateRequestDto.getRecord());
        // 사진 수정
    }

    // 기록 조회
    public RecordDetailResponseDto findRecordDetail(Long pieceId) {
        Pieces pieces = findById(pieceId);

        // 사진 조회

        return RecordDetailResponseDto.builder()
            .recordId(pieces.getPieceId())
            .pieceId(pieces.getPieceId())
            .record(pieces.getRecord())
            .imgList(null)
            .build();
    }

    // 1년 전 조각 조회
    public PieceRecentResponseDto findPieceYear() {
        Long userId = 1L; // user 조회

        LocalDate dateOneYearAgo = LocalDate.now().minusYears(1);
        LocalDateTime oneYearAgoStart = dateOneYearAgo.atTime(LocalTime.MIN);
        LocalDateTime oneYearAgoEnd = dateOneYearAgo.atTime(LocalTime.MAX);

        Pieces pieces = piecesRepository.findByPieceIdAndCreatedAt(userId, oneYearAgoStart,
                oneYearAgoEnd)
            .orElseThrow(PiecesRecentNotFoundException::new);

        return PieceRecentResponseDto.builder()
            .pieceId(pieces.getPieceId())
            .frontImg(pieces.getImageFront())
            .backImg(pieces.getImageBack())
            .build();
    }


    // 조각 조회
    public Pieces findById(Long pieceId) {
        return piecesRepository.findById(pieceId)
            .orElseThrow(PiecesNotFoundException::new);
    }
}
