package com.leonardo.rocha.wedding.client;

import com.leonardo.rocha.wedding.data.DeleteAllResponse;
import com.leonardo.rocha.wedding.data.DeleteGuestResponse;
import com.leonardo.rocha.wedding.data.Guest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Objects;

public class WeddingRsvpRegistryApiClient {
    private static final Logger logger = LoggerFactory.getLogger(WeddingRsvpRegistryApiClient.class);

    private final WebClient webClient;

    public WeddingRsvpRegistryApiClient(WebClient webClient){
        this.webClient = webClient;
    }

    public boolean healthCheck(){
        String stringMono = webClient.get().uri("actuator/health").retrieve().bodyToMono(String.class).block();
        return Objects.requireNonNull(stringMono).contains("UP");
    }

    public List<Guest> getGuests(){
        ParameterizedTypeReference<List<Guest>> typeReference = new ParameterizedTypeReference<List<Guest>>(){};
        return webClient.get().uri("guests").retrieve().bodyToMono(typeReference).block();
    }

    public Guest getGuest(String name){
        logger.info("getGuest name: {}", name);
        return webClient.get().uri("guest/{name}", name).retrieve().bodyToMono(Guest.class).block();
    }

    public Guest createGuest(String name, int maxGuest){
        logger.info("createGuest name: {}, maxGuests: {}", name, maxGuest);
        return webClient.post().uri("guest/create/name/{name}/maxGuest/{maxGuest}", name, maxGuest).retrieve().bodyToMono(Guest.class).block();
    }

    public DeleteAllResponse deleteGuests(){
        logger.info("deleteGuests");
        return webClient.delete().uri("guests/delete").retrieve().bodyToMono(DeleteAllResponse.class).block();
    }

    public DeleteGuestResponse deleteGuest(String name){
        logger.info("deleteGuests name: {}", name);
        return webClient.delete().uri("guest/delete/{name}", name).retrieve().bodyToMono(DeleteGuestResponse.class).block();
    }

    public Guest updateGuest(String name, boolean going , int confirmedGuest){
        logger.info("updateGuest name: {}, going: {}, confirmedGuest: {}", name, going, confirmedGuest);
        return webClient.post().uri("guest/update/name/{name}/going/{going}/confirmedGuest/{confirmedGuest}", name,going,confirmedGuest).retrieve().bodyToMono(Guest.class).block();
    }
}
