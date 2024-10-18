package com.sparta.twitNation.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger((getClass()));

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        log.debug("디버그 : BCryptPasswordEncoder 빈 등록");
        return new BCryptPasswordEncoder();
    }

    //추가 예정


}
