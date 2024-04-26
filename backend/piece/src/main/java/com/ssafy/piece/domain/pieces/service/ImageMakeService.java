package com.ssafy.piece.domain.pieces.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class ImageMakeService {

    @Value("${openai.gpt-model}")
    private String gptModel;

    @Value("${openai.gpt-url}")
    private String gptUrl;

    @Value("${openai.gpt-key}")
    private String gptSecret;

    // GPT에게 이미지 전달
    public String MakePrompt(String imageUrl) {

        System.out.println("2번 : " + imageUrl);

        Map<String, Object> imgUrl = new HashMap<>();
        imgUrl.put("url", imageUrl);
        imgUrl.put("detail", "high");

        Map<String, Object> imgContent = new HashMap<>();
        imgContent.put("type", "image_url");
        imgContent.put("image_url", imgUrl);

        String prompt = "색상과 분위기를 바탕으로 사진을 200자 이내로 설명해줘";

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

        System.out.println("3번 : " + request);

        String response = wc.post()
            .uri(gptUrl)
            .header("Authorization", "Bearer " + gptSecret)
            .header("Content-type", "application/json")
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(String.class)
            .block();

        System.out.println("4번 " + response);

        ObjectMapper objectMapper = new ObjectMapper();
        String contentResponse = null;
        try {
            JsonNode responseNode = objectMapper.readTree(response);
            contentResponse = responseNode.path("choices").get(0).path("message").path("content")
                .asText();

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        System.out.println("5번 " + contentResponse);
        return contentResponse;
    }
}
