package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.Guest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class GuestDaoMapTest extends GuestDaoTest {

    @BeforeEach
    void setUp() {
        uut = new GuestDaoMap();
    }

    @AfterEach
    void tearDown() {
        this.uut = null;
    }

    @Test
    void createGuest() {
        super.createGuestDaoTest();
    }

    @Test
    void getGuests() {
        super.getGuestsDaoTest();
    }

    @Test
    void getGuest_id() {
        super.getGuestDao_id();
    }

    @Test
    void getGuest_name() {
        super.getGuestDao_name();
    }

    @Test
    void updateGuest() {
        super.updateGuestDao();
    }

    @Test
    void deleteGuests() {
        super.deleteGuestsDao();
    }

    @Override
    protected void setUpGetGuestsMock(List<Guest> testGuestList){
        GuestDaoMap guestDaoMap = (GuestDaoMap) uut;
        guestDaoMap.saveAll(testGuestList);
    }

    protected Guest setUpGetGuestIdMock(int id) {
        GuestDaoMap guestDaoMap = (GuestDaoMap) uut;
        Guest testGuest = getTestGuest();
        testGuest.setId(id);
        guestDaoMap.save(testGuest);
        return testGuest;
    }

    @Override
    protected Guest setUpGetGuestNameMock(int id) {
        GuestDaoMap guestDaoMap = (GuestDaoMap) uut;
        Guest testGuest = getTestGuest();
        testGuest.setId(id);
        guestDaoMap.save(testGuest);
        return testGuest;
    }

    @Override
    protected void setUpDeleteGuestsMock(boolean isEmpty, long expected) {
        List<Guest> guests = new ArrayList<>();
        if(!isEmpty){
            guests.addAll(getTestGuestList(expected + 1));
        }
        GuestDaoMap guestDaoMap = (GuestDaoMap) uut;
        guestDaoMap.saveAll(guests);
    }

    @Override
    protected void setUpdateGuestMock(Guest updatedGuest) {
        GuestDaoMap guestDaoMap = (GuestDaoMap) uut;
        guestDaoMap.save(updatedGuest);
    }

}