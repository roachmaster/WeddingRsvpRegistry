package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.Guest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class GuestDaoMap extends AbstractGuestDao {
    private static final Logger logger = LoggerFactory.getLogger(GuestDaoMap.class);

    private final Map<Integer, Guest> guestMap;
    private final AtomicInteger counter;

    public GuestDaoMap() {
        super(logger);
        this.guestMap = new HashMap<>();
        counter = new AtomicInteger(0);
    }

    public void saveAll(List<Guest> guests){
        for (Guest guest: guests){
            this.guestMap.put(guest.getId(), guest);
        }
    }

    @Override
    protected Guest save(Guest newGuest) {
        if (Objects.isNull(newGuest.getId())) {
            int id = counter.incrementAndGet();
            newGuest.setId(id);
        }
        this.guestMap.put(newGuest.getId(), newGuest);
        return this.guestMap.get(newGuest.getId());
    }

    @Override
    protected List<Guest> findAll() {
        return new ArrayList<>(this.guestMap.values());
    }

    @Override
    protected Optional<Guest> findById(int id) {
        Guest guest = this.guestMap.get(id);
        return Optional.ofNullable(guest);
    }

    @Override
    protected Guest findByName(String name) {
        List<Guest> guests = new ArrayList<>(this.guestMap.values());
        for (Guest guest: guests) {
            if(guest.getName().equalsIgnoreCase(name)){
                return guest;
            }
        }
        return null;
    }

    @Override
    protected void deleteAll() {
        this.guestMap.clear();
    }
}
