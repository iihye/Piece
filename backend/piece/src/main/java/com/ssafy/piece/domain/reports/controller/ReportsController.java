package com.ssafy.piece.domain.reports.controller;

import com.ssafy.piece.domain.reports.dto.ReportsAddRequestDto;
import com.ssafy.piece.domain.reports.dto.ReportsCommentsAddRequestDto;
import com.ssafy.piece.domain.reports.dto.ReportsNoticesAddRequestDto;
import com.ssafy.piece.domain.reports.service.ReportsService;
import com.ssafy.piece.global.annotation.AuthenticatedUser;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/reports")
public class ReportsController {

    private final ReportsService reportsService;

    // 조각 신고
    @PostMapping("/")
    public ResponseEntity<Object> reportsAdd(@AuthenticatedUser Long userId, @RequestBody ReportsAddRequestDto reportsAddRequestDto){
        reportsService.addReports(userId, reportsAddRequestDto);

        return SuccessResponse.createSuccess(SuccessCode.ADD_REPORTS_SUCCESS);
    }

    // 문의 작성
    @PostMapping("/comment")
    public ResponseEntity<Object> reportsCommentsAdd(@AuthenticatedUser Long userId, @RequestBody ReportsCommentsAddRequestDto reportsCommentsAddRequestDto){
        reportsService.addReportsComments(userId, reportsCommentsAddRequestDto);

        return SuccessResponse.createSuccess(SuccessCode.ADD_REPORTS_COMMENTS_SUCCESS);
    }

    // 공지 작성
    @PostMapping("/notice")
    public ResponseEntity<Object> reportsNoticesAdd(@RequestBody ReportsNoticesAddRequestDto reportsNoticesAddRequestDto){
        reportsService.addReportsNotices(reportsNoticesAddRequestDto);

        return SuccessResponse.createSuccess(SuccessCode.ADD_REPORTS_NOTICES_SUCCESS);
    }
}
