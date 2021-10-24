package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.Guest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class GuestDaoTest {
    protected static final String GUEST_NAME = "TestName";
    protected static final int MAX_GUEST = 2;

    protected GuestDao uut;

    void createGuestDaoTest() {
        Guest testGuestData = getTestGuest();
        testGuestData.setId(1);
        setUpCreateGuestMock(testGuestData);
        Guest response = this.uut.createGuest(GUEST_NAME, MAX_GUEST);
        assertEquals(testGuestData,response);
    }

    void getGuestsDaoTest() {
        List<Guest> expected = getTestGuestList(10);
        setUpGetGuestsMock(expected);
        List<Guest> response = this.uut.getGuests();
        assertEquals(expected, response);
    }

    void getGuestDao_id() {
        int id = 10;
        Guest expectedGuest = setUpGetGuestIdMock(id);
        Guest response = this.uut.getGuest(id);
        assertEquals(expectedGuest, response);
    }

    void getGuestDao_name() {
        int id = 1;
        Guest expectedGuest = setUpGetGuestNameMock(id);
        Guest response = this.uut.getGuest(expectedGuest.getName());
        assertEquals(expectedGuest, response);
    }

    void updateGuestDao() {
        int id = 1;
        Guest expectedGuest = getTestGuest(id);
        int updatedMaxGuest = 26;
        expectedGuest.setMaxGuest(updatedMaxGuest);
        setUpdateGuestMock(expectedGuest);
        Guest updatedGuest = this.uut.updateGuest(expectedGuest.getName(),false, updatedMaxGuest);
        assertEquals(expectedGuest, updatedGuest);
    }


    void deleteGuestsDao() {
        long expected = 0;
        setUpDeleteGuestsMock(true, expected);
        long actual = this.uut.deleteGuests();
        assertEquals(expected, actual);
    }

    void deleteGuestsDao_fail() {
        long expected = 10;
        setUpDeleteGuestsMock(false, expected);
        long actual = this.uut.deleteGuests();
        assertEquals(expected, actual);
    }

    protected abstract void setUpDeleteGuestsMock(boolean isEmpty, long expected);

    protected abstract void setUpdateGuestMock(Guest updatedGuest);

    protected void setUpCreateGuestMock(Guest testGuestData){
    }

    protected abstract void setUpGetGuestsMock(List<Guest> testGuestList);

    protected abstract Guest setUpGetGuestIdMock(int id);

    protected abstract Guest setUpGetGuestNameMock(int id);

    protected Guest getTestGuest(){
        return new Guest(GUEST_NAME, MAX_GUEST);
    }
    protected List<Guest> getTestGuestList(long numGuests){
        List<Guest> guests = new ArrayList<>();
        for (int i = 1; i < numGuests; i++){
            Guest guest = getTestGuest(i);
            guests.add(guest);
        }
        return guests;
    }

    protected Guest getTestGuest(int i){
        Guest guest = new Guest("TestName" + i, 2 + i);
        guest.setId(i);
        return guest;
    }
}