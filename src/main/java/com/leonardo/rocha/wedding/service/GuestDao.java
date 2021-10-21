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
        Guest newGuest = new Guest(name, maxGuest);
        logger.info("Adding Guest: {}", newGuest);
        return this.guestRepository.save(newGuest);
    }

    public List<Guest> getGuests() {
        logger.info("Getting Guests from DB");
        Iterable<Guest> guests = this.guestRepository.findAll();
        return (List<Guest>) guests;
    }

    public Guest getGuest(int id) {
        logger.info("Getting Guests with id {} from DB", id);
        Optional<Guest> optionalGuest = this.guestRepository.findById(id);
        Guest guest = null;
        if (optionalGuest.isPresent()) {
            guest = optionalGuest.get();
        }
        return guest;
    }

    public Guest getGuest(String name) {
        logger.info("Getting Guests from DB");
        return this.guestRepository.findByName(name);
    }

    public Guest updateGuest(String name, boolean going, int confirmedGuest){
        Guest guest = this.guestRepository.findByName(name);
        logger.info("Getting Guest from DB: {}", guest);
        guest.setGoing(going);
        return saveGuest(guest, confirmedGuest);
    }

    private Guest saveGuest(Guest guest, int confirmedGuest){
        if(guest.isGoing()){
            return validateAndSaveGoingGuest(guest, confirmedGuest);
        } else {
            return this.guestRepository.save(guest);
        }
    }

    private Guest validateAndSaveGoingGuest(Guest guest, int confirmedGuest){
        if(guest.getMaxGuest() >= confirmedGuest) {
            return saveGoingGuest(guest, confirmedGuest);
        } else {
            logger.info("Guest cannot confirm bringing {} when their allowed Max guests is {}", confirmedGuest, guest.getMaxGuest());
            return  null;
        }
    }

    private Guest saveGoingGuest(Guest guest, int confirmedGuest){
        guest.setConfirmedGuest(confirmedGuest);
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
