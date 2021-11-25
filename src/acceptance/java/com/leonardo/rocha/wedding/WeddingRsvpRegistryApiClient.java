package com.leonardo.rocha.wedding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class WeddingRsvpRegistryApiClient {
    private static final Logger logger = LoggerFactory.getLogger(WeddingRsvpRegistryApiClient.class);

    private WebClient webClient;

    public WeddingRsvpRegistryApiClient(WebClient webClient){
        this.webClient = webClient;
    }

    public void healthCheck(){
        logger.info("healthCheck");
        String stringMono = webClient.get().uri("/actuator/health").retrieve().bodyToMono(String.class).block();
        logger.info(stringMono);
    }

    public void getGuests(){
        logger.info("getGuests");
    }

    public void getGuest(String name){
        logger.info("getGuest name: {}", name);
    }

    public void createGuest(String name, int maxGuest){
        logger.info("createGuest name: {}, maxGuests: {}", name, maxGuest);
    }

    public void deleteGuests(){
        logger.info("deleteGuests");
    }

    public void deleteGuest(String name){
        logger.info("deleteGuests name: {}", name);
    }

    public void updateGuest(String name,boolean going ,int confirmedGuest){
        logger.info("updateGuest name: {}, going: {}, confirmedGuest: {}", name, going, confirmedGuest);
    }
}
