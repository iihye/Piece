package com.ssafy.piece.global.dto;

import com.ssafy.piece.global.util.UrlGenerator;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageResponse<T> {

    private List<T> dataList;
    private String nextPage;

    public static <T> PageResponse<T> create(List<T> dataList, boolean hasNextPage,
        String baseUrl, String requestUri, Long nextPageId, int pageSize,
        Map<String, String> queryParams) {
        String nextPageUrl =
            hasNextPage ? UrlGenerator.generateNextPageUrl(
                baseUrl,
                requestUri,
                nextPageId,
                pageSize,
                queryParams) : null;
        
        return PageResponse.<T>builder()
            .dataList(dataList)
            .nextPage(nextPageUrl)
            .build();
    }
}
