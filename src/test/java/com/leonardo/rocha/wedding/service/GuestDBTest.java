package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.data.GuestRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GuestDBTest {
    @InjectMocks
    public GuestDB uut;

    @Mock
    GuestRepository guestRepository;

    private static String TEST_GUEST_NAME = "guest";

    private static int TEST_MAX_GUEST = 5;

    @Test
    public void createGuest(){
        Guest expectedGuest = getTestGuest();
        when(guestRepository.save(any(Guest.class))).thenReturn(getTestGuest());
        Guest createdGuest = uut.createGuest(TEST_GUEST_NAME,TEST_MAX_GUEST);
        assert Objects.nonNull(createdGuest);
        assert expectedGuest.equals(createdGuest);
    }

    @Test
    public void getGuests(){
        int size = 5;
        List<Guest> expectedGuestList = getTestGuestListOf(size);
        when(guestRepository.findAll()).thenReturn(getTestGuestListOf(size));
        List<Guest> guestList = uut.getGuests();
        assert Objects.nonNull(guestList) && guestList.size() > 0;
        assert expectedGuestList.equals(guestList);
    }

    @Test
    public void getGuest(){
        Guest expectedGuest = getTestGuest();
        when(guestRepository.findByName(anyString())).thenReturn(getTestGuest());
        Guest guest = uut.getGuest(TEST_GUEST_NAME);
        assert Objects.nonNull(guest);
        assert expectedGuest.equals(guest);
    }

    @Test
    public void updateGuest(){
        Guest expected = getTestGuest();
        int updatedConfirmedGuestsGoing = 4;
        expected.setGoing(true);
        expected.setConfirmedGuest(updatedConfirmedGuestsGoing);

        Guest updatedGuest = getTestGuest();
        when(guestRepository.findByName(anyString())).thenReturn(updatedGuest);
        when(guestRepository.save(any(Guest.class))).thenReturn(updatedGuest);
        Guest guest = uut.updateGuest(TEST_GUEST_NAME,true, updatedConfirmedGuestsGoing);

        assert Objects.nonNull(guest);
        assert expected.equals(guest);
    }

    @Test
    public void deleteGuests(){
        long expectedSize = 0;

        when(guestRepository.findAll()).thenReturn(new ArrayList<>());
        long guestsSize = uut.deleteGuests();
        assert expectedSize == guestsSize;
    }

    private List<Guest> getTestGuestListOf(int size) {
        List<Guest> guestList = new ArrayList<>(size);
        for(int i = 0; i < size; i++){
            guestList.add(getTestGuest(TEST_GUEST_NAME + "_" + i, TEST_MAX_GUEST + i));
        }
        return guestList;
    }

    private Guest getTestGuest(){
        return getTestGuest(TEST_GUEST_NAME,TEST_MAX_GUEST);
    }

    private Guest getTestGuest(String name, int maxGuest){
        return new Guest(name, maxGuest);
    }
}