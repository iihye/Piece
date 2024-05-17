package com.ssafy.piece.domain.pieces.service;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import com.ssafy.piece.domain.pieces.dto.request.PiecesAddRequestDto;
import com.ssafy.piece.domain.pieces.dto.request.RecordImageAddRequestDto;
import com.ssafy.piece.domain.pieces.dto.request.RecordUpdateRequestDto;
import com.ssafy.piece.domain.pieces.dto.response.PieceRecentResponseDto;
import com.ssafy.piece.domain.pieces.dto.response.PiecesDetailResponseDto;
import com.ssafy.piece.domain.pieces.dto.response.RecordDetailResponseDto;
import com.ssafy.piece.domain.pieces.entity.GenreType;
import com.ssafy.piece.domain.pieces.entity.Pieces;
import com.ssafy.piece.domain.pieces.entity.Piecesimage;
import com.ssafy.piece.domain.pieces.exception.PiecesNotFoundException;
import com.ssafy.piece.domain.pieces.exception.PiecesRecentNotFoundException;
import com.ssafy.piece.domain.pieces.repository.HeartRepository;
import com.ssafy.piece.domain.pieces.repository.PiecesRepository;
import com.ssafy.piece.domain.pieces.repository.PiecesimageRepository;
import com.ssafy.piece.domain.statistics.service.StatisticsService;
import jakarta.persistence.Tuple;
import jakarta.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PiecesService {

    private final PiecesRepository piecesRepository;
    private final PiecesimageRepository piecesimageRepository;
    private final HeartRepository heartRepository;
    private final StatisticsService statisticsService;

    // 조각 등록
    public Pieces addPieces(Long userId, PiecesAddRequestDto piecesAddRequestDto) {
        GenreType genreType = GenreType.valueOf(String.valueOf(piecesAddRequestDto.getGenre()));

        Pieces pieces = Pieces.builder()
            .userId(userId)
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
            .genre(genreType)
            .isPrivate(piecesAddRequestDto.getIsPrivate())
            .build();
//        modifyViews(Long userId, CultureType type, LocalDate date)
//        modifyConsumption(Long userId, LocalDate date, int price)
        statisticsService.modifyViews(userId,pieces.getPerformanceType(),pieces.getDate());
        statisticsService.modifyConsumption(userId,pieces.getDate(),pieces.getPrice());

        return piecesRepository.save(pieces);
    }


    // 조각 상세
    public PiecesDetailResponseDto findPieceDetail(Long userId, Long pieceId) {
        // 조각 권한 있는지 확인

        Pieces pieces = findById(pieceId);

        PiecesDetailResponseDto piecesDetailResponseDto = PiecesDetailResponseDto.builder()
            .pieceId(pieces.getPieceId())
            .userId(pieces.getUserId())
            .title(pieces.getTitle())
            .frontImg(pieces.getImageFront())
            .backImg(pieces.getImageBack())
            .build();
        return piecesDetailResponseDto;
    }

    // 조각 삭제
    public void deletePiece(Long userId, Long pieceId) {
        // 조각 생성자인지 확인

        piecesimageRepository.deleteByPieceId(pieceId);
        heartRepository.deleteByPieceId(pieceId);
        piecesRepository.deleteById(pieceId);
    }

    // 기록 수정
    public void updateRecord(Long userId, RecordUpdateRequestDto recordUpdateRequestDto) {
        // 조각 생성자인지 확인

        Pieces pieces = findById(recordUpdateRequestDto.getPieceId());
        pieces.setRecord(recordUpdateRequestDto.getRecord());
    }

    // 기록 조회
    public RecordDetailResponseDto findRecordDetail(Long userId, Long pieceId) {
        // 조각 권한 있는지 확인
        Pieces pieces = findById(pieceId);

        // 사진 조회
        List<Piecesimage> piecesimages = piecesimageRepository.findByPieceId(pieceId);
        List<String> imgList = piecesimages.stream()
            .map(Piecesimage::getImageUrl)
            .toList();
        List<Long> imgIdList = piecesimages.stream()
            .map(Piecesimage::getPiecesImageId)
            .toList();

        return RecordDetailResponseDto.builder()
            .recordId(pieces.getPieceId())
            .pieceId(pieces.getPieceId())
            .record(pieces.getRecord())
            .imgList(imgList)
            .imgIdList(imgIdList)
            .build();
    }

    // 1년 전 조각 조회
    public PieceRecentResponseDto findPieceYear(Long userId) {
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

    // 기록 이미지 저장
    public void savePieceRecordImage(Long userId,
        RecordImageAddRequestDto recordImageAddRequestDto) {
        Pieces piece = findById(recordImageAddRequestDto.getPieceId());
        Piecesimage piecesimage = Piecesimage.builder()
            .piece(piece)
            .imageUrl(recordImageAddRequestDto.getS3path())
            .build();

        piecesimageRepository.save(piecesimage);
    }

    // 기록 이미지 삭제
    public void deletePieceRecordImage(Long userId, Long piecesimageId) {
        piecesimageRepository.deleteById(piecesimageId);
    }


    // 칭호 관련
    // 장르 3개 이상
    public boolean isGenreOver(GenreType genre, Long userId) {
        return piecesRepository.countByGenre(genre, userId) >= 3;
    }

    // 종류 5개 이상
    public boolean isPerformanceTypeOver(CultureType performanceType, Long userId) {
        return piecesRepository.countByPerformanceType(performanceType, userId) >= 5;
    }

    // 영화 시간
    public boolean isTimeMovie(LocalTime start, LocalTime end, Long userId) {
        return piecesRepository.countByTimeMovie(start, end, CultureType.MOVIE, userId) >= 1;
    }

    // 좌석
    public boolean isSeat(Long userId) {
        return piecesRepository.countBySeat("A열", userId) >= 3;
    }

    // 출연
    public boolean isCast(Long userId) {
        List<Tuple> result = piecesRepository.countPiecesByCastForUser(CultureType.CONCERT, userId);
        for (Tuple tuple : result) {
            if (tuple.get(1, Long.class).intValue() >= 3) {
                return true;
            }
        }

        return false;
    }

    // 만들기
    public boolean isPieceMake(int cnt, Long userId) {
        return piecesRepository.countByUserId(userId) >= cnt;
    }

    // 새벽
    public boolean isMidNight(LocalDateTime start, LocalDateTime end, Long userId) {
        return piecesRepository.findPiecesByCreatedAtBetween(start, end, userId) >= 3;
    }

    // 관람 당일
    public boolean isEqualDateAndCreatedAt(Long userId) {
        return piecesRepository.findPiecesByDateAndCreatedAt(userId) >= 1;
    }

    // 소비
    public boolean isConsume(Long userId) {
        if (piecesRepository.countByUserId(userId) > 0) {
            return piecesRepository.sumByUserId(userId) >= 1000000;
        } else {
            return false;
        }
    }

    // 장르 5종류 이상
    public boolean isGenreMany(Long userId) {
        Set<String> genreTypes = piecesRepository.countByGenreType(userId);
        return genreTypes.size() >= 5;
    }
}
