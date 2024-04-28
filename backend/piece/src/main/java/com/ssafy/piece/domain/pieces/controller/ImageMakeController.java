package com.ssafy.piece.domain.pieces.controller;

import com.ssafy.piece.domain.pieces.service.ImageMakeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/piece")
public class ImageMakeController {

    private final ImageMakeService imageMakeService;

}
