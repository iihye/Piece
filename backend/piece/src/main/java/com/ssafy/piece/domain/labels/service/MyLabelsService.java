package com.ssafy.piece.domain.labels.service;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import com.ssafy.piece.domain.labels.dto.response.LabelsResponseDto;
import com.ssafy.piece.domain.labels.entity.Labels;
import com.ssafy.piece.domain.labels.entity.MyLabels;
import com.ssafy.piece.domain.labels.repository.LabelsRepository;
import com.ssafy.piece.domain.labels.repository.MyLabelsRepository;
import com.ssafy.piece.domain.pieces.entity.GenreType;
import com.ssafy.piece.domain.pieces.service.PiecesService;
import com.ssafy.piece.domain.users.service.UsersService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MyLabelsService {

    private final MyLabelsRepository myLabelsRepository;
    private final LabelsRepository labelsRepository;
    private final LabelsService labelsService;
    private final PiecesService piecesService;
    private final UsersService usersService;

    // 칭호 착용
    public void wearMyLabels(Long userId, Long labelId) {
        Labels labels = labelsService.findById(labelId);
        usersService.setLabelId(userId, labelId);

    }

    // 칭호 착용 해제
    public void wearoffMyLabels(Long userId) {
        usersService.setLabelIdNull(userId);
    }

    // 칭호 목록
    public List<LabelsResponseDto> listMyLabels(Long userId) {
        List<LabelsResponseDto> labelsResponseDtos = new ArrayList<>();
        Long wearLabelId = null;
        if (userId != null && usersService.findLabelId(userId) != null) {
            wearLabelId = usersService.findLabelId(userId);
        }

        List<Labels> labels = labelsRepository.findAll();

        for (Labels label : labels) {
            boolean isMyLabels = myLabelsRepository.existsByLabelIdAndUserId(label.getLabelId(),
                userId);
            boolean isWearLabel = label.getLabelId().equals(wearLabelId);

            LabelsResponseDto labelsResponseDto = LabelsResponseDto.builder()
                .labelId(label.getLabelId())
                .labelType(label.getLabelType())
                .title(label.getTitle())
                .description(label.getDescription())
                .isMyLabels(isMyLabels)
                .isWearLabels(isWearLabel)
                .build();

            labelsResponseDtos.add(labelsResponseDto);
        }

        return labelsResponseDtos;
    }

    // 칭호 획득
    public void addMyLabels(Long userId, Long labelId) {
        Labels labels = labelsService.findById(labelId);

        MyLabels myLabels = MyLabels.builder()
            .userId(userId)
            .labels(labels)
            .build();

        if (!myLabelsRepository.existsByLabelIdAndUserId(labelId, userId)) {
            myLabelsRepository.save(myLabels);
        }
    }

    // 칭호 획득 검사
    public void checkMyLabels(Long userId) {

        // 1~14 장르 검사
        for (GenreType genre : GenreType.values()) {
            if (piecesService.isGenreOver(genre, userId)) {
                addMyLabels(userId, (long) genre.getId());
            }
        }

        // 15 영화 조각 5개
        if (piecesService.isPerformanceTypeOver(CultureType.MOVIE, userId)) {
            addMyLabels(userId, 15L);
        }

        // 16 조조 영화
        LocalTime morningStart = LocalTime.of(6, 0, 0);
        LocalTime morningEnd = LocalTime.of(10, 0, 0);
        if (piecesService.isTimeMovie(morningStart, morningEnd, userId)) {
            addMyLabels(userId, 16L);
        }

        // 17 심야 영화
        LocalTime eveningStart = LocalTime.of(22, 0, 0);
        LocalTime eveningEnd = LocalTime.of(23, 59, 59);
        if (piecesService.isTimeMovie(eveningStart, eveningEnd, userId)) {
            addMyLabels(userId, 17L);
        }

        // 18 A열
        if (piecesService.isSeat(userId)) {
            addMyLabels(userId, 18L);
        }

        // 19 콘서트 조각 5개
        if (piecesService.isPerformanceTypeOver(CultureType.CONCERT, userId)) {
            addMyLabels(userId, 19L);
        }

        // 20 특정 아티스트
        if (piecesService.isCast(userId)) {
            addMyLabels(userId, 20L);
        }

        // 21 뮤지컬 조각 5개
        if (piecesService.isPerformanceTypeOver(CultureType.MUSICAL, userId)) {
            addMyLabels(userId, 21L);
        }

        // 22 연극 조각 5개
        if (piecesService.isPerformanceTypeOver(CultureType.THEATER, userId)) {
            addMyLabels(userId, 22L);
        }

        // 23 PIECE 가입
        addMyLabels(userId, 23L);

        // 24 한 조각 만들기
        if (piecesService.isPieceMake(1, userId)) {
            addMyLabels(userId, 24L);
        }

        // 25 열 조각 만들기
        if (piecesService.isPieceMake(10, userId)) {
            addMyLabels(userId, 25L);
        }

        // 26 새벽에 만들기
        LocalDateTime midNightStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime midNightEnd = LocalDateTime.now().withHour(4).withMinute(0).withSecond(0);
        if (piecesService.isMidNight(midNightStart, midNightEnd, userId)) {
            addMyLabels(userId, 26L);
        }

        // 27 관람 당일
        if (piecesService.isEqualDateAndCreatedAt(userId)) {
            addMyLabels(userId, 27L);
        }

        // 28 채팅방 - 미완성

        // 29 소비
        if (piecesService.isConsume(userId)) {
            addMyLabels(userId, 29L);
        }

        // 30 1~14 장르 모두 모았는지
        if (isCollect(userId)) {
            addMyLabels(userId, 30L);
        }

        // 31 서로 다른 장르 조각 모았는지
        if (piecesService.isGenreMany(userId)) {
            addMyLabels(userId, 31L);
        }
    }

    // 장르 모두 모으기
    public boolean isCollect(Long userId) {
        return myLabelsRepository.countByGenreAll(userId) == 14;
    }
}
