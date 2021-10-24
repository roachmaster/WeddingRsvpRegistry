package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.data.GuestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class GuestDaoRepo extends AbstractGuestDao{
    private static final Logger logger = LoggerFactory.getLogger(GuestDaoRepo.class);

    private final GuestRepository guestRepository;

    @Autowired
    public GuestDaoRepo(GuestRepository GuestRepository){
        super(logger);
        this.guestRepository = GuestRepository;
    }

    @Override
    protected void deleteAll(){
        this.guestRepository.deleteAll();
    }

    @Override
    protected Guest findByName(String name){
        return this.guestRepository.findByName(name);
    }

    @Override
    protected Guest save(Guest guest){
        return this.guestRepository.save(guest);
    }

    @Override
    protected List<Guest> findAll(){
        return (List<Guest>)this.guestRepository.findAll();
    }

    @Override
    protected Optional<Guest> findById(int id){
        return this.guestRepository.findById(id);
    }
}
