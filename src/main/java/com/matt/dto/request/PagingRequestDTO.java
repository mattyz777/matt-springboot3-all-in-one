package com.matt.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
//@NoArgsConstructor
public class PagingRequestDTO<T> {
    private Integer currentPage;
    private Integer pageSize;
    private T requestBody;
}
