package com.matt.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.matt.dto.request.PagingRequestDTO;
import com.matt.dto.response.PagingResponseDTO;
import com.matt.dto.response.ResponseDTO;
import com.matt.entity.Account;
import com.matt.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseDTO<Boolean> createAccount(@RequestBody Account account) {
        boolean result = accountService.addAccount(account);
        return ResponseDTO.success(result);
    }

    @GetMapping("/{id}")
    public ResponseDTO<Account> getAccount(@PathVariable Long id) {
        Account account = accountService.getById(id);
        return ResponseDTO.success(account);
    }

    @PutMapping("/{id}")
    public ResponseDTO<Boolean> updateAccount(@PathVariable Long id, @RequestBody Account account) {
        account.setId(id);
        boolean result = accountService.updateById(account);
        return ResponseDTO.success(result);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Boolean> deleteAccount(@PathVariable Long id) {
        boolean result = accountService.removeById(id);
        return ResponseDTO.success(result);
    }

    @GetMapping("/user/{userId}")
    public ResponseDTO<List<Account>> getAccountsByUserId(@PathVariable Long userId) {
        List<Account> accounts = accountService.getAccountsByUserId(userId);
        return ResponseDTO.success(accounts);
    }

    @GetMapping("/user/{userId}/coin/{coin}")
    public ResponseDTO<Account> getAccountByUserIdAndCoin(@PathVariable Long userId, @PathVariable String coin) {
        Account account = accountService.getAccountByUserIdAndCoin(userId, coin);
        return ResponseDTO.success(account);
    }

    @PostMapping("/page")
    public ResponseDTO<Page<Account>> getPage(@RequestBody PagingRequestDTO<Void> pagingRequest) {
        Page<Account> page = accountService.getAccountPage(
                pagingRequest.getCurrentPage(),
                pagingRequest.getPageSize()
        );

//        PagingResponseDTO<Account> pagingResponse = new PagingResponseDTO<>(
//                (int) page.getCurrent(),
//                (int) page.getSize(),
//                page.getTotal(),
//                page.getRecords()
//        );

        return ResponseDTO.success(page);
    }

    @PostMapping("/page/user/{userId}")
    public ResponseDTO<PagingResponseDTO<Account>> getPageByUserId(
            @PathVariable Long userId,
            @RequestBody PagingRequestDTO<Void> pagingRequest) {
        Page<Account> page = accountService.getAccountPageByUserId(
                userId,
                pagingRequest.getCurrentPage(),
                pagingRequest.getPageSize()
        );

        PagingResponseDTO<Account> pagingResponse = new PagingResponseDTO<>(
                (int) page.getCurrent(),
                (int) page.getSize(),
                page.getTotal(),
                page.getRecords()
        );

        return ResponseDTO.success(pagingResponse);
    }
}
