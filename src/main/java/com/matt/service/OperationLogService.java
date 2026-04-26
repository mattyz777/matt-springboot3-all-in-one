package com.matt.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.matt.entity.OperationLog;

public interface OperationLogService extends IService<OperationLog>  {
    void createOperationLog(String message);
    void createOperationLogRollback(String message);
    void createOperationLogNewTransaction(String message);
    void createOperationLogNewTransactionRollback(String message);
}
