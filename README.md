# matt-springboot3-all-in-one
A Spring Boot 3 template with common features

# features

## load resource file
```java
@Component
public class ResourceLoader {
    @Value("classpath:certs/private.pem")
    private Resource privateKeyResource;
}
```

## load resource in test code
```java
JwtUtilTest.class.getResourceAsStream("/certs/private.pem")
```