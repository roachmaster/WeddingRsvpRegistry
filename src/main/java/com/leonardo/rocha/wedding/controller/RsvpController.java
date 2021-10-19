package com.leonardo.rocha.wedding.controller;

import com.leonardo.rocha.wedding.data.DeleteAllResponse;
import com.leonardo.rocha.wedding.data.User;
import com.leonardo.rocha.wedding.service.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/")
public class RsvpController {
	private final UserDao userDao;

	@Autowired
	public RsvpController(UserDao userDao){
		this.userDao = userDao;
	}

	@RequestMapping(value = "users", method = RequestMethod.GET)
	public ResponseEntity<List<User>> getUsers() {
		return new ResponseEntity<>(this.userDao.getUsers(), HttpStatus.OK);
	}

	@RequestMapping(value = "user/{name}", method = RequestMethod.GET)
	public ResponseEntity<User> getUser(@PathVariable String name) {
		return new ResponseEntity<>(this.userDao.getUser(name), HttpStatus.OK);
	}

	@RequestMapping(value = "user/create/name/{name}/age/{age}", method = RequestMethod.POST)
	public ResponseEntity<User> createUser(@PathVariable String name, @PathVariable int age) {
		User createdUser = this.userDao.createUser(name, age);
		return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
	}

	@RequestMapping(value = "users/delete", method = RequestMethod.GET)
	public ResponseEntity<DeleteAllResponse> deleteUsers() {
		DeleteAllResponse response = new DeleteAllResponse();
		response.setNumOfUsers(this.userDao.deleteUsers());
		if(response.getNumOfUsers() != 0){
			response.setDescription("Users were not removed form DB table");
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		response.setDescription("Users have been deleted");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@RequestMapping(value = "user/update/name/{name}/age/{age}", method = RequestMethod.POST)
	public ResponseEntity<User> updateUser(@PathVariable String name, @PathVariable int age) {
		return new ResponseEntity<>(this.userDao.updateUser(name, age), HttpStatus.OK);
	}
}
