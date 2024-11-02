package com.example.demo.service;

import com.example.demo.exceptions.CustomExeption;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.UserRepository;
import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.UserInfoResponse;
import com.example.demo.model.enums.Gender;
import com.example.demo.model.enums.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
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

    @Test
    public void createUser() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");
        request.setPhone("1234567890");
        request.setSecondPhone("");

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Petrov");
        user.setMiddleName("Sergeevitch");
        user.setPassword("123456");
        user.setGender(Gender.MALE);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserInfoResponse result = userService.createUser(request);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getMiddleName(), result.getMiddleName());
        assertEquals(user.getPassword(), result.getPassword());
        assertEquals(user.getGender(), result.getGender());
    }

    @Test(expected = CustomExeption.class)
    public void createUser_badEmail() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@.test.com");

        UserInfoResponse result = userService.createUser(request);

    }

    @Test(expected = CustomExeption.class)
    public void createUser_badPhone() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");
        request.setPhone("123456");

        UserInfoResponse result = userService.createUser(request);

    }

    @Test(expected = CustomExeption.class)
    public void createUser_emptyPhone() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");
        request.setPhone("");

        UserInfoResponse result = userService.createUser(request);

    }

    @Test(expected = CustomExeption.class)
    public void createUser_badSecondPhone() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");
        request.setPhone("1234567890");
        request.setSecondPhone("123");

        UserInfoResponse result = userService.createUser(request);

    }

    @Test(expected = CustomExeption.class)
    public void createUser_userExist() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");
        request.setPhone("1234567890");
        request.setSecondPhone("");

        User user = new User();
        user.setId(1L);

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));

        userService.createUser(request);
    }

    @Test(expected = CustomExeption.class)
    public void createUser_userExistByPhone() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");
        request.setPhone("1234567890");
        request.setSecondPhone("");

        User user = new User();
        user.setId(1L);

        when(userRepository.findByPhone(anyString())).thenReturn(Optional.of(user));

        userService.createUser(request);
    }

    @Test
    public void getUser() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        UserInfoResponse result = userService.getUser(user.getId());

        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
    }

    @Test
    public void updateUser() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");
        request.setPhone("1234567890");
        request.setSecondPhone("");

        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");
        user.setLastName("Petrov");
        user.setMiddleName("Sergeevitch");
        user.setPassword("123456");
        user.setGender(Gender.MALE);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.updateUser(user.getId(), request);

        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(UserStatus.UPDATED, user.getStatus());
    }

    @Test
    public void deleteUser() {
        User user = new User();
        user.setId(1L);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));

        userService.deleteUser(user.getId());

        verify(userRepository, times(1)).save(any(User.class));

        assertEquals(UserStatus.DELETED, user.getStatus());
    }

    @Test
    public void getAllUsers() {
        User user1 = new User();
        user1.setId(1L);
        user1.setFirstName("Ivan");

        User user2 = new User();
        user2.setId(2L);
        user2.setFirstName("Petr");

        List<User> users = List.of(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        List<UserInfoResponse> result = userService.getAllUsers();

        assertNotNull(result);
    }

    @Test
    public void updateUserData() {
        User user = new User();
        user.setId(1L);
        user.setFirstName("Ivan");

        when(userRepository.save(any(User.class))).thenReturn(user);

        User result = userService.updateUserData(user);

        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
    }

}