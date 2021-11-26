package com.leonardo.rocha.wedding.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.leonardo.rocha.wedding.client.WeddingRsvpRegistryApiClient;
import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.data.GuestRepository;
import com.leonardo.rocha.wedding.service.GuestDB;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@EntityScan(basePackageClasses = {Guest.class})
@EnableJpaRepositories(basePackageClasses = GuestRepository.class)
@EnableAutoConfiguration
@Import(GuestDB.class)
public class CucumberConfig {

    @Value("${kube.name}")
    String KUBE_NAME;

    @Value("${kube.port}")
    String KUBE_PORT;

    @Bean
    public WebClient.Builder webClientBuilder(){
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder){
        String baseUrl = "http://" + KUBE_NAME + ":" + KUBE_PORT + "/";
        System.out.println(baseUrl);
        return builder.baseUrl(baseUrl).build();
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
