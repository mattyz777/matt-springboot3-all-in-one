package com.matt.service;

import java.util.Map;

public interface JwtService {
    String generateToken(String userId, Integer age);

    Map<String, Object> parseToken(String token);
}