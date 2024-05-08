package com.ssafy.piece.global.dto;

import java.util.List;
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
        String baseUrl, String RequestUri, Long nextPageId, int pageSize) {
        String nextPageUrl =
            hasNextPage ? String.format("%s/%s?pageId=%d&pageSize=%d", baseUrl, RequestUri,
                nextPageId,
                pageSize) : null;
        return PageResponse.<T>builder()
            .dataList(dataList)
            .nextPage(nextPageUrl)
            .build();
    }
}
