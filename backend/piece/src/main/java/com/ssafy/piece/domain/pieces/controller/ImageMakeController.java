package com.ssafy.piece.domain.pieces.controller;

import com.ssafy.piece.domain.pieces.service.ImageMakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/piece")
public class ImageMakeController {

    private final ImageMakeService imageMakeService;

    @PostMapping("/make")
    public ResponseEntity<String> promptMake(@RequestParam String img) {

        System.out.println("1번 : " + img);

        String prompt = imageMakeService.MakePrompt(img);

        System.out.println("6번 " + prompt);

        return new ResponseEntity<>(prompt, HttpStatus.OK);
    }

}
