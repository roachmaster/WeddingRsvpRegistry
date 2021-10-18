package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.User;
import com.leonardo.rocha.wedding.data.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Component
public class UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDao.class);

    private final UserRepository userRepository;

    @Autowired
    public UserDao(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> createUser(String name, int age){
        User newUser = new User(name,age);
        logger.info("Adding User: {}", newUser);
        User savedUser = this.userRepository.save(newUser);
        return new ResponseEntity<>(savedUser.toString(), HttpStatus.OK);
    }

    public ResponseEntity<String> getUsers() {
        logger.info("Getting Users from DB");
        Iterable<User> users = this.userRepository.findAll();
        String usersAsString = getAsString(users);
        return new ResponseEntity<>(usersAsString, HttpStatus.OK);
    }

    public static String getAsString(Iterable<User> users){
        StringBuilder stringBuilder = new StringBuilder();
        for (User user: users){
            stringBuilder.append(user.toString()).append("\n");
        }
        return stringBuilder.toString();
    }

    public ResponseEntity<String> getUser(int id) {
        logger.info("Getting Users with id {} from DB", id);
        Optional<User> optionalUser = this.userRepository.findById(id);

        String userString;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userString= user.toString();
        } else {
            userString =  id + " does not exist.";
        }
        return new ResponseEntity<>(userString, HttpStatus.OK);
    }

    public ResponseEntity<String> getUser(String name) {
        logger.info("Getting Users from DB");
        User user = this.userRepository.findByName(name);
        String userString = Objects.nonNull(user) ? user.toString() : name + " does not exist ";
        return new ResponseEntity<>(userString, HttpStatus.OK);
    }

    public ResponseEntity<String> updateUser(String name, int age){
        User user = this.userRepository.findByName(name);
        user.setName(name);
        user.setAge(age);
        User updatedUser = this.userRepository.save(user);
        return new ResponseEntity<>(updatedUser + "\n", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteUsers() {
        this.userRepository.deleteAll();
        Iterable<User> users = this.userRepository.findAll();
        long usersSize = getNumberOfUsers(users);
        if(usersSize != 0){
            logger.error("Users should have been deleted but size is : {}", usersSize);
            return new ResponseEntity<>(String.format("Users should have been deleted but size is : %s",usersSize), HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(String.format("Users have been deleted, size is : %s",usersSize), HttpStatus.OK);
    }

    public static long getNumberOfUsers(Iterable<User> users){
        return StreamSupport.stream(users.spliterator(), false).count();
    }

}
