package practice.fundingboost2.config.security;

import java.util.List;

public class SecurityConst {

    private SecurityConst() {
    }

    public static final List<String> ALLOWED_URLS = List.of(
        // healthcheck
        "/api",

        // login
        "/api/auth/register",
        "/api/auth/login",
        // swagger
        "/swagger-ui/**",
        "/swagger-resources/**",
        "/v3/api-docs/**");

    public static final List<String> ALLOWED_GET_URLS = List.of(
        "/api/item");
}
