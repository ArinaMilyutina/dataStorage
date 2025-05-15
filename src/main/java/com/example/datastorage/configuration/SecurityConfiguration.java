package com.example.datastorage.configuration;

import com.example.datastorage.jwt.JWTTokenFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JWTTokenFilter jwtTokenFilter;
    private static final String LOGIN_ENDPOINT = "/user/login";
    private static final String CLIENT_ENDPOINT = "/client/**";
    private static final String APP_ENDPOINT = "/application/**";
    private static final String DOCUMENT_ENDPOINT = "/document/**";

    private static final String REG_ENDPOINT_USER = "/user/reg";
    private static final String REG_ENDPOINT_ADMIN = "/user/reg/admin";
    private static final String DB = "/db/**";

    private static final String[] PUBLIC_URLS = {
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-resources/**",
            "configuration/**",
            "webjars/**",
            "/*.html",
            "/**/*.html",
            "/**/*.css",
            "/**/*.js"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .cors() // Включаем CORS
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(LOGIN_ENDPOINT).permitAll()
                .antMatchers(CLIENT_ENDPOINT).permitAll()
                .antMatchers(APP_ENDPOINT).permitAll()
                .antMatchers(DOCUMENT_ENDPOINT).permitAll()
                .antMatchers("/h2-console/**").permitAll()
                .antMatchers(REG_ENDPOINT_USER).permitAll()
                .antMatchers(REG_ENDPOINT_ADMIN).permitAll()
                .antMatchers(HttpMethod.GET, PUBLIC_URLS).permitAll()
                .antMatchers(DB).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.headers().frameOptions().sameOrigin();
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // Разрешаем доступ с порта 3000
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS")); // Разрешаем методы
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type")); // Разрешаем заголовки
        configuration.setAllowCredentials(true); // Разрешаем отправку куки
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); // Применяем конфигурацию ко всем маршрутам
        return source;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}