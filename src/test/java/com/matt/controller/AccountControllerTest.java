package com.matt.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matt.constant.Constant;
import com.matt.dto.request.AccountCreateRequest;
import com.matt.dto.request.AccountQueryRequest;
import com.matt.dto.request.AccountUpdateRequest;
import com.matt.dto.request.PagingRequest;
import com.matt.dto.response.AccountResponse;
import com.matt.dto.response.PagingResponse;
import com.matt.dto.response.ResponseDTO;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("AccountController Test Scenario")
public class AccountControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Order(1)
    @DisplayName("CRUD - 增删改查 全场景接口")
    void testCreateAccount() throws Exception {
        // 1. create
        AccountCreateRequest createRequest = new AccountCreateRequest();
        createRequest.setUserId(Constant.USER_ADMIN);
        createRequest.setCoin("BTC");
        createRequest.setAmount(new BigDecimal("1.5"));

        MvcResult createResult = mockMvc.perform(post("/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String createResponseJson = createResult.getResponse().getContentAsString();
        ResponseDTO<Long> createResponse = objectMapper.readValue(
                createResponseJson,
                new TypeReference<>() {}
        );

        assertNotNull(createResponse);
        assertEquals(Constant.RESPONSE_SUCCESS, createResponse.getCode());
        assertNotNull(createResponse.getData());
        assertTrue(createResponse.getData() > 0);

        // 2. get
        Long accountId = createResponse.getData();

        MvcResult getResult = mockMvc.perform(get("/account/" + accountId))
                .andExpect(status().isOk())
                .andReturn();

        String getResponseJson = getResult.getResponse().getContentAsString();
        ResponseDTO<AccountResponse> getResponse = objectMapper.readValue(
                getResponseJson,
                new TypeReference<>() {}
        );

        assertNotNull(getResponse);
        assertEquals(Constant.RESPONSE_SUCCESS, getResponse.getCode());
        assertNotNull(getResponse.getData());
        assertEquals(accountId, getResponse.getData().getId());
        assertEquals(Constant.USER_ADMIN, getResponse.getData().getUserId());
        assertEquals("BTC", getResponse.getData().getCoin());
        assertEquals(0, new BigDecimal("1.5").compareTo(getResponse.getData().getAmount()));

        // 3. update
        AccountUpdateRequest updateRequest = new AccountUpdateRequest();
        updateRequest.setUserId(Constant.USER_ADMIN_B);
        updateRequest.setCoin("ETH");
        updateRequest.setAmount(new BigDecimal("2.5"));

        MvcResult updateResult = mockMvc.perform(put("/account/" + accountId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String updateResponseJson = updateResult.getResponse().getContentAsString();
        ResponseDTO<Boolean> updateResponse = objectMapper.readValue(
                updateResponseJson,
                new TypeReference<>() {}
        );

        assertNotNull(updateResponse);
        assertEquals(Constant.RESPONSE_SUCCESS, updateResponse.getCode());
        assertTrue(updateResponse.getData());

        MvcResult getAfterUpdateResult = mockMvc.perform(get("/account/" + accountId))
                .andExpect(status().isOk())
                .andReturn();

        String getAfterUpdateJson = getAfterUpdateResult.getResponse().getContentAsString();
        ResponseDTO<AccountResponse> getAfterUpdateResponse = objectMapper.readValue(
                getAfterUpdateJson,
                new TypeReference<>() {}
        );

        assertEquals("ETH", getAfterUpdateResponse.getData().getCoin());
        assertEquals(0, new BigDecimal("2.5").compareTo(getAfterUpdateResponse.getData().getAmount()));
        assertEquals(Constant.USER_ADMIN_B, getAfterUpdateResponse.getData().getUserId());

        // 4. delete
        MvcResult deleteResult = mockMvc.perform(delete("/account/" + accountId))
                .andExpect(status().isOk())
                .andReturn();

        String deleteResponseJson = deleteResult.getResponse().getContentAsString();
        ResponseDTO<Boolean> deleteResponse = objectMapper.readValue(
                deleteResponseJson,
                new TypeReference<>() {}
        );

        assertNotNull(deleteResponse);
        assertEquals(Constant.RESPONSE_SUCCESS, deleteResponse.getCode());
        assertTrue(deleteResponse.getData());
    }

    @Test
    @Order(2)
    @DisplayName("getPage - 测试分页查询第一页（无条件）")
    void testGetPage_NoCondition() throws Exception {
        int pageSize = 5;
        PagingRequest<AccountQueryRequest> request = new PagingRequest<>(1, pageSize, new AccountQueryRequest());

        MvcResult result = mockMvc.perform(post("/account/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ResponseDTO<PagingResponse<AccountResponse>> response = objectMapper.readValue(
                responseJson,
                new TypeReference<>() {}
        );

        assertNotNull(response);
        assertEquals(Constant.RESPONSE_SUCCESS, response.getCode());
        assertNotNull(response.getData());
        assertEquals(8, response.getData().getTotal());
        assertEquals(2, response.getData().getPages());
        assertEquals(pageSize, response.getData().getRecords().size());
    }

    @Test
    @Order(3)
    @DisplayName("getPage - 测试按用户ID精确查询")
    void testGetPage_ByUserId() throws Exception {
        AccountQueryRequest queryRequest = new AccountQueryRequest();
        queryRequest.setUserId(1001L);
        PagingRequest<AccountQueryRequest> request = new PagingRequest<>(1, 10, queryRequest);

        MvcResult result = mockMvc.perform(post("/account/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ResponseDTO<PagingResponse<AccountResponse>> response = objectMapper.readValue(
                responseJson,
                new TypeReference<>() {}
        );

        assertNotNull(response);
        assertEquals(Constant.RESPONSE_SUCCESS, response.getCode());
        assertNotNull(response.getData());
        assertEquals(3, response.getData().getRecords().size());
        response.getData().getRecords().forEach(account ->
                assertEquals(1001L, account.getUserId())
        );
    }

    @Test
    @Order(4)
    @DisplayName("getPage - 测试按币种模糊查询")
    void testGetPage_ByCoinLike() throws Exception {
        AccountQueryRequest queryRequest = new AccountQueryRequest();
        queryRequest.setCoin("US");
        PagingRequest<AccountQueryRequest> request = new PagingRequest<>(1, 10, queryRequest);

        MvcResult result = mockMvc.perform(post("/account/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ResponseDTO<PagingResponse<AccountResponse>> response = objectMapper.readValue(
                responseJson,
                new TypeReference<>() {}
        );

        assertNotNull(response);
        assertEquals(Constant.RESPONSE_SUCCESS, response.getCode());
        assertNotNull(response.getData());
        assertFalse(response.getData().getRecords().isEmpty());
        response.getData().getRecords().forEach(account ->
                assertEquals("USDT", account.getCoin())
        );
    }

    @Test
    @Order(5)
    @DisplayName("getPage - 测试按金额范围查询")
    void testGetPage_ByAmountRange() throws Exception {
        AccountQueryRequest queryRequest = new AccountQueryRequest();
        queryRequest.setAmountMin(new BigDecimal("1.0"));
        queryRequest.setAmountMax(new BigDecimal("10.0"));
        PagingRequest<AccountQueryRequest> request = new PagingRequest<>(1, 10, queryRequest);

        MvcResult result = mockMvc.perform(post("/account/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ResponseDTO<PagingResponse<AccountResponse>> response = objectMapper.readValue(
                responseJson,
                new TypeReference<>() {}
        );

        assertNotNull(response);
        assertEquals(Constant.RESPONSE_SUCCESS, response.getCode());
        assertNotNull(response.getData());
        response.getData().getRecords().forEach(account -> {
            assertTrue(account.getAmount().compareTo(new BigDecimal("1.0")) >= 0);
            assertTrue(account.getAmount().compareTo(new BigDecimal("10.0")) <= 0);
        });
    }

    @Test
    @Order(6)
    @DisplayName("getPage - 测试按创建者ID查询")
    void testGetPage_ByCreatorId() throws Exception {
        AccountQueryRequest queryRequest = new AccountQueryRequest();
        queryRequest.setCreatorId(0L);
        PagingRequest<AccountQueryRequest> request = new PagingRequest<>(1, 10, queryRequest);

        MvcResult result = mockMvc.perform(post("/account/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ResponseDTO<PagingResponse<AccountResponse>> response = objectMapper.readValue(
                responseJson,
                new TypeReference<>() {}
        );

        assertNotNull(response);
        assertEquals(Constant.RESPONSE_SUCCESS, response.getCode());
        assertNotNull(response.getData());
        assertFalse(response.getData().getRecords().isEmpty());
    }

    @Test
    @Order(7)
    @DisplayName("getPage - 测试按时间范围查询")
    void testGetPage_ByTimeRange() throws Exception {
        AccountQueryRequest queryRequest = new AccountQueryRequest();
        queryRequest.setCreatedAtAfter(1745020800000L);
        queryRequest.setCreatedAtBefore(1745107200000L);
        PagingRequest<AccountQueryRequest> request = new PagingRequest<>(1, 10, queryRequest);

        MvcResult result = mockMvc.perform(post("/account/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ResponseDTO<PagingResponse<AccountResponse>> response = objectMapper.readValue(
                responseJson,
                new TypeReference<>() {}
        );

        assertNotNull(response);
        assertEquals(Constant.RESPONSE_SUCCESS, response.getCode());
        assertNotNull(response.getData());
        response.getData().getRecords().forEach(account -> {
            assertTrue(account.getCreatedAt() >= 1745020800000L);
            assertTrue(account.getCreatedAt() <= 1745107200000L);
        });
    }

    @Test
    @Order(8)
    @DisplayName("getPage - 测试组合条件查询")
    void testGetPage_CombinedConditions() throws Exception {
        AccountQueryRequest queryRequest = new AccountQueryRequest();
        queryRequest.setUserId(1002L);
        queryRequest.setCoin("BTC");
        queryRequest.setAmountMin(new BigDecimal("1.0"));
        queryRequest.setAmountMax(new BigDecimal("2.0"));
        PagingRequest<AccountQueryRequest> request = new PagingRequest<>(1, 10, queryRequest);

        MvcResult result = mockMvc.perform(post("/account/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        String responseJson = result.getResponse().getContentAsString();
        ResponseDTO<PagingResponse<AccountResponse>> response = objectMapper.readValue(
                responseJson,
                new TypeReference<>() {}
        );

        assertNotNull(response);
        assertEquals(Constant.RESPONSE_SUCCESS, response.getCode());
        assertNotNull(response.getData());
        response.getData().getRecords().forEach(account -> {
            assertEquals(1002L, account.getUserId());
            assertTrue(account.getCoin().contains("BTC"));
            assertTrue(account.getAmount().compareTo(new BigDecimal("1.0")) >= 0);
            assertTrue(account.getAmount().compareTo(new BigDecimal("2.0")) <= 0);
        });
    }

    @Test
    @Order(9)
    @DisplayName("getPage - 测试分页功能第二页")
    void testGetPage_SecondPage() throws Exception {
        PagingRequest<AccountQueryRequest> firstPageRequest = new PagingRequest<>(1, 3, new AccountQueryRequest());
        MvcResult firstResult = mockMvc.perform(post("/account/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(firstPageRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String firstResponseJson = firstResult.getResponse().getContentAsString();
        ResponseDTO<PagingResponse<AccountResponse>> firstResponse = objectMapper.readValue(
                firstResponseJson,
                new TypeReference<>() {}
        );

        PagingRequest<AccountQueryRequest> secondPageRequest = new PagingRequest<>(2, 3, new AccountQueryRequest());
        MvcResult secondResult = mockMvc.perform(post("/account/query")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(secondPageRequest)))
                .andExpect(status().isOk())
                .andReturn();

        String secondResponseJson = secondResult.getResponse().getContentAsString();
        ResponseDTO<PagingResponse<AccountResponse>> secondResponse = objectMapper.readValue(
                secondResponseJson,
                new TypeReference<>() {}
        );

        assertNotNull(secondResponse);
        assertEquals(Constant.RESPONSE_SUCCESS, secondResponse.getCode());
        assertNotNull(secondResponse.getData());

        if (firstResponse.getData().getTotal() > 3) {
            assertFalse(secondResponse.getData().getRecords().isEmpty());
            assertNotEquals(
                    firstResponse.getData().getRecords().getFirst().getId(),
                    secondResponse.getData().getRecords().getFirst().getId()
            );
        }
    }

}
