package com.matt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("JwtUtil 单元测试")
class JwtUtilTest {

    private static PrivateKey privateKey;
    private static PublicKey publicKey;

    @BeforeAll
    static void setUp() throws Exception {
        try (InputStream privateStream = JwtUtilTest.class.getResourceAsStream("/certs/private.pem");
             InputStream publicStream = JwtUtilTest.class.getResourceAsStream("/certs/public.pem")) {
            privateKey = JwtUtil.loadPrivateKey(privateStream);
            publicKey = JwtUtil.loadPublicKey(publicStream);
        }
    }

    @DisplayName("parseToken - 成功解析 token 并获取 userId 和 age")
    @Test
    void testParseToken_Success() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", "user123");
        claims.put("age", 25);

        String token = JwtUtil.generateToken("user123", claims, 3600000L, privateKey);
        Claims parsedClaims = JwtUtil.parseToken(token, publicKey);

        assertEquals("user123", parsedClaims.getSubject());
        assertEquals("user123", parsedClaims.get("userId"));
        assertEquals(25, parsedClaims.get("age"));
    }

    @DisplayName("parseToken - 篡改的 token 应抛出异常")
    @Test
    void testParseToken_TamperedToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", "user123");

        String token = JwtUtil.generateToken("user123", claims, 3600000L, privateKey);
        String tamperedToken = token.substring(0, token.length() - 5) + "abcde";

        assertThrows(JwtException.class, () -> {
            JwtUtil.parseToken(tamperedToken, publicKey);
        });
    }
}