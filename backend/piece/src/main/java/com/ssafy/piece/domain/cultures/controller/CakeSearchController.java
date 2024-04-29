package com.ssafy.piece.domain.cultures.controller;

import com.ssafy.piece.domain.cultures.dto.response.CakeListResponseDto;
import com.ssafy.piece.domain.cultures.service.CakeSearchService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/cakesearch")
public class CakeSearchController {

    private CakeSearchService cakeSearchService;

    @GetMapping
    public ResponseEntity<Object> cakeSearchList(@PathVariable String keyword) {

        List<CakeListResponseDto> list = cakeSearchService.searchCake(keyword);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
