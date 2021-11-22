package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.data.GuestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class GuestDao {
    private static final Logger logger = LoggerFactory.getLogger(GuestDao.class);

    private final GuestRepository guestRepository;

    @Autowired
    public GuestDao(GuestRepository GuestRepository){
        this.guestRepository = GuestRepository;
    }

    public Guest createGuest(String name, int maxGuest){
        Guest newGuest = new Guest(name, maxGuest, 0);
        logger.info("Adding Guest: {}", newGuest);
        return this.guestRepository.save(newGuest);
    }

    public List<Guest> getGuests() {
        logger.info("Getting Guests from DB");
        Iterable<Guest> Guests = this.guestRepository.findAll();
        return (List<Guest>) Guests;
    }

    public Guest getGuest(int id) {
        logger.info("Getting Guests with id {} from DB", id);
        return this.guestRepository.findById(id);
    }

    public Guest getGuest(String name) {
        logger.info("Getting Guests from DB");
        return this.guestRepository.findByName(name);
    }

    public Guest updateGuest(String name, int confirmedGuest){
        Guest guest = this.guestRepository.findByName(name);
        logger.info("Getting Guest from DB: {}", guest);
        guest.setConfirmedGuest(confirmedGuest);
        if(guest.getMaxGuest() < guest.getConfirmedGuest()) {
            logger.info("Guest cannot confirm bringing more than their allowed Max guests");
            return null;
        }
        Guest updatedGuest = this.guestRepository.save(guest);
        logger.info("Updated Guest from DB to: {}", updatedGuest);
        return updatedGuest;
    }

    public long deleteGuests() {
        this.guestRepository.deleteAll();
        Iterable<Guest> guests = this.guestRepository.findAll();
        return getNumberOfGuests(guests);
    }

    public static long getNumberOfGuests(Iterable<Guest> guests){
        return StreamSupport.stream(guests.spliterator(), false).count();
    }

}
