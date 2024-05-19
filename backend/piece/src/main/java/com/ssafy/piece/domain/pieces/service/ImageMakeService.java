package com.ssafy.piece.domain.pieces.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageMakeService {

    private final WebClient webClient;

    @Value("${openai.gpt-model}")
    private String gptModel;

    @Value("${openai.gpt-url}")
    private String gptUrl;

    @Value("${openai.gpt-key}")
    private String gptSecret;

    @Value("${openai.dalle-model}")
    private String dalleModel;

    @Value("${openai.dalle-url}")
    private String dalleUrl;

    @Value("${openai.dalle-key}")
    private String dalleSecret;

    // GPT에게 이미지 전달 + 달리에게 전달할 프롬프트 작성
    public String MakePrompt(String imageUrl) {

        Map<String, Object> imgUrl = new HashMap<>();
        imgUrl.put("url", imageUrl);
        imgUrl.put("detail", "high");

        Map<String, Object> imgContent = new HashMap<>();
        imgContent.put("type", "image_url");
        imgContent.put("image_url", imgUrl);

        String prompt = "달리 이미지 생성을 위한 이미지 설명을 작성해주세요. 글자는 설명하지 마세요. 분위기 설명에 불필요한 물체나 인물에 대한 설명은 생략하세요. 색상과 분위기를 중점으로 400자이내로 설명해주세요. 맞게 설명했는지 검토 후 답변해주세요";

        Map<String, Object> textContent = new HashMap<>();
        textContent.put("type", "text");
        textContent.put("text", prompt);

        Map<String, Object> message = new HashMap<>();
        message.put("model", gptModel);

        List<Map<String, Object>> content = new ArrayList<>();
        content.add(textContent);
        content.add(imgContent);

        Map<String, Object> massage = new HashMap<>();
        massage.put("role", "user");
        massage.put("content", content);

        List<Map<String, Object>> messages = new ArrayList<>();
        messages.add(massage);

        Map<String, Object> request = new HashMap<>();
        request.put("model", gptModel);
        request.put("messages", messages);
        request.put("max_tokens", 500);
        request.put("temperature", 1);
        request.put("top_p", 0.8);
        request.put("presence_penalty", 0.5);

        WebClient wc = WebClient.create();

        String response = wc.post()
            .uri(gptUrl)
            .header("Authorization", "Bearer " + gptSecret)
            .header("Content-Type", "application/json")
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(String.class)
            .block();

        ObjectMapper objectMapper = new ObjectMapper();
        String contentResponse = null;
        try {
            JsonNode responseNode = objectMapper.readTree(response);
            contentResponse = responseNode.path("choices").get(0).path("message").path("content")
                .asText();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return MakeImage(contentResponse);
    }

    // 달리를 이용한 뒷면 이미지 생성
    public String MakeImage(String prompt) {

        Map<String, Object> request = new HashMap<>();
        request.put("model", dalleModel);
        request.put("prompt", prompt + "제공된 이미지 설명 중 주요 색상과 분위기를 기반으로 인상주의 스타일의 부드러운 색조의 전환과 빛을 특징으로 하는 풍경화를 그려주세요. 설명 중 배경을 중점으로 이미지를 생성하세요. 인물은 그림에 넣지 마세요. 그림 스타일은 유화 느낌의 부드러운 붓터치를 표현해주세요.");
        request.put("n", 1);
        request.put("response_format", "b64_json");
        request.put("size", "1024x1024");
//        request.put("style","natural");
//        request.put("quality", );

        String response = webClient.post()
            .uri(dalleUrl)
            .header("Authorization", "Bearer " + dalleSecret)
            .header("Content-Type", "application/json")
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(String.class)
            .block();

        ObjectMapper objectMapper = new ObjectMapper();
        String imageResponse = null;
        try {
            JsonNode responseNode = objectMapper.readTree(response);
            imageResponse = responseNode.path("data").get(0).path("b64_json").asText();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "data:image/png;base64,"+imageResponse;
    }

//    // S3 업로드를 위한 달리 이미지 (base 64) -> MultipartFile로 변환
//    public MultipartFile convertImage(String img) {
//
//        byte[] image = Base64.getDecoder().decode(img);
//
//        MultipartFile imageFile = null;
//
//        int totalCnt = 1024;
//        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(totalCnt)) {
//            int offset = 0;
//            while (offset < image.length) {
//                int chunkSize = Math.min(totalCnt, image.length - offset);
//
//                byte[] byteArray = new byte[chunkSize];
//                System.arraycopy(image, offset, byteArray, 0, chunkSize);
//
//                byteArrayOutputStream.write(byteArray);
//                byteArrayOutputStream.flush();
//
//                offset += chunkSize;
//            }
//
//            // ByteArrayOutputStream -> ByteArrayInputStream
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
//                byteArrayOutputStream.toByteArray());
//
//            String fileName =
//                String.valueOf((int) (Math.random() * 900000) + 100000) + LocalDateTime.now()
//                    .format(
//                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//
//            // MultipartFile 객체 생성
//            imageFile = new MockMultipartFile(fileName, byteArrayInputStream.readAllBytes());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        return imageFile;
//    }
}
