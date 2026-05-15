package com.matt.loader;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Component
public class ResourceLoader {
    @Value("classpath:certs/private.pem")
    private Resource privateKeyResource;

    @Value("classpath:certs/public.pem")
    private Resource publicKeyResource;

    public InputStream getPrivateKeyStream() throws Exception {
        return privateKeyResource.getInputStream();
    }

    public InputStream getPublicKeyStream() throws Exception {
        return publicKeyResource.getInputStream();
    }
}