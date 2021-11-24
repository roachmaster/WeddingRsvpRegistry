package com.leonardo.rocha.wedding.helper;

import com.leonardo.rocha.wedding.data.DeleteAllResponse;
import com.leonardo.rocha.wedding.data.DeleteGuestResponse;
import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.service.GuestDB;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class ResponseEntityHelper {
    public static ResponseEntity<List<Guest>> getGuests(GuestDB guestDao){
        List<Guest> response = guestDao.getGuests();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    public static ResponseEntity<Guest> getGuest(GuestDB guestDao, String name){
        return new ResponseEntity<>(guestDao.getGuest(name), HttpStatus.OK);
    }

    public static ResponseEntity<Guest> createGuest(GuestDB guestDao, String name, int maxGuest){
        Guest createdGuest = guestDao.createGuest(name, maxGuest);
        return new ResponseEntity<>(createdGuest, HttpStatus.CREATED);
    }

    public static ResponseEntity<DeleteAllResponse> deleteGuests(GuestDB guestDao){
        DeleteAllResponse response = new DeleteAllResponse();
        response.setNumOfGuests(guestDao.deleteGuests());
        return getDeleteAllResponseEntity(response);
    }

    private static ResponseEntity<DeleteAllResponse> getDeleteAllResponseEntity(DeleteAllResponse response) {
        if(response.getNumOfGuests() != 0){
            response.setDescription("Guests were not removed form DB table");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }else{
            response.setDescription("Guests have been deleted");
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
    }

    public static ResponseEntity<Guest> updateGuest(GuestDB guestDao, String name, boolean going, int confirmedGuest){
        return new ResponseEntity<>(guestDao.updateGuest(name, going,confirmedGuest), HttpStatus.OK);
    }

    public static ResponseEntity<DeleteGuestResponse> deleteGuest(GuestDB guestDao, String name) {
        DeleteGuestResponse response = new DeleteGuestResponse();
        long numOfDeletedGuest = guestDao.deleteGuest(name);
        response.setNumOfGuestsDeleted(numOfDeletedGuest);
        response.setDescription("Deleting Guests with name: " + name);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
