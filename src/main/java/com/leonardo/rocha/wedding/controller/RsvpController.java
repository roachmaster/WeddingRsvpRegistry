package com.leonardo.rocha.wedding.controller;

import com.leonardo.rocha.wedding.data.DeleteAllResponse;
import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.service.GuestDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/")
public class RsvpController {
	private final GuestDao guestDao;

	@Autowired
	public RsvpController(GuestDao guestDao){
		this.guestDao = guestDao;
	}

	@RequestMapping(value = "guests", method = RequestMethod.GET)
	public ResponseEntity<List<Guest>> getGuests() {
		return new ResponseEntity<>(this.guestDao.getGuests(), HttpStatus.OK);
	}

	@RequestMapping(value = "guest/{name}", method = RequestMethod.GET)
	public ResponseEntity<Guest> getGuest(@PathVariable String name) {
		return new ResponseEntity<>(this.guestDao.getGuest(name), HttpStatus.OK);
	}

	@RequestMapping(value = "guest/create/name/{name}/maxGuest/{maxGuest}", method = RequestMethod.POST)
	public ResponseEntity<Guest> createGuest(@PathVariable String name, @PathVariable int maxGuest) {
		Guest createdGuest = this.guestDao.createGuest(name, maxGuest);
		return new ResponseEntity<>(createdGuest, HttpStatus.CREATED);
	}

	@RequestMapping(value = "guests/delete", method = RequestMethod.GET)
	public ResponseEntity<DeleteAllResponse> deleteGuests() {
		DeleteAllResponse response = new DeleteAllResponse();
		response.setNumOfGuests(this.guestDao.deleteGuests());
		if(response.getNumOfGuests() != 0){
			response.setDescription("Guests were not removed form DB table");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setDescription("Guests have been deleted");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "guest/update/name/{name}/going/{going}/confirmedGuest/{confirmedGuest}", method = RequestMethod.POST)
	public ResponseEntity<Guest> updateGuest(@PathVariable String name, @PathVariable boolean going ,@PathVariable int confirmedGuest) {
		return new ResponseEntity<>(this.guestDao.updateGuest(name, going, confirmedGuest), HttpStatus.OK);
	}
}
