package com.matt.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountResponse {
    private Long id;
    private Long userId;
    private String coin;
    private BigDecimal amount;
    private Long createdBy;
    private Long updatedBy;
    private Long createdAt;
    private Long updatedAt;
}
