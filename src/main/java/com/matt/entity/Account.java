package com.matt.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("tb_account")
public class Account implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField("user_id")
    private Long userId;

    @TableField("coin")
    private String coin;

    @TableField("amount")
    private BigDecimal amount;

    /**
     * @TableLogic logic delete - 1:deleted, 0: not deleted
     */
    @TableField("is_deleted")
    @TableLogic
    private Integer deleted;

    @TableField("created_by")
    private Long createdBy;

    @TableField("updated_by")
    private Long updatedBy;

    @TableField("created_at")
    private Long createdAt;

    @TableField("updated_at")
    private Long updatedAt;
}

