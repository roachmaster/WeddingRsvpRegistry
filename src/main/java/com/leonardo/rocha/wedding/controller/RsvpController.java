package com.leonardo.rocha.wedding.controller;

import com.leonardo.rocha.wedding.data.DeleteAllResponse;
import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.helper.ResponseEntityHelper;
import com.leonardo.rocha.wedding.service.GuestDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/")
public class RsvpController {

	private final GuestDB guestDao;

	@Autowired
	public RsvpController(GuestDB guestDao){
		this.guestDao = guestDao;
	}

	@RequestMapping(value = "guests", method = RequestMethod.GET)
	public ResponseEntity<List<Guest>> getGuests() {
		return ResponseEntityHelper.getGuests(this.guestDao);
	}

	@RequestMapping(value = "guest/{name}", method = RequestMethod.GET)
	public ResponseEntity<Guest> getGuest(@PathVariable String name) {
		return ResponseEntityHelper.getGuest(this.guestDao,name);
	}

	@RequestMapping(value = "guest/create/name/{name}/maxGuest/{maxGuest}", method = RequestMethod.POST)
	public ResponseEntity<Guest> createGuest(@PathVariable String name, @PathVariable int maxGuest) {
		return ResponseEntityHelper.createGuest(this.guestDao, name, maxGuest);
	}

	@RequestMapping(value = "guests/delete", method = RequestMethod.GET)
	public ResponseEntity<DeleteAllResponse> deleteGuests() {
		return ResponseEntityHelper.deleteGuests(this.guestDao);
	}

	@RequestMapping(value = "guest/update/name/{name}/going/{going}/confirmedGuest/{confirmedGuest}", method = RequestMethod.POST)
	public ResponseEntity<Guest> updateGuest(@PathVariable String name, @PathVariable boolean going ,@PathVariable int confirmedGuest) {
		return ResponseEntityHelper.updateGuest(this.guestDao,name,going, confirmedGuest);
	}
}
