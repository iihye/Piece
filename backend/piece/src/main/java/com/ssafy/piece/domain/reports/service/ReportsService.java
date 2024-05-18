package com.ssafy.piece.domain.reports.service;

import com.ssafy.piece.domain.reports.dto.ReportsAddRequestDto;
import com.ssafy.piece.domain.reports.dto.ReportsCommentsAddRequestDto;
import com.ssafy.piece.domain.reports.dto.ReportsNoticesAddRequestDto;
import com.ssafy.piece.domain.reports.entity.Reports;
import com.ssafy.piece.domain.reports.entity.ReportsComments;
import com.ssafy.piece.domain.reports.entity.ReportsNotices;
import com.ssafy.piece.domain.reports.repository.ReportsCommentsRepository;
import com.ssafy.piece.domain.reports.repository.ReportsNoticesRepository;
import com.ssafy.piece.domain.reports.repository.ReportsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReportsService {

    private final ReportsRepository reportsRepository;
    private final ReportsCommentsRepository reportsCommentsRepository;
    private final ReportsNoticesRepository reportsNoticesRepository;

    // 조각 신고
    public void addReports(Long userId, ReportsAddRequestDto reportsAddRequestDto){
        Reports reports = Reports.builder()
            .reporterUserId(userId)
            .pieceId(reportsAddRequestDto.getPieceId())
            .reportType(reportsAddRequestDto.getReportType())
            .build();

        reportsRepository.save(reports);
    }

    // 문의 작성
    public void addReportsComments(Long userId, ReportsCommentsAddRequestDto reportsCommentsAddRequestDto){
        ReportsComments reportsComments = ReportsComments.builder()
            .userId(userId)
            .comments(reportsCommentsAddRequestDto.getComments())
            .build();

        reportsCommentsRepository.save(reportsComments);
    }

    // 공지 작성
    public void addReportsNotices(ReportsNoticesAddRequestDto reportsNoticesAddRequestDto){
        ReportsNotices reportsNotices = ReportsNotices.builder()
                .title(reportsNoticesAddRequestDto.getTitle())
                .content(reportsNoticesAddRequestDto.getContent())
                .build();

        reportsNoticesRepository.save(reportsNotices);
    }

}
