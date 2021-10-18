package com.leonardo.rocha.wedding.controller;

import com.leonardo.rocha.wedding.data.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class RsvpControllerTest {
    @InjectMocks
    RsvpController uut;

    @Mock
    private UserRepository userRepository;

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getTest() {
        //ResponseEntity<String> response = uut.getTest();
        //assertEquals(response.getBody(),"Hello, World!");
    }
}