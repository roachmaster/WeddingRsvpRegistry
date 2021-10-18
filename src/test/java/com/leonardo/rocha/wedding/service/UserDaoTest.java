package com.leonardo.rocha.wedding.service;

import com.leonardo.rocha.wedding.data.User;
import com.leonardo.rocha.wedding.data.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserDaoTest {
    @InjectMocks
    UserDao uut;

    @Mock
    UserRepository userRepository;
    
    @BeforeEach
    void setUp() {
        assertNotNull(this.userRepository);
    }

    @AfterEach
    void tearDown() {
        this.uut = null;
        this.userRepository = null;
    }

    @Test
    void createUser() {
        User testUserData = getTestUser();
        setUpCreateUserMock(testUserData);
        ResponseEntity<String> response = this.uut.createUser("TestName", 25);
        assertEquals(testUserData.toString(),response.getBody());
    }

    private void setUpCreateUserMock(User testUserData) {
        when(this.userRepository.save(any())).thenReturn(testUserData);
    }

    @Test
    void getUsers() {
        List<User> testUserList = getTestUserList();
        ResponseEntity<String> expectedResponse = setUpGetUsersMock(testUserList);
        ResponseEntity<String> response = this.uut.getUsers();
        assertEquals(expectedResponse, response);
    }

    private ResponseEntity<String> setUpGetUsersMock(List<User> testUserList ) {
        when(this.userRepository.findAll()).thenReturn(testUserList);
        return new ResponseEntity<>(UserDao.getAsString(testUserList), HttpStatus.OK);
    }

    private User getTestUser(){
        return new User("TestName", 25);
    }

    private User getTestUser(int i){
        User user = new User("TestName" + i, 25 + i);
        user.setId(i);
        return user;
    }

    private List<User> getTestUserList(){
        List<User> users = new ArrayList<>();
        for (int i = 1; i < 10; i++){
            User user = getTestUser(i);
            users.add(user);
        }
        return users;
    }

    @Test
    void getUser_id() {
        int id = 10;
        User expectedUser = setUpGetUserIdMock(id);
        ResponseEntity<String> response = this.uut.getUser(id);
        assertEquals(expectedUser.toString(), response.getBody());
    }

    private User setUpGetUserIdMock(int id) {
        User testUser = getTestUser();
        testUser.setId(id);
        Optional<User> optionalUser = Optional.of(testUser);
        when(this.userRepository.findById(anyInt())).thenReturn(optionalUser);
        return testUser;
    }

    @Test
    void getUser_name() {
        User expectedUser = setUpGetUserNameMock();
        ResponseEntity<String> response = this.uut.getUser(expectedUser.getName());
        assertEquals(expectedUser.toString(), response.getBody());
    }

    private User setUpGetUserNameMock() {
        User testUser = getTestUser();
        when(this.userRepository.findByName(anyString())).thenReturn(testUser);
        return testUser;
    }

//    @Test
//    void updateUser() {
//    }
//
//    @Test
//    void deleteUsers() {
//    }


}