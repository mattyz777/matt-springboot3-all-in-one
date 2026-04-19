package com.matt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PagingRequestDTO<T> {
    private Integer currentPage;
    private Integer pageSize;
    private T requestBody;
}
