package com.leonardo.rocha.wedding.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.leonardo.rocha.wedding.data.DeleteAllResponse;
import com.leonardo.rocha.wedding.data.DeleteGuestResponse;
import com.leonardo.rocha.wedding.data.Guest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    ObjectWriter objectWriter;

    @Before
    public void setUp() throws Exception {
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
    public void deleteGuests(){
        getGuestsTest();
        assertGetGuestsResponse(cut.getGuests());
        ResponseEntity<DeleteAllResponse> deleteGuestsResponse = cut.deleteGuests();
        assertEquals(HttpStatus.OK, deleteGuestsResponse.getStatusCode());
        DeleteAllResponse response = deleteGuestsResponse.getBody();
        assert response != null;
        assertEquals(0, response.getNumOfGuests());
    }

    @Test
    public void deleteGuest() throws JsonProcessingException {
        getGuestsTest();
        assertGetGuestsResponse(cut.getGuests());

        ResponseEntity<DeleteGuestResponse> deleteGuestsResponse = cut.deleteGuest("Leo");
        assertEquals(HttpStatus.OK, deleteGuestsResponse.getStatusCode());
        DeleteGuestResponse response = deleteGuestsResponse.getBody();
        assert response != null;
        assertEquals(1, response.getNumOfGuestsDeleted());

        ResponseEntity<Guest> deletedGuest = cut.getGuest("Leo");
        assert deletedGuest != null;
        assert deletedGuest.getBody() == null;

        logger.info(prettyPrint(response));

    }

    @Test
    public void updateGuest(){
        getGuestsTest();
    }

    @Test
    public void integrationTest() throws JsonProcessingException {
        ResponseEntity<Guest> createResponse = cut.createGuest("Leo", 5);
        logger.info(prettyPrint(createResponse.getBody()));
        ResponseEntity<Guest> createResponse1 = cut.createGuest("Emily", 6);
        logger.info(prettyPrint(createResponse1.getBody()));
        ResponseEntity<Guest> getResponse = cut.getGuest("Leo");
        logger.info(prettyPrint(getResponse.getBody()));
        ResponseEntity<List<Guest>> response = cut.getGuests();
        logger.info(prettyPrint(response.getBody()));
        ResponseEntity<Guest> updateResponse = cut.updateGuest("Leo",true, 5);
        logger.info(prettyPrint(updateResponse.getBody()));
    }

    private String prettyPrint(Object obj) throws JsonProcessingException {
        return objectWriter.writeValueAsString(Objects.requireNonNull(obj));
    }
}