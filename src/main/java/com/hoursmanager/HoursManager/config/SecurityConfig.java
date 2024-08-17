package com.hoursmanager.HoursManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;

/*
* Cancel Default Spring Security Behaviour that requires login creds
* */
@Configuration
public class SecurityConfig
{
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()
    {
        return (web) -> web.ignoring().anyRequest();
    }
}
