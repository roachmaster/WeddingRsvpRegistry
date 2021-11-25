package com.leonardo.rocha.wedding;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

public class WeddingRsvpRegistryApiClient {
    private static final Logger logger = LoggerFactory.getLogger(WeddingRsvpRegistryApiClient.class);

    public void healthCheck(){
        logger.info("healthCheck");
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
