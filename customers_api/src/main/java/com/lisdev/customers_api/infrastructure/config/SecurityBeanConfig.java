package com.lisdev.customers_api.infrastructure.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.lisdev.customers_api.application.port.out.PasswordEncoderPort;

@Configuration
public class SecurityBeanConfig {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    PasswordEncoderPort passwordEncoderPort(PasswordEncoder passwordEncoder) {
        return passwordEncoder::encode;
    }

}
