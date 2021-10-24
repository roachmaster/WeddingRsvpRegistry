package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.Guest;

import java.util.List;

public interface GuestDao {
    Guest createGuest(String name, int maxGuest);
    List<Guest> getGuests();
    Guest getGuest(int id);
    Guest getGuest(String name);
    Guest updateGuest(String name, boolean going, int confirmedGuest);
    long deleteGuests();
}
