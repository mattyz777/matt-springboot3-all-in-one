package com.matt.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PagingResponseDTO<T> {
    private Integer pageNum;
    private Integer pageSize;
    private Long total;
    private List<T> data;
}
