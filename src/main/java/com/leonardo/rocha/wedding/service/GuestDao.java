package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.User;
import com.leonardo.rocha.wedding.data.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
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

    public User createUser(String name, int age){
        User newUser = new User(name,age);
        logger.info("Adding User: {}", newUser);
        return this.userRepository.save(newUser);
    }

    public List<User> getUsers() {
        logger.info("Getting Users from DB");
        Iterable<User> users = this.userRepository.findAll();
        return (List<User>) users;
    }

    public User getUser(int id) {
        logger.info("Getting Users with id {} from DB", id);
        Optional<User> optionalUser = this.userRepository.findById(id);

        User user = null;
        if (optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        return user;
    }

    public User getUser(String name) {
        logger.info("Getting Users from DB");
        return this.userRepository.findByName(name);
    }

    public User updateUser(String name, int age){
        User user = this.userRepository.findByName(name);
        logger.info("Getting User from DB: {}", user);
        user.setName(name);
        user.setAge(age);
        User updatedUser = this.userRepository.save(user);
        logger.info("Updated User from DB: {}", updatedUser);
        return updatedUser;
    }

    public long deleteUsers() {
        this.userRepository.deleteAll();
        Iterable<User> users = this.userRepository.findAll();
        return getNumberOfUsers(users);
    }

    public static long getNumberOfUsers(Iterable<User> users){
        return StreamSupport.stream(users.spliterator(), false).count();
    }

}
