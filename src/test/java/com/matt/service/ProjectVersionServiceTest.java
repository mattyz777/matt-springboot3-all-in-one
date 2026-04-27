package com.matt.service;

import com.matt.constant.Constant;
import com.matt.entity.OperationLog;
import com.matt.entity.ProjectVersion;
import com.matt.repository.OperationLogRepository;
import com.matt.repository.ProjectVersionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DisplayName("Project version Transaction Test")
public class ProjectVersionServiceTest {

    @Autowired
    private ProjectVersionService projectVersionService;

    @Autowired
    private OperationLogService operationLogService;

    @Autowired
    private ProjectVersionRepository projectVersionRepository;

    @Autowired
    private OperationLogRepository operationLogRepository;

    @Test
    @Order(1)
    @DisplayName("createProjectVersion - should create project version and operation log successfully")
    void testCreateProjectVersion() {
        String version = "v1.0.0";
        projectVersionService.createProjectVersion(version);

        // verify project version
        List<ProjectVersion> versions = projectVersionService.list();
        assertEquals(1, versions.size());
        assertEquals(version, versions.getFirst().getVersion());

        // verify operation log
        List<OperationLog> operationLogs = operationLogService.list();
        assertEquals(1, operationLogs.size());
        assertEquals(version, operationLogs.getFirst().getMessage());
    }

    @Test
    @Order(2)
    @DisplayName("createProjectVersionRollbackAllTriggeredOnProjectVersion - should rollback both project version and operation log on exception")
    void testCreateProjectVersionRollbackAllTriggeredOnProjectVersion() {
        String version = "v2.0.0";

        assertThrows(RuntimeException.class, () -> {
            projectVersionService.createProjectVersionRollbackAllTriggeredOnProjectVersion(version);
        });

        List<ProjectVersion> versions = projectVersionService
                .list()
                .stream()
                .filter(v -> v.getVersion().equals(version))
                .toList();
        assertEquals(0, versions.size());

        List<OperationLog> logs = operationLogService
                .list()
                .stream()
                .filter(v -> v.getMessage().equals(version))
                .toList();;
        assertEquals(0, logs.size());
    }

    @Test
    @Order(3)
    @DisplayName("createProjectVersionRollbackAllTriggeredOnOperationLog - should rollback both project version and operation log on exception")
    void testCreateProjectVersionRollbackAllTriggeredOnOperationLog() {
        String version = "v3.0.0";

        assertThrows(RuntimeException.class, () -> {
            projectVersionService.createProjectVersionRollbackAllTriggeredOnOperationLog(version);
        });

        List<ProjectVersion> versions = projectVersionService
                .list()
                .stream()
                .filter(v -> v.getVersion().equals(version))
                .toList();
        assertEquals(0, versions.size());

        List<OperationLog> logs = operationLogService
                .list()
                .stream()
                .filter(v -> v.getMessage().equals(version))
                .toList();;
        assertEquals(0, logs.size());
    }

    @Test
    @Order(4)
    @DisplayName("createProjectVersionRollbackPartialProject - should rollback project version but keep operation log in new transaction")
    void testCreateProjectVersionRollbackPartialProject() {
        String version = "v4.0.0";

        assertThrows(RuntimeException.class, () -> {
            projectVersionService.createProjectVersionRollbackPartialProject(version);
        });

        List<ProjectVersion> versions = projectVersionService
                .list()
                .stream()
                .filter(v -> v.getVersion().equals(version))
                .toList();
        assertEquals(0, versions.size());

        List<OperationLog> logs = operationLogService
                .list()
                .stream()
                .filter(v -> v.getMessage().equals(version))
                .toList();;
        assertEquals(1, logs.size());
    }

    @Test
    @Order(5)
    @DisplayName("createProjectVersionRollbackPartialOperationLog - should keep project version but rollback operation log")
    void testCreateProjectVersionRollbackPartialOperationLog() {
        String version = "v5.0.0";

        assertThrows(RuntimeException.class, () -> {
            projectVersionService.createProjectVersionRollbackPartialOperationLog(version);
        });

        List<ProjectVersion> versions = projectVersionService
                .list()
                .stream()
                .filter(v -> v.getVersion().equals(version))
                .toList();
        assertEquals(0, versions.size());

        List<OperationLog> logs = operationLogService
                .list()
                .stream()
                .filter(v -> v.getMessage().equals(version))
                .toList();;
        assertEquals(0, logs.size());
    }
}
