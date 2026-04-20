package com.matt.dto.request;

import lombok.Data;
import java.math.BigDecimal;

/**
 * Query parameters are optional. Null values will be ignored in the query.
 */
@Data
public class AccountQueryRequest {
    private Long creatorId;
    private Long userId;
    private String coin;
    private BigDecimal amountMin;
    private BigDecimal amountMax;
    private Long createdAtBefore;
    private Long createdAtAfter;
}
