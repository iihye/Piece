package com.ssafy.piece.global.util;

import java.util.Map;

public class UrlGenerator {

    public static String generateNextPageUrl(
        String baseUrl,
        String requestUri,
        Long nextPageId,
        int pageSize,
        Map<String, String> queryParams
    ) {
        StringBuilder urlBuilder = new StringBuilder();
        urlBuilder
            .append(baseUrl)
            .append("/")
            .append(requestUri)
            .append("?pageSize=")
            .append(pageSize)
            .append("&startPageId=")
            .append(nextPageId);
        if (queryParams != null && !queryParams.isEmpty()) {
            for (Map.Entry<String, String> entry : queryParams.entrySet()) {
                urlBuilder.append("&").append(entry.getKey()).append("=").append(entry.getValue());
            }
        }

        return urlBuilder.toString();
    }

}
