package com.example.demo.service;

import com.example.demo.exceptions.CustomExeption;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.UserRepository;
import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.UserInfoResponse;
import com.example.demo.model.enums.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Spy
    ObjectMapper objectMapper;

//    @Test
//    public void createUser() {
//        UserInfoRequest request = new UserInfoRequest();
//        request.setEmail("test@test.com");
//
//        User user = new User();
//        user.setId(1L);
//        user.setAge(20);
//        user.setFirstName("Test");
//        user.setLastName("Tost");
//
//        when(userRepository.save(any(User.class))).thenReturn(user);
//
//        UserInfoResponse result = userService.createUser(request);
//
//        assertEquals(user.getId(), result.getId());
//        assertEquals(user.getAge(), result.getAge());
//        assertEquals(user.getFirstName(), result.getFirstName());
//        assertEquals(user.getLastName(), result.getLastName());
//    }
//
//    @Test(expected = CustomExeption.class)
//    public void createUser_badEmail() {
//        UserInfoRequest request = new UserInfoRequest();
//        request.setEmail("test@.test.com");
//
//        UserInfoResponse result = userService.createUser(request);
//
//    }
//
//    @Test
//    public void createUser_userExist() {
//        User user = new User();
//        user.setId(1L);
//
//        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
//
//        userService.deleteUser(user.getId());
//
//        verify(userRepository, times(1)).save(any(User.class));
//
//        assertEquals(UserStatus.DELETED, user.getStatus());
//    }
//
//    @Test
//    public void getUserFromBD() {
//    }
//
//    @Test
//    public void getUser() {
//    }
//
//    @Test
//    public void updateUser() {
//    }
//
//    @Test
//    public void deleteUser() {
//        User user = new User();
//        user.setId(1L);
//
//        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));
//    }
//
//    @Test
//    public void getAllUsers() {
//    }
//
//    @Test
//    public void updateUserData() {
//    }
//
//    @Test
//    public void getAllCarsOfUser() {
//    }
}