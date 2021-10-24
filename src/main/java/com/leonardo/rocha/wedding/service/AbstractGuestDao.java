package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.Guest;
import org.slf4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

public abstract class AbstractGuestDao implements GuestDao {
    private final Logger logger;

    public AbstractGuestDao(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Guest createGuest(String name, int maxGuest){
        Guest newGuest = new Guest(name, maxGuest);
        logger.info("Adding Guest: {}", newGuest);
        return save(newGuest);
    }

    @Override
    public List<Guest> getGuests() {
        logger.info("Getting Guests from DB");
        return findAll();
    }

    @Override
    public Guest getGuest(int id) {
        logger.info("Getting Guests with id {} from DB", id);
        Optional<Guest> optionalGuest = findById(id);
        Guest guest = null;
        if (optionalGuest.isPresent()) {
            guest = optionalGuest.get();
        }
        return guest;
    }

    @Override
    public Guest getGuest(String name) {
        logger.info("Getting Guests from DB");
        return findByName(name);
    }

    @Override
    public Guest updateGuest(String name, boolean going, int confirmedGuest){
        Guest guest = findByName(name);
        logger.info("Getting Guest from DB: {}", guest);
        guest.setGoing(going);
        return saveGuest(guest, confirmedGuest);
    }

    private Guest saveGuest(Guest guest, int confirmedGuest){
        if(guest.isGoing()){
            return validateAndSaveGoingGuest(guest, confirmedGuest);
        } else {
            return save(guest);
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
        Guest updatedGuest = save(guest);
        logger.info("Updated Guest from DB to: {}", updatedGuest);
        return updatedGuest;
    }

    @Override
    public long deleteGuests() {
        deleteAll();
        Iterable<Guest> guests = findAll();
        return getNumberOfGuests(guests);
    }

    public static long getNumberOfGuests(Iterable<Guest> guests){
        return StreamSupport.stream(guests.spliterator(), false).count();
    }

    protected abstract Guest save(Guest newGuest);
    protected abstract List<Guest> findAll();
    protected abstract Optional<Guest> findById(int id);
    protected abstract Guest findByName(String name);
    protected abstract void deleteAll();
}
