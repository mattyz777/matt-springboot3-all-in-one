package com.matt.dto.response;

import lombok.Data;

import java.util.List;

@Data
public class PagingResponse<T> {
    private Integer currentPage;
    private Integer pageSize;
    private Long total;
    private List<T> records;
}
