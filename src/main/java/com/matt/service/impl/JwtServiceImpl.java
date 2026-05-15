package com.matt.service.impl;

import com.matt.loader.ResourceLoader;
import com.matt.service.JwtService;
import com.matt.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class JwtServiceImpl implements JwtService {
    private static final long EXPIRATION_MILLIS = 3600000L;

    private final PrivateKey privateKey;
    private final PublicKey publicKey;

    public JwtServiceImpl(ResourceLoader resourceLoader) throws Exception {
        try (InputStream privateStream = resourceLoader.getPrivateKeyStream();
             InputStream publicStream = resourceLoader.getPublicKeyStream()) {
            this.privateKey = JwtUtil.loadPrivateKey(privateStream);
            this.publicKey = JwtUtil.loadPublicKey(publicStream);
        }
    }

    @Override
    public String generateToken(String userId, Integer age) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId);
        claims.put("age", age);
        return JwtUtil.generateToken(userId, claims, EXPIRATION_MILLIS, privateKey);
    }

    @Override
    public Map<String, Object> parseToken(String token) {
        Claims claims = JwtUtil.parseToken(token, publicKey);
        Map<String, Object> result = new HashMap<>();
        result.put("userId", claims.get("userId"));
        result.put("age", claims.get("age"));
        return result;
    }
}