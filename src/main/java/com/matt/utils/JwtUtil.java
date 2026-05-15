package com.matt.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

public final class JwtUtil {

    private JwtUtil() {
    }

    /**
     * 从 PEM 编码的 PKCS#8 私钥文件中解析并生成 RSA 私钥对象
     */
    public static PrivateKey loadPrivateKey(InputStream inputStream) throws Exception {
        String key = readPem(inputStream)
                .replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Decoders.BASE64.decode(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        return keyFactory.generatePrivate(spec);
    }

    /**
     * 从 PEM 编码的 X.509 SubjectPublicKeyInfo 格式公钥文件中，解析并生成 RSA 公钥对象
     */
    public static PublicKey loadPublicKey(InputStream inputStream) throws Exception {
        String key = readPem(inputStream)
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s", "");

        byte[] keyBytes = Decoders.BASE64.decode(key);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        return keyFactory.generatePublic(spec);
    }

    private static String readPem(InputStream inputStream) throws Exception {
        try (inputStream) {
            return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    public static String generateToken(
            String subject,
            Map<String, Object> claims,
            long expirationMillis,
            PrivateKey privateKey
    ) {
        Instant now = Instant.now();
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(expirationMillis)))
                .signWith(privateKey)
                .compact();
    }

    public static Claims parseToken(String token, PublicKey publicKey) {
        return Jwts.parser()
                .verifyWith(publicKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}