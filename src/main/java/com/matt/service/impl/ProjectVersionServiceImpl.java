package com.matt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.matt.constant.Constant;
import com.matt.entity.ProjectVersion;
import com.matt.repository.ProjectVersionRepository;
import com.matt.service.OperationLogService;
import com.matt.service.ProjectVersionService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Slf4j
@Service
@AllArgsConstructor
public class ProjectVersionServiceImpl extends ServiceImpl<ProjectVersionRepository, ProjectVersion> implements ProjectVersionService {
    private final OperationLogService operationLogService;

    @Transactional(rollbackFor = Exception.class)
    public void createProjectVersion(String version) {
        log.info("createProjectVersion: {}",version );

        ProjectVersion projectVersion = new ProjectVersion();
        projectVersion.setVersion(version);
        projectVersion.setCreatedBy(Constant.USER_ADMIN);

        super.save(projectVersion);

        operationLogService.createOperationLog(version);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createProjectVersionRollbackAllTriggeredOnProjectVersion(String version) {
        log.info("createProjectVersionRollbackAllTriggeredOnProjectVersion: {}",version );

        operationLogService.createOperationLog(version);

        ProjectVersion projectVersion = new ProjectVersion();
        projectVersion.setVersion(version);
        projectVersion.setCreatedBy(Constant.USER_ADMIN);

        super.save(projectVersion);

        throw new RuntimeException("");
    }

    @Transactional(rollbackFor = Exception.class)
    public void createProjectVersionRollbackAllTriggeredOnOperationLog(String version) {
        log.info("createProjectVersionRollbackAllTriggeredOnOperationLog: {}",version );

        ProjectVersion projectVersion = new ProjectVersion();
        projectVersion.setVersion(version);
        projectVersion.setCreatedBy(Constant.USER_ADMIN);

        super.save(projectVersion);

        operationLogService.createOperationLogRollback(version);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createProjectVersionRollbackPartialProject(String version) {
        log.info("createProjectVersionRollbackPartialProject: {}",version );

        operationLogService.createOperationLogNewTransaction(version);

        ProjectVersion projectVersion = new ProjectVersion();
        projectVersion.setVersion(version);
        projectVersion.setCreatedBy(Constant.USER_ADMIN);

        super.save(projectVersion);

        String message = "createProjectVersionRollbackPartialProject:" + version;
        log.error(message);
        throw new RuntimeException(message);
    }

    @Transactional(rollbackFor = Exception.class)
    public void createProjectVersionRollbackPartialOperationLog(String version) {
        log.info("createProjectVersionRollbackPartialOperationLog: {}",version );

        ProjectVersion projectVersion = new ProjectVersion();
        projectVersion.setVersion(version);
        projectVersion.setCreatedBy(Constant.USER_ADMIN);

        super.save(projectVersion);

        operationLogService.createOperationLogNewTransactionRollback(version);
    }
}
