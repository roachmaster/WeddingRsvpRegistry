package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.data.GuestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuestDaoRepoTest extends GuestDaoTest {
    @Mock
    GuestRepository guestRepository;
    
    @BeforeEach
    void setUp() {
        assertNotNull(this.guestRepository);
        this.uut = new GuestDaoRepo(this.guestRepository);
    }

    @AfterEach
    void tearDown() {
        this.uut = null;
        this.guestRepository = null;
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

    @Test
    void deleteGuests_fail() {
        super.deleteGuestsDao_fail();
    }

    @Override
    protected void setUpCreateGuestMock(Guest testGuestData) {
        when(this.guestRepository.save(any())).thenReturn(testGuestData);
    }

    @Override
    protected void setUpGetGuestsMock(List<Guest> testGuestList) {
        when(this.guestRepository.findAll()).thenReturn(testGuestList);
    }

    @Override
    protected Guest setUpGetGuestIdMock(int id) {
        Guest testGuest = getTestGuest();
        testGuest.setId(id);
        Optional<Guest> optionalGuest = Optional.of(testGuest);
        when(this.guestRepository.findById(anyInt())).thenReturn(optionalGuest);
        return testGuest;
    }

    @Override
    protected Guest setUpGetGuestNameMock(int id) {
        Guest testGuest = getTestGuest(id);
        when(this.guestRepository.findByName(anyString())).thenReturn(testGuest);
        return testGuest;
    }

    @Override
    protected void setUpdateGuestMock(Guest updatedGuest) {
        when(this.guestRepository.findByName(anyString())).thenReturn(getTestGuest());
        when(this.guestRepository.save(any())).thenReturn(updatedGuest);
    }

    @Override
    protected void setUpDeleteGuestsMock(boolean isEmpty, long expected) {
        List<Guest> guests = new ArrayList<>();
        if(!isEmpty){
            guests.addAll(getTestGuestList(expected + 1));
        }
        when(this.guestRepository.findAll()).thenReturn(guests);
    }
}