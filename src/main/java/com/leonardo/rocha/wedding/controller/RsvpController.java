package com.leonardo.rocha.wedding.controller;

import com.leonardo.rocha.wedding.data.User;
import com.leonardo.rocha.wedding.data.UserRepository;
import com.leonardo.rocha.wedding.service.UserDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.StreamSupport;

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
	public ResponseEntity<String> getUsers() {
		return this.userDao.getUsers();
	}

	@RequestMapping(value = "user/create/name/{name}/age/{age}", method = RequestMethod.POST)
	public ResponseEntity<String> createUser(@PathVariable String name, @PathVariable int age) {
		return this.userDao.createUser(name, age);
	}

	@RequestMapping(value = "users/delete", method = RequestMethod.GET)
	public ResponseEntity<String> deleteUsers() {
		return this.userDao.deleteUsers();
	}

	@RequestMapping(value = "user/update/name/{name}/age/{age}", method = RequestMethod.POST)
	public ResponseEntity<String> updateUser(@PathVariable String name, @PathVariable int age) {
		return this.userDao.updateUser(name, age);
	}
}
