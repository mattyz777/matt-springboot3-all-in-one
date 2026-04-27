package com.matt.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("tb_transaction")
public class Transaction extends BaseModel implements Serializable {
    /**
     * Associated account ID
     */
    @TableField("account_id")
    private Long accountId;

    /**
     * Transaction type: 1-CREDIT (deposit), 2-DEBIT (withdrawal)
     */
    @TableField("type")
    private Integer type;

    /**
     * Transaction before amount
     */
    @TableField("amount_before")
    private BigDecimal amountBefore;
    
    /**
     * Transaction amount
     */
    @TableField("amount")
    private BigDecimal amount;
}
