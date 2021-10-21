package com.leonardo.rocha.wedding.controller;

import com.leonardo.rocha.wedding.data.Guest;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.datasource.url}")
    String url;

    @Value("${spring.datasource.password}")
    String pw;

    @Before
    public void setUp() throws Exception {
        logger.info("look leo spring.datasource.url: {}", url);
        logger.info("look leo spring.datasource.password: {}", pw);
        cut.deleteGuests();
    }

    @After
    public void tearDown() throws Exception {
        cut.deleteGuests();
    }

    @Test
    public void integrationTest(){
        ResponseEntity<Guest> createResponse = cut.createGuest("Leo", 5);
        logger.info(Objects.requireNonNull(createResponse.getBody()).toString());
        ResponseEntity<Guest> createResponse1 = cut.createGuest("Emily", 6);
        logger.info(Objects.requireNonNull(createResponse1.getBody()).toString());
        ResponseEntity<Guest> getResponse = cut.getGuest("Leo");
        logger.info(Objects.requireNonNull(getResponse.getBody()).toString());
        ResponseEntity<List<Guest>> response = cut.getGuests();
        logger.info(Objects.requireNonNull(response.getBody()).toString());
        ResponseEntity<Guest> updateResponse = cut.updateGuest("Leo",true, 5);
        logger.info(Objects.requireNonNull(updateResponse.getBody()).toString());
    }
}