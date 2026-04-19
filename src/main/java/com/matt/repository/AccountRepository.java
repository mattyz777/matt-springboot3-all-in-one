package com.matt.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matt.entity.Account;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AccountRepository extends BaseMapper<Account> {
}
