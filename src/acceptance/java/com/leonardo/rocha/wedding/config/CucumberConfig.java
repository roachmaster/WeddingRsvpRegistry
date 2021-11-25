package com.leonardo.rocha.wedding.config;

import com.leonardo.rocha.wedding.WeddingRsvpRegistryApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CucumberConfig {
    @Bean
    public WeddingRsvpRegistryApiClient weddingRsvpRegistryApiClient(){
        return new WeddingRsvpRegistryApiClient();
    }
}
