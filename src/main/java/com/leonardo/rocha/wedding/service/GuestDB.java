package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.data.GuestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
public class GuestDB {
    private static final Logger logger = LoggerFactory.getLogger(GuestDB.class);

    private final GuestRepository guestRepository;

    @Autowired
    public GuestDB(GuestRepository GuestRepository) {
        this.guestRepository = GuestRepository;
    }

    public Guest createGuest(String name, int maxGuest) {
        Guest newGuest = new Guest(name, maxGuest);
        logger.info("Adding Guest: {}", newGuest);
        return this.guestRepository.save(newGuest);
    }

    public List<Guest> getGuests() {
        logger.info("Getting Guests from DB");
        Iterable<Guest> Guests = this.guestRepository.findAll();
        return (List<Guest>) Guests;
    }

    public Guest getGuest(String name) {
        logger.info("Getting Guests from DB");
        return this.guestRepository.findByName(name);
    }

    public Guest updateGuest(String name, boolean going,int confirmedGuest) {
        Guest guest = this.guestRepository.findByName(name);
        logger.info("Retrieved Guest from DB: {}", guest);
        if (guest.getMaxGuest() < confirmedGuest) {
            logger.info("Guest cannot confirm bringing more than their allowed Max guests");
            return null;
        }
        guest.setConfirmedGuest(confirmedGuest);
        guest.setGoing(going);
        Guest updatedGuest = this.guestRepository.save(guest);
        logger.info("Updated Guest from DB to: {}", updatedGuest);
        return updatedGuest;
    }

    public long deleteGuests() {
        this.guestRepository.deleteAll();
        Iterable<Guest> guests = this.guestRepository.findAll();
        return getNumberOfGuests(guests);
    }

    public static long getNumberOfGuests(Iterable<Guest> guests) {
        return StreamSupport.stream(guests.spliterator(), false).count();
    }
}
