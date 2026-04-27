package com.matt.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@TableName("tb_project_version")
public class ProjectVersion extends BaseModel implements Serializable {
    /**
     * Cryptocurrency symbol (e.g., BTC, ETH, USDT)
     */
    private String version;
}

