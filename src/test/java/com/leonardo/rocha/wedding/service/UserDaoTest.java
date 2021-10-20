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
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        User response = this.uut.createUser("TestName", 25);
        assertEquals(testUserData,response);
    }

    private void setUpCreateUserMock(User testUserData) {
        when(this.userRepository.save(any())).thenReturn(testUserData);
    }

    @Test
    void getUsers() {
        List<User> expected = getTestUserList();
        setUpGetUsersMock(expected);
        List<User> response = this.uut.getUsers();
        assertEquals(expected, response);
    }

    private void setUpGetUsersMock(List<User> testUserList ) {
        when(this.userRepository.findAll()).thenReturn(testUserList);
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
        User response = this.uut.getUser(id);
        assertEquals(expectedUser, response);
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
        User response = this.uut.getUser(expectedUser.getName());
        assertEquals(expectedUser, response);
    }

    private User setUpGetUserNameMock() {
        User testUser = getTestUser();
        when(this.userRepository.findByName(anyString())).thenReturn(testUser);
        return testUser;
    }

    @Test
    void updateUser() {
        int updatedAge = 26;
        User expectedUser = setUpdateUserMock(updatedAge);
        User updatedUser = this.uut.updateUser("newName", updatedAge);
        assertEquals(expectedUser, updatedUser);
    }

    private User setUpdateUserMock(int updatedAge) {
        User updatedUser = getTestUser();
        updatedUser.setAge(updatedAge);
        when(this.userRepository.findByName(anyString())).thenReturn(getTestUser());
        when(this.userRepository.save(any())).thenReturn(updatedUser);
        return updatedUser;
    }

    @Test
    void deleteUsers() {
        long expected = setUpDeleteUsersMock(true);
        long actual = this.uut.deleteUsers();
        assertEquals(expected, actual);
    }

    @Test
    void deleteUsers_fail() {
        long expected = setUpDeleteUsersMock(false);
        long actual = this.uut.deleteUsers();
        assertEquals(expected, actual);
    }

    private long setUpDeleteUsersMock(boolean isEmpty) {
        List<User> users = new ArrayList<>();
        if(!isEmpty){
            users.add(getTestUser());
        }
        when(this.userRepository.findAll()).thenReturn(users);
        return users.size();
    }
}