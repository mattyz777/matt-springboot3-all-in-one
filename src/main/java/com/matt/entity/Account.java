package com.matt.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@TableName("tb_account")
public class Account extends BaseModel implements Serializable {
    /**
     * User ID associated with this account
     */
    private Long userId;

    /**
     * Cryptocurrency symbol (e.g., BTC, ETH, USDT)
     */
    private String coin;

    /**
     * Account balance with high precision
     */
    private BigDecimal amount;
}

