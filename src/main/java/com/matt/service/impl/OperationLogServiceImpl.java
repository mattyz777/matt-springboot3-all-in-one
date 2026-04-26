package com.matt.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.matt.constant.Constant;
import com.matt.entity.OperationLog;
import com.matt.repository.OperationLogRepository;
import com.matt.service.OperationLogService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.transaction.annotation.Propagation.REQUIRES_NEW;

@Slf4j
@Service
@AllArgsConstructor
public class OperationLogServiceImpl extends ServiceImpl<OperationLogRepository, OperationLog> implements OperationLogService {
    public void createOperationLog(String message) {
        log.info("createOperationLog: {}", message);

        OperationLog operationLog = new OperationLog();
        operationLog.setCreatedBy(Constant.USER_ADMIN);
        operationLog.setMessage(message);

        super.save(operationLog);
    }

    public void createOperationLogRollback(String message) {
        log.info("createOperationLogRollback: {}", message);

        OperationLog operationLog = new OperationLog();
        operationLog.setCreatedBy(Constant.USER_ADMIN);
        operationLog.setMessage(message);

        super.save(operationLog);

        String errorMessage = "createOperationLog message:" + message;
        log.error(errorMessage);
        throw new RuntimeException(errorMessage);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRES_NEW)
    public void createOperationLogNewTransaction(String message) {
        log.info("createOperationLogNewTransaction: {}", message);

        OperationLog operationLog = new OperationLog();
        operationLog.setCreatedBy(Constant.USER_ADMIN);
        operationLog.setMessage(message);
        super.save(operationLog);
    }

    @Transactional(rollbackFor = Exception.class, propagation = REQUIRES_NEW)
    public void createOperationLogNewTransactionRollback(String message) {
        log.info("createOperationLogNewTransactionRollback: {}", message);

        OperationLog operationLog = new OperationLog();
        operationLog.setCreatedBy(Constant.USER_ADMIN);
        operationLog.setMessage(message);
        super.save(operationLog);

        String errorMessage = "createOperationLogNewTransaction message:" + message;
        log.error(errorMessage);
        throw new RuntimeException(errorMessage);
    }
}
