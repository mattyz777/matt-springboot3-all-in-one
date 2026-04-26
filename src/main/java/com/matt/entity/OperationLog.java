package com.matt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_operation_log")
public class OperationLog extends BaseModel implements Serializable {
    /**
     * Cryptocurrency symbol (e.g., BTC, ETH, USDT)
     */
    private String message;
}

