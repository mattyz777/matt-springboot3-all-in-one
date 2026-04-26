package com.matt.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.matt.entity.ProjectVersion;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProjectVersionRepository extends BaseMapper<ProjectVersion> {

}