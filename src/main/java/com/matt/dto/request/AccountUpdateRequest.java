package com.matt.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;


@Data
public class AccountUpdateRequest {

    @NotNull(message = "用户ID不能为空")
    @Min(value = 1, message = "用户ID必须大于0")
    private Long userId;

    @NotBlank(message = "币种不能为空")
    private String coin;

    @NotNull(message = "金额不能为空")
    @DecimalMin(value = "0.00000001", inclusive = true, message = "金额必须大于0")
    private BigDecimal amount;
}