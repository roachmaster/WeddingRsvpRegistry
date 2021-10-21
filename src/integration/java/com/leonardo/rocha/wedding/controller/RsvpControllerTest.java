package com.leonardo.rocha.wedding.controller;

import com.leonardo.rocha.wedding.data.DeleteAllResponse;
import com.leonardo.rocha.wedding.data.Guest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RsvpControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(RsvpControllerTest.class);

    @Autowired
    RsvpController cut;

    @Before
    public void setUp() throws Exception {
        cut.deleteGuests();
    }

    @After
    public void tearDown() throws Exception {
        cut.deleteGuests();
    }

    @Test
    public void creatingGuestInviteTest(){
        String name = "Emily";
        int maxGuest = 4;
        ResponseEntity<Guest> newGuestInvite = cut.createGuest(name, maxGuest);
        Guest createdGuest = newGuestInvite.getBody();
        assertEquals(HttpStatus.CREATED, newGuestInvite.getStatusCode());
        assert createdGuest != null;
        assertEquals(name, createdGuest.getName());
        assertEquals(maxGuest, createdGuest.getMaxGuest());
    }

    @Test
    public void getGuestsTest(){
        Guest guest1 = cut.createGuest("Leo", 3).getBody();
        Guest guest2 = cut.createGuest("Max", 5).getBody();

        ResponseEntity<List<Guest>> guestsResponse = cut.getGuests();
        List<Guest> guests = assertGetGuestsResponse(guestsResponse);
        assertTrue(guests.contains(guest1));
        assertTrue(guests.contains(guest2));
    }

    private List<Guest> assertGetGuestsResponse(ResponseEntity<List<Guest>> guestsResponse){
        List<Guest> guests = guestsResponse.getBody();
        assertEquals(HttpStatus.OK, guestsResponse.getStatusCode());
        assert guests != null;
        assertEquals(2, guests.size());
        return guests;
    }

    @Test
    public void deleteUsers(){
        getGuestsTest();
        assertGetGuestsResponse(cut.getGuests());
        ResponseEntity<DeleteAllResponse> deleteGuestsResponse = cut.deleteGuests();
        assertEquals(HttpStatus.OK, deleteGuestsResponse.getStatusCode());
        DeleteAllResponse response = deleteGuestsResponse.getBody();
        assert response != null;
        assertEquals(0, response.getNumOfGuests());
    }

    @Test
    public void updateGuest(){
        
    }

    @Test
    public void integrationTest(){
        ResponseEntity<Guest> createResponse = cut.createGuest("Leo", 5);
        logger.info(Objects.requireNonNull(createResponse.getBody()).toString());
        ResponseEntity<Guest> createResponse1 = cut.createGuest("Emily", 6);
        logger.info(Objects.requireNonNull(createResponse1.getBody()).toString());
        ResponseEntity<Guest> getResponse = cut.getGuest("Leo");
        logger.info(Objects.requireNonNull(getResponse.getBody()).toString());
        ResponseEntity<List<Guest>> response = cut.getGuests();
        logger.info(Objects.requireNonNull(response.getBody()).toString());
        ResponseEntity<Guest> updateResponse = cut.updateGuest("Leo",true, 5);
        logger.info(Objects.requireNonNull(updateResponse.getBody()).toString());
    }
}