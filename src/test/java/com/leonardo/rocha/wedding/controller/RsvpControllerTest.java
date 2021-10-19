package com.leonardo.rocha.wedding.controller;

import com.leonardo.rocha.wedding.data.User;
import com.leonardo.rocha.wedding.service.UserDao;
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

@RunWith(SpringRunner.class)
@SpringBootTest
public class RsvpControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(RsvpControllerTest.class);

    @Autowired
    RsvpController cut;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {
        cut.deleteUsers();
    }

    @Test
    public void integrationTest(){
        ResponseEntity<String> createResponse = cut.createUser("Leo", 30);
        logger.info(createResponse.getBody());
        ResponseEntity<String> createResponse1 = cut.createUser("Emily", 31);
        logger.info(createResponse1.getBody());
        ResponseEntity<String> getResponse = cut.getUser("Leo");
        logger.info(getResponse.getBody());
        ResponseEntity<String> response = cut.getUsers();
        logger.info(response.getBody());
        ResponseEntity<String> updateResponse = cut.updateUser("Leo", 31);
        logger.info(updateResponse.getBody());
    }
}