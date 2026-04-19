package com.matt.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.matt.entity.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountService extends IService<Account> {

    Account getAccountByUserIdAndCoin(Long userId, String coin);

    List<Account> getAccountsByUserId(Long userId);

    boolean updateBalance(Long accountId, BigDecimal amount);

    boolean addAccount(Account account);

    Page<Account> getAccountPage(Integer currentPage, Integer pageSize);

    Page<Account> getAccountPageByUserId(Long userId, Integer currentPage, Integer pageSize);
}
