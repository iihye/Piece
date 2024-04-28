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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

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

        System.out.println("2번 : " + imageUrl);

        Map<String, Object> imgUrl = new HashMap<>();
        imgUrl.put("url", imageUrl);
        imgUrl.put("detail", "high");

        Map<String, Object> imgContent = new HashMap<>();
        imgContent.put("type", "image_url");
        imgContent.put("image_url", imgUrl);

        String prompt = "이미지에 그려진 글자는 설명하지말고 색상과 분위기를 바탕으로 사진을 200자 이내로 설명해줘. 맞게 설명했는지 검토 후 답변해줘";

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
        request.put("max_tokens", 300);
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

        return contentResponse;
    }

    // 달리 이미지 생성
    public String MakeImage(String prompt) {

        Map<String, Object> request = new HashMap<>();
        request.put("model", dalleModel);
        request.put("prompt", prompt + "이 설명에 어울리는 배경을 만들어줘");
        request.put("n", 1);
//        request.put("quality", );
        request.put("response_format", "b64_json");
        request.put("size", "1024x1024");
//        request.put("style","")

        System.out.println("3번 : " + request);

        String response = webClient.post()
            .uri(dalleUrl)
            .header("Authorization", "Bearer " + dalleSecret)
            .header("Content-Type", "application/json")
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(String.class)
            .block();

        System.out.println("4번 : " + response);

        ObjectMapper objectMapper = new ObjectMapper();
        String imageResponse = null;
        try {
            JsonNode responseNode = objectMapper.readTree(response);
            imageResponse = responseNode.path("data").get(0).path("b64_json").asText();
            byte[] image = Base64.getDecoder().decode(imageResponse);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return imageResponse;
    }

    // S3 업로드를 위한 달리 이미지 (base 64) -> MultipartFile로 변환
    public MultipartFile convertImage(String img) {

        byte[] image = Base64.getDecoder().decode(img);

        MultipartFile imageFile = null;

        int totalCnt = 1024;
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(totalCnt)) {
            int offset = 0;
            while (offset < image.length) {
                int chunkSize = Math.min(totalCnt, image.length - offset);

                byte[] byteArray = new byte[chunkSize];
                System.arraycopy(image, offset, byteArray, 0, chunkSize);

                byteArrayOutputStream.write(byteArray);
                byteArrayOutputStream.flush();

                offset += chunkSize;
            }

            // ByteArrayOutputStream -> ByteArrayInputStream
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
                byteArrayOutputStream.toByteArray());

            String fileName =
                String.valueOf((int) (Math.random() * 900000) + 100000) + LocalDateTime.now()
                    .format(
                        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            // MultipartFile 객체 생성
            imageFile = new MockMultipartFile(fileName, byteArrayInputStream.readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageFile;
    }
}
