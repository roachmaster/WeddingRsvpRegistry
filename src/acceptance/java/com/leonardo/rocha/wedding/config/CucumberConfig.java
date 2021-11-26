package com.leonardo.rocha.wedding.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.leonardo.rocha.wedding.client.WeddingRsvpRegistryApiClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class CucumberConfig {

    @Bean
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        return builder.baseUrl("http://kube1:30710/").build();
    }

    @Bean
    public WeddingRsvpRegistryApiClient weddingRsvpRegistryApiClient(WebClient webClient){
        return new WeddingRsvpRegistryApiClient(webClient);
    }

    @Bean
    public ObjectWriter objectWriter(){
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writer().withDefaultPrettyPrinter();
    }
}
