package com.github.supercoding.config.security;

import com.github.supercoding.web.filters.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // **HTTP 헤더 설정**
                // `X-Frame-Options`을 `SAMEORIGIN`으로 설정하여 동일 출처의 페이지에서만 iframe에 포함되도록 허용.
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))

                // **CSRF 비활성화**
                // CSRF(Cross-Site Request Forgery) 방어를 비활성화. REST API는 주로 무상태 방식이므로 CSRF를 끌 필요가 있음.
                .csrf(csrf -> csrf.disable())

                // **세션 관리**
                // Spring Security의 세션 관리를 무상태(stateless)로 설정.
                // 이는 REST API 설계 원칙에 맞추기 위한 것으로, 서버가 세션 상태를 유지하지 않음.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // **요청에 대한 권한(인가) 설정**
                .authorizeHttpRequests(authorize -> authorize
                        // `/resources/static/**` 및 `/v1/api/sign/*` 경로는 모든 사용자에게 허용.
                        .requestMatchers("/resources/static/**", "/v1/api/sign/*", "/api/items/**").permitAll()
                        // `/v1/api/air-reservation/*` 경로는 ROLE_USER 권한이 있는 사용자만 접근 가능.
                        .requestMatchers("/v1/api/air-reservation/*").hasRole("USER")
                        // 위에서 명시되지 않은 모든 요청은 인증이 필요함.
                        .anyRequest().authenticated()
                )
                // **예외 처리**
                .exceptionHandling(exceptions -> exceptions
                        // 인증 실패 처리
                        // 인증되지 않은 사용자가 보호된 리소스에 접근하려고 하면 `CustomAuthenticationEntryPoint`를 실행.
                        .authenticationEntryPoint(new CustomAuthenticationEntryPoint())

                        // 인가 실패 처리
                        // 권한이 부족한 사용자가 리소스에 접근하려고 하면 `CustomerAccessDeniedHandler`를 실행.
                        .accessDeniedHandler(new CustomerAccessDeniedHandler())
                )

                // **필터 설정**
                // `JwtAuthenticationFilter`를 `UsernamePasswordAuthenticationFilter` 이전에 실행하도록 설정.
                // JWT 인증 필터가 우선 실행되어 토큰을 검사하고, 사용자 인증 정보를 설정함.
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);

        // 설정된 SecurityFilterChain을 반환.
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:63342"));
        configuration.setAllowCredentials(true); // token 주고 받을 때 사용
        configuration.addExposedHeader("X-AUTH_TOKEN"); // token
        configuration.addAllowedHeader("*");
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "PATCH", "DELETE", "OPTIONS"));
        configuration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}