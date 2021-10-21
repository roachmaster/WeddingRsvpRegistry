package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.data.GuestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestDaoTest {
    @InjectMocks
    GuestDao uut;

    @Mock
    GuestRepository guestRepository;
    
    @BeforeEach
    void setUp() {
        assertNotNull(this.guestRepository);
    }

    @AfterEach
    void tearDown() {
        this.uut = null;
        this.guestRepository = null;
    }

    @Test
    void createGuest() {
        Guest testGuestData = getTestGuest();
        setUpCreateGuestMock(testGuestData);
        Guest response = this.uut.createGuest("TestName", 2);
        assertEquals(testGuestData,response);
    }

    private void setUpCreateGuestMock(Guest testGuestData) {
        when(this.guestRepository.save(any())).thenReturn(testGuestData);
    }

    @Test
    void getGuests() {
        List<Guest> expected = getTestGuestList();
        setUpGetGuestsMock(expected);
        List<Guest> response = this.uut.getGuests();
        assertEquals(expected, response);
    }

    private void setUpGetGuestsMock(List<Guest> testGuestList) {
        when(this.guestRepository.findAll()).thenReturn(testGuestList);
    }

    private Guest getTestGuest(){
        return new Guest("TestName", 25);
    }

    private Guest getTestGuest(int i){
        Guest guest = new Guest("TestName" + i, 2 + i);
        guest.setId(i);
        return guest;
    }

    private List<Guest> getTestGuestList(){
        List<Guest> guests = new ArrayList<>();
        for (int i = 1; i < 10; i++){
            Guest guest = getTestGuest(i);
            guests.add(guest);
        }
        return guests;
    }

    @Test
    void getGuest_id() {
        int id = 10;
        Guest expectedGuest = setUpGetGuestIdMock(id);
        Guest response = this.uut.getGuest(id);
        assertEquals(expectedGuest, response);
    }

    private Guest setUpGetGuestIdMock(int id) {
        Guest testGuest = getTestGuest();
        testGuest.setId(id);
        Optional<Guest> optionalGuest = Optional.of(testGuest);
        when(this.guestRepository.findById(anyInt())).thenReturn(optionalGuest);
        return testGuest;
    }

    @Test
    void getGuest_name() {
        Guest expectedGuest = setUpGetGuestNameMock();
        Guest response = this.uut.getGuest(expectedGuest.getName());
        assertEquals(expectedGuest, response);
    }

    private Guest setUpGetGuestNameMock() {
        Guest testGuest = getTestGuest();
        when(this.guestRepository.findByName(anyString())).thenReturn(testGuest);
        return testGuest;
    }

    @Test
    void updateGuest() {
        int updatedMaxGuest = 26;
        Guest expectedGuest = setUpdateGuestMock(updatedMaxGuest);
        Guest updatedGuest = this.uut.updateGuest("newName",false, updatedMaxGuest);
        assertEquals(expectedGuest, updatedGuest);
    }

    private Guest setUpdateGuestMock(int updatedMaxGuest) {
        Guest updatedGuest = getTestGuest();
        updatedGuest.setMaxGuest(updatedMaxGuest);
        when(this.guestRepository.findByName(anyString())).thenReturn(getTestGuest());
        when(this.guestRepository.save(any())).thenReturn(updatedGuest);
        return updatedGuest;
    }

    @Test
    void deleteGuests() {
        long expected = setUpDeleteGuestsMock(true);
        long actual = this.uut.deleteGuests();
        assertEquals(expected, actual);
    }

    @Test
    void deleteGuests_fail() {
        long expected = setUpDeleteGuestsMock(false);
        long actual = this.uut.deleteGuests();
        assertEquals(expected, actual);
    }

    private long setUpDeleteGuestsMock(boolean isEmpty) {
        List<Guest> guests = new ArrayList<>();
        if(!isEmpty){
            guests.add(getTestGuest());
        }
        when(this.guestRepository.findAll()).thenReturn(guests);
        return guests.size();
    }
}