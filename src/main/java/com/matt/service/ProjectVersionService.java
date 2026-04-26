package com.matt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.matt.entity.ProjectVersion;

public interface ProjectVersionService extends IService<ProjectVersion> {
    void createProjectVersion(String version);
    void createProjectVersionRollback(String version);
    void createProjectVersionRollbackPartialProject(String version);
    void createProjectVersionRollbackPartialOperationLog(String version);
}
