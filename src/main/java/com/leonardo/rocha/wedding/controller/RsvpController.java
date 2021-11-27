package com.leonardo.rocha.wedding.controller;

import com.leonardo.rocha.wedding.data.DeleteAllResponse;
import com.leonardo.rocha.wedding.data.DeleteGuestResponse;
import com.leonardo.rocha.wedding.data.Guest;
import com.leonardo.rocha.wedding.helper.ResponseEntityHelper;
import com.leonardo.rocha.wedding.service.GuestDB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/")
public class RsvpController {
	private static final Logger logger = LoggerFactory.getLogger(RsvpController.class);

	private final GuestDB guestDao;

	@Autowired
	public RsvpController(GuestDB guestDao){
		this.guestDao = guestDao;
	}

	@RequestMapping(value = "guests", method = RequestMethod.GET)
	public ResponseEntity<List<Guest>> getGuests() {
		logger.info("Getting Guests");
		return ResponseEntityHelper.getGuests(this.guestDao);
	}

	@RequestMapping(value = "guest/{name}", method = RequestMethod.GET)
	public ResponseEntity<Guest> getGuest(@PathVariable String name) {
		logger.info("Getting Guest {}", name);
		return ResponseEntityHelper.getGuest(this.guestDao,name);
	}

	@RequestMapping(value = "guest/create/name/{name}/maxGuest/{maxGuest}", method = RequestMethod.POST)
	public ResponseEntity<Guest> createGuest(@PathVariable String name, @PathVariable int maxGuest) {
		logger.info("Creating Guest name: {}, maxGuest: {}", name, maxGuest);
		return ResponseEntityHelper.createGuest(this.guestDao, name, maxGuest);
	}

	@RequestMapping(value = "guests/delete", method = RequestMethod.DELETE)
	public ResponseEntity<DeleteAllResponse> deleteGuests() {
		logger.info("Deleting Guests");
		return ResponseEntityHelper.deleteGuests(this.guestDao);
	}

	@RequestMapping(value = "guest/delete/{name}", method = RequestMethod.DELETE)
	public ResponseEntity<DeleteGuestResponse> deleteGuest(@PathVariable String name){
		logger.info("Deleting Guests with name: {}", name);
		return ResponseEntityHelper.deleteGuest(this.guestDao, name);
	}

	@RequestMapping(value = "guest/update/name/{name}/going/{going}/confirmedGuest/{confirmedGuest}", method = RequestMethod.POST)
	public ResponseEntity<Guest> updateGuest(@PathVariable String name, @PathVariable boolean going ,@PathVariable int confirmedGuest) {
		logger.info("Updating Guest name: {}, going: {}, confirmedGuest: {}", name, going, confirmedGuest);
		return ResponseEntityHelper.updateGuest(this.guestDao,name,going, confirmedGuest);
	}
}
