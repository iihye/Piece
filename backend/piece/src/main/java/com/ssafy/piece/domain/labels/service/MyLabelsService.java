package com.ssafy.piece.domain.labels.service;

import com.ssafy.piece.domain.cultures.entity.CultureType;
import com.ssafy.piece.domain.labels.dto.response.LabelsResponseDto;
import com.ssafy.piece.domain.labels.entity.Labels;
import com.ssafy.piece.domain.labels.entity.MyLabels;
import com.ssafy.piece.domain.labels.repository.LabelsRepository;
import com.ssafy.piece.domain.labels.repository.MyLabelsRepository;
import com.ssafy.piece.domain.pieces.entity.GenreType;
import com.ssafy.piece.domain.pieces.service.PiecesService;
import com.ssafy.piece.domain.users.entity.Users;
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
    private final UsersService usersService;
    private final LabelsService labelsService;
    private final PiecesService piecesService;

    // 칭호 착용
    public void wearMyLabels(Long labelId) {
        // user 찾기
        Users users = usersService.findById();
        Labels labels = labelsService.findById(labelId);

        users.setMyLabels(labels);
    }

    // 칭호 착용 해제
    public void wearoffMyLabels() {
        // user 찾기
        Users users = usersService.findById();

        users.setMyLabels(null);
    }

    // 칭호 목록
    public List<LabelsResponseDto> listMyLabels() {
        List<LabelsResponseDto> labelsResponseDtos = new ArrayList<>();
        // user 찾기
        Users users = usersService.findById();
        Long wearLabelId = null;
        if (users != null && users.getLabels() != null) {
            wearLabelId = users.getLabels().getLabelId();
        }

        List<Labels> labels = labelsRepository.findAll();

        for (Labels label : labels) {
            boolean isMyLabels = myLabelsRepository.existsByLabelIdAndUserId(label.getLabelId(),
                users.getUserId());
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
    public void addMyLabels(Long labelId) {
        // user 찾기
        Users users = usersService.findById();
        Labels labels = labelsService.findById(labelId);

        MyLabels myLabels = MyLabels.builder()
            .users(users)
            .labels(labels)
            .build();

        if (!myLabelsRepository.existsByLabelIdAndUserId(labelId, users.getUserId())) {
            myLabelsRepository.save(myLabels);
        }
    }

    // 칭호 획득 검사
    public void checkMyLabels() {
        // user 찾기
        Long userId = 1L;

        // 1~14 장르 검사
        for (GenreType genre : GenreType.values()) {
            if (piecesService.isGenreOver(genre, userId)) {
                addMyLabels((long) genre.getId());
            }
        }

        // 15 영화 조각 5개
        if (piecesService.isPerformanceTypeOver(CultureType.MOVIE, userId)) {
            addMyLabels(15L);
        }

        // 16 조조 영화
        LocalTime morningStart = LocalTime.of(6, 0, 0);
        LocalTime morningEnd = LocalTime.of(10, 0, 0);
        if (piecesService.isTimeMovie(morningStart, morningEnd, userId)) {
            addMyLabels(16L);
        }

        // 17 심야 영화
        LocalTime eveningStart = LocalTime.of(22, 0, 0);
        LocalTime eveningEnd = LocalTime.of(23, 59, 59);
        if (piecesService.isTimeMovie(eveningStart, eveningEnd, userId)) {
            addMyLabels(17L);
        }

        // 18 A열
        if (piecesService.isSeat(userId)) {
            addMyLabels(18L);
        }

        // 19 콘서트 조각 5개
        if (piecesService.isPerformanceTypeOver(CultureType.CONCERT, userId)) {
            addMyLabels(19L);
        }

        // 20 특정 아티스트
        if (piecesService.isCast(userId)) {
            addMyLabels(20L);
        }

        // 21 뮤지컬 조각 5개
        if (piecesService.isPerformanceTypeOver(CultureType.MUSICAL, userId)) {
            addMyLabels(21L);
        }

        // 22 연극 조각 5개
        if (piecesService.isPerformanceTypeOver(CultureType.THEATER, userId)) {
            addMyLabels(22L);
        }

        // 23 PIECE 가입
        addMyLabels(23L);

        // 24 한 조각 만들기
        if (piecesService.isPieceMake(1, userId)) {
            addMyLabels(24L);
        }

        // 25 열 조각 만들기
        if (piecesService.isPieceMake(10, userId)) {
            addMyLabels(25L);
        }

        // 26 새벽에 만들기
        LocalDateTime midNightStart = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime midNightEnd = LocalDateTime.now().withHour(4).withMinute(0).withSecond(0);
        if (piecesService.isMidNight(midNightStart, midNightEnd, userId)) {
            addMyLabels(26L);
        }

        // 27 관람 당일
        if (piecesService.isEqualDateAndCreatedAt(userId)) {
            addMyLabels(27L);
        }

        // 28 채팅방

        // 29 소비
        if (piecesService.isConsume(userId)) {
            addMyLabels(29L);
        }

        // 30 1~14 장르 모두 모았는지
        if (myLabelsRepository.count() == 14) {
            addMyLabels(23L);
        }

        // 31 서로 다른 장르 조각 모았는지
        if (piecesService.isGenreMany()) {
            addMyLabels(16L);
        }
    }
}
