package com.matt.controller;

import com.matt.dto.request.AccountCreateRequest;
import com.matt.dto.request.AccountQueryRequest;
import com.matt.dto.request.AccountUpdateRequest;
import com.matt.dto.request.PagingRequest;
import com.matt.dto.response.AccountResponse;
import com.matt.dto.response.PagingResponse;
import com.matt.dto.response.ResponseDTO;
import com.matt.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @PostMapping
    public ResponseDTO<Long> createAccount(@RequestBody AccountCreateRequest account) {
        Long result = accountService.createAccount(account);
        return ResponseDTO.success(result);
    }

    @PutMapping("/{id}")
    public ResponseDTO<Boolean> updateAccount(@PathVariable Long id, @RequestBody AccountUpdateRequest account) {
        boolean result = accountService.updateAccount(id, account);
        return ResponseDTO.success(result);
    }

    @GetMapping("/{id}")
    public ResponseDTO<AccountResponse> getAccount(@PathVariable Long id) {
        AccountResponse account = accountService.getAccountById(id);
        return ResponseDTO.success(account);
    }

    @DeleteMapping("/{id}")
    public ResponseDTO<Boolean> deleteAccount(@PathVariable Long id) {
        boolean result = accountService.removeById(id);
        return ResponseDTO.success(result);
    }

    @PostMapping("/query")
    public ResponseDTO<PagingResponse<AccountResponse>> getAccountPage(@RequestBody PagingRequest<AccountQueryRequest> request) {
        PagingResponse<AccountResponse> accounts = accountService.getAccountPage(request);
        return ResponseDTO.success(accounts);
    }
}
