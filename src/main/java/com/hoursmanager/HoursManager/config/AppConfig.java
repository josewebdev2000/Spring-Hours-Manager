package com.hoursmanager.HoursManager.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
/*
* Let Spring Send API Requests to other services
*
*  */

@Configuration
public class AppConfig
{
    @Bean
    public RestTemplate restTemplate()
    {
        return new RestTemplate();
    }
}
