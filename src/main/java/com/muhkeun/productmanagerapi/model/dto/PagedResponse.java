package com.muhkeun.productmanagerapi.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PagedResponse<T> {

    @Schema(description = "현재 페이지 번호")
    private final int page;

    @Schema(description = "페이지 크기")
    private final int size;

    @Schema(description = "전체 데이터 개수")
    private final long totalElements;

    @Schema(description = "전체 페이지 수")
    private final int totalPages;

    @Schema(description = "정렬 기준")
    private final String sort;

    @Schema(description = "실제 데이터 목록")
    private final List<T> content;

    public PagedResponse(Page<T> pageData) {
        this.page = pageData.getNumber();
        this.size = pageData.getSize();
        this.totalElements = pageData.getTotalElements();
        this.totalPages = pageData.getTotalPages();
        this.sort = pageData.getSort().toString();
        this.content = pageData.getContent();
    }
}
