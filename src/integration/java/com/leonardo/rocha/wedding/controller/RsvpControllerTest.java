package com.leonardo.rocha.wedding.controller;

import com.leonardo.rocha.wedding.data.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RsvpControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(RsvpControllerTest.class);

    @Autowired
    RsvpController cut;

    @Before
    public void setUp() throws Exception {
        cut.deleteUsers();
    }

    @After
    public void tearDown() throws Exception {
        cut.deleteUsers();
    }

    @Test
    public void integrationTest(){
        ResponseEntity<User> createResponse = cut.createUser("Leo", 30);
        logger.info(Objects.requireNonNull(createResponse.getBody()).toString());
        ResponseEntity<User> createResponse1 = cut.createUser("Emily", 31);
        logger.info(Objects.requireNonNull(createResponse1.getBody()).toString());
        ResponseEntity<User> getResponse = cut.getUser("Leo");
        logger.info(Objects.requireNonNull(getResponse.getBody()).toString());
        ResponseEntity<List<User>> response = cut.getUsers();
        logger.info(Objects.requireNonNull(response.getBody()).toString());
        ResponseEntity<User> updateResponse = cut.updateUser("Leo", 31);
        logger.info(Objects.requireNonNull(updateResponse.getBody()).toString());
    }
}