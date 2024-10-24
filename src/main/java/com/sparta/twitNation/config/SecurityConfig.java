package com.sparta.twitNation.config;

import com.sparta.twitNation.config.jwt.JwtAuthenticationFilter;
import com.sparta.twitNation.config.jwt.JwtAuthorizationFilter;
import com.sparta.twitNation.config.jwt.JwtExceptionFilter;
import com.sparta.twitNation.config.jwt.JwtProcess;
import com.sparta.twitNation.util.CustomResponseUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger((getClass()));
    private final JwtProcess jwtProcess;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록");
        return new BCryptPasswordEncoder();
    }

    //jwt 필터 등록 예정
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity http) throws Exception {
            AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
            http.addFilterBefore(new JwtExceptionFilter(), JwtAuthorizationFilter.class);
            http.addFilter(new JwtAuthenticationFilter(authenticationManager, jwtProcess));
            http.addFilter(new JwtAuthorizationFilter(authenticationManager, jwtProcess));
            super.configure(http);
        }
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("디버그: filterChain 등록");
        http
                .headers(header -> header.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .csrf(AbstractHttpConfigurer::disable) //csrf 비활성화
                .cors(cors -> cors.configurationSource(configurationSource()))
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/swagger", "/swagger-ui.html", "/swagger-ui/**", "/api-docs", "/api-docs/**", "/v3/api-docs/**")
                        .permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) //jwt 사용
                .formLogin(AbstractHttpConfigurer::disable) //formLogin 비활성화
                .httpBasic(AbstractHttpConfigurer::disable) //브라우저가 팝업창으로 사용자 인증 진행하는 것 비활성화
                .with(new CustomSecurityFilterManager(), CustomSecurityFilterManager::getClass)
                //인증 실패 Exception 가로채기
                .exceptionHandling(handler -> handler.authenticationEntryPoint((request, response, e) -> {
                    log.error("인증 실패: {}", e.getMessage(), e);
                    CustomResponseUtil.fail(response,"로그인을 진행해 주세요", HttpStatus.UNAUTHORIZED);
                 }))
                //권한 실패 Exception 가로채기
                .exceptionHandling(handler -> handler.accessDeniedHandler((request, response, e) -> {
                    log.error("권한 실패: {}", e.getMessage(), e);
                    CustomResponseUtil.fail(response, "권한이 없습니다", HttpStatus.FORBIDDEN);
                }));
        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("디버그: filterChain에 configurationSource cors 설정 등록");

        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*"); // 모든 메서드 허용
        configuration.addAllowedOriginPattern("*"); //일단 모든 주소 허용
        configuration.setAllowCredentials(true); //일단 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration); //모든 주소에 대해서 cors 정책 설정
        return source;
    }


}
