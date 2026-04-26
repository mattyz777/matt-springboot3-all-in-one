package com.matt.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.matt.dto.request.AccountQueryRequest;
import com.matt.entity.Account;
import com.matt.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OperationLogRepository extends BaseMapper<OperationLog> {

}