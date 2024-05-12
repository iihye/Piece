package com.ssafy.piece.domain.pieces.service;

import com.ssafy.piece.domain.pieces.dto.response.PieceListResponseDto;
import com.ssafy.piece.domain.pieces.entity.Pieces;
import com.ssafy.piece.domain.pieces.repository.HeartRepository;
import com.ssafy.piece.domain.pieces.repository.PieceSerchRepository;
import com.ssafy.piece.domain.pieces.repository.PiecesRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PieceSearchService {

    private final PieceSerchRepository pieceSerchRepository;
    private final HeartRepository heartRepository;

    // 공개된 조각 검색하기
    public List<PieceListResponseDto> searchPiece(String keyword, Long userId){
        List<Pieces> list =pieceSerchRepository.findBykeyword(keyword);

        List<PieceListResponseDto> result = new ArrayList<>();

        for(Pieces piece : list){
            int heart = heartRepository.findByPieceIdAndUserId(piece.getPieceId(), userId);
            result.add(PieceListResponseDto.builder()
                .pieceId(piece.getPieceId())
                .performanceType(piece.getPerformanceType())
                .title(piece.getTitle())
                .date(piece.getDate())
                .frontImg(piece.getImageFront())
                .isHeart(heart > 0)
                .build());
        }
        return result;
    }

    // 내 조각 검색하기
    public List<PieceListResponseDto> searchMyPiece(String keyword, Long userId){
        List<Pieces> list =pieceSerchRepository.findBykeywordAndUserId(keyword,userId);

        List<PieceListResponseDto> result = new ArrayList<>();

        for(Pieces piece : list){
            int heart = heartRepository.findByPieceIdAndUserId(piece.getPieceId(), userId);
            result.add(PieceListResponseDto.builder()
                .pieceId(piece.getPieceId())
                .performanceType(piece.getPerformanceType())
                .title(piece.getTitle())
                .date(piece.getDate())
                .frontImg(piece.getImageFront())
                .isHeart(heart > 0)
                .build());
        }
        return result;
    }

}
