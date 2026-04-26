package com.matt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("tb_project_version")
public class ProjectVersion extends BaseModel implements Serializable {
    /**
     * Cryptocurrency symbol (e.g., BTC, ETH, USDT)
     */
    private String version;
}

