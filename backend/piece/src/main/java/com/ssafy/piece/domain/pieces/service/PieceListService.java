package com.ssafy.piece.domain.pieces.service;

import com.ssafy.piece.domain.pieces.dto.response.PieceListResponseDto;
import com.ssafy.piece.domain.pieces.dto.response.PieceRecentResponseDto;
import com.ssafy.piece.domain.pieces.entity.Pieces;
import com.ssafy.piece.domain.pieces.repository.HeartRepository;
import com.ssafy.piece.domain.pieces.repository.PiecesRepository;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PieceListService {

    private final PiecesRepository piecesRepository;
    private final HeartRepository HeartRepository;

    // 조각 리스트
    public List<PieceListResponseDto> listPieces(Long userId) {
        List<Pieces> pieces = piecesRepository.findByAll();
        return getPieceListResponseDtos(userId, pieces);
    }

    // 내 조각 리스트
    public List<PieceListResponseDto> listMyPieces(Long userId) {
        List<Pieces> pieces = piecesRepository.findByUserId(userId);
        return getPieceListResponseDtos(userId, pieces);
    }

    // 찜한 조각 리스트
    public List<PieceListResponseDto> listHeartPieces(Long userId) {
        List<Pieces> pieces = piecesRepository.findByUserIdAndHeart(userId);
        return getPieceListResponseDtos(userId, pieces);
    }

    // 최근 조각 리스트
    public List<PieceRecentResponseDto> listRecentPieces(Long userId) {
        Page<Pieces> piecesPage = piecesRepository.findTopPieces(PageRequest.of(0, 10));

        List<PieceRecentResponseDto> pieceListResponseDtos = new ArrayList<>();

        for (Pieces piece : piecesPage.getContent()) {
            int cntHeart = HeartRepository.findByPieceIdAndUserId(piece.getPieceId(), userId);
            PieceRecentResponseDto pieceRecentResponseDto = PieceRecentResponseDto.builder()
                .pieceId(piece.getPieceId())
                .frontImg(piece.getImageFront())
                .backImg(piece.getImageBack())
                .build();
            pieceListResponseDtos.add(pieceRecentResponseDto);
        }

        return pieceListResponseDtos;
    }


    // 조각 리스트 Dto로 변환
    private List<PieceListResponseDto> getPieceListResponseDtos(Long userId, List<Pieces> pieces) {
        List<PieceListResponseDto> pieceListResponseDtos = new ArrayList<>();

        for (Pieces piece : pieces) {
            int cntHeart = HeartRepository.findByPieceIdAndUserId(piece.getPieceId(), userId);
            PieceListResponseDto pieceListResponseDto = PieceListResponseDto.builder()
                .pieceId(piece.getPieceId())
                .performanceType(piece.getPerformanceType())
                .title(piece.getTitle())
                .date(piece.getDate())
                .frontImg(piece.getImageFront())
                .isHeart(cntHeart > 0)
                .build();
            pieceListResponseDtos.add(pieceListResponseDto);
        }

        return pieceListResponseDtos;
    }

}
