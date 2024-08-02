package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@RequestMapping("/api")
public class UserInfoController {

    public static final Logger log = LoggerFactory.getLogger(UserInfoController.class);

    @GetMapping("/user")
    public Principal user(Principal principal) {
        System.out.println(principal.toString());
        return principal;
    }



    @Configuration
    public static class ResourceServerConfig {

        @Bean
        @Order(1)
        public SecurityFilterChain resourceServerSecurityFilterChain(HttpSecurity http) throws Exception {
            JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
            JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
            grantedAuthoritiesConverter.setAuthorityPrefix("SCOPE_");
            grantedAuthoritiesConverter.setAuthoritiesClaimName("scope");
            jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

            http.securityMatcher("/api/**")
                    .authorizeHttpRequests(authorizeRequests ->
                            authorizeRequests
                                    //拿着token去https://jwt.io/解析看scope是否有权限访问
                                    //这行代码的作用是，一定要拥有SCOPE_openid权限才能访问/api/user接口
                                    //.requestMatchers("/api/user").hasAuthority("SCOPE_openid")
                                    .anyRequest().authenticated()
                    )
                    .oauth2ResourceServer(oauth2ResourceServer ->
                            oauth2ResourceServer
                                    .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter))
                    );

            return http.build();
        }
    }
}