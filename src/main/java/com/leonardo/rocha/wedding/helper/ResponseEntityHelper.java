package com.leonardo.rocha.wedding.helper;

import com.leonardo.rocha.wedding.data.DeleteAllResponse;
import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.service.GuestDao;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public class ResponseEntityHelper {
    public static ResponseEntity<List<Guest>> getGuests(GuestDao guestDao){
        List<Guest> response = guestDao.getGuests();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public static ResponseEntity<Guest> getGuest(GuestDao guestDao, String name){
        return new ResponseEntity<>(guestDao.getGuest(name), HttpStatus.OK);
    }

    public static ResponseEntity<Guest> createGuest(GuestDao guestDao, String name, int maxGuest){
        Guest createdGuest = guestDao.createGuest(name, maxGuest);
        return new ResponseEntity<>(createdGuest, HttpStatus.CREATED);
    }

    public static ResponseEntity<DeleteAllResponse> deleteGuests(GuestDao guestDao){
        DeleteAllResponse response = new DeleteAllResponse();
        response.setNumOfGuests(guestDao.deleteGuests());
        ResponseEntity<DeleteAllResponse> responseEntity = null;
        if(response.getNumOfGuests() != 0){
            response.setDescription("Guests were not removed form DB table");
            responseEntity = new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            response.setDescription("Guests have been deleted");
            responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
        }
        return responseEntity;
    }

    public static ResponseEntity<Guest> updateGuest(GuestDao guestDao, String name, int maxGuest){
        return new ResponseEntity<>(guestDao.updateGuest(name, maxGuest), HttpStatus.OK);
    }
}
