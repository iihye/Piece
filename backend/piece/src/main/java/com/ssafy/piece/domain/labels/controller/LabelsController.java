package com.ssafy.piece.domain.labels.controller;

import com.ssafy.piece.domain.labels.service.LabelsService;
import com.ssafy.piece.global.response.code.SuccessCode;
import com.ssafy.piece.global.response.structure.SuccessResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/labels")
public class LabelsController {

    private final LabelsService labelsService;

    // 칭호 등록
    @PostMapping()
    public ResponseEntity<Object> labelsAdd() throws IOException {
        labelsService.addLabels();

        return SuccessResponse.createSuccess(SuccessCode.ADD_LABEL_SUCCESS);
    }

    // 칭호명 찾기
    @GetMapping("/{labelId}")
    public ResponseEntity<Object> labelsFind(@PathVariable Long labelId) {
        // labelId가 0인 경우 처리
        if (labelId == 0) {
            return SuccessResponse.createSuccess(SuccessCode.FIND_LABEL_SUCCESS, "");
        }
        String title = labelsService.findLabelsTitle(labelId);

        return SuccessResponse.createSuccess(SuccessCode.FIND_LABEL_SUCCESS, title);
    }
}
