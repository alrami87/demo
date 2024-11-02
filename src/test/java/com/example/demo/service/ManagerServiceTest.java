package com.example.demo.service;

import com.example.demo.exceptions.CustomExeption;
import com.example.demo.model.db.entity.Manager;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.ManagerRepository;
import com.example.demo.model.dto.request.ManagerInfoRequest;
import com.example.demo.model.dto.request.ManagerToUserRequest;
import com.example.demo.model.dto.response.ManagerInfoResponse;
import com.example.demo.model.enums.ManagerStatus;
import com.example.demo.model.enums.ManagerType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ManagerServiceTest {

    @InjectMocks
    private ManagerService managerService;

    @Mock
    private ManagerRepository managerRepository;

    @Mock
    private UserService userService;

    @Spy
    ObjectMapper objectMapper;

    @Test
    public void createManager() {
        ManagerInfoRequest request = new ManagerInfoRequest();
//        request.setStatus(ManagerStatus.CREATED);
//        request.setType(ManagerType.DIRECTOR);

        Manager manager = new Manager();
        manager.setId(1L);
        manager.setStatus(ManagerStatus.CREATED);
        manager.setType(ManagerType.DIRECTOR);

        when(managerRepository.save(any(Manager.class))).thenReturn(manager);

        ManagerInfoResponse result = managerService.createManager(request);

        assertEquals(manager.getId(), result.getId());
        assertEquals(manager.getStatus(), result.getStatus());
        assertEquals(manager.getType(), result.getType());
    }

    @Test(expected = CustomExeption.class)
    public void createManager_managerExist() {
        ManagerInfoRequest request = new ManagerInfoRequest();
        request.setStatus(ManagerStatus.CREATED);
        request.setType(ManagerType.DIRECTOR);

        Manager manager = new Manager();
        manager.setId(1L);
        manager.setStatus(ManagerStatus.CREATED);
        manager.setType(ManagerType.DIRECTOR);

        when(managerRepository.findByTypeAndStatus(request.getType(), request.getStatus())).thenReturn(Optional.of(manager));

        managerService.createManager(request);
    }

    @Test
    public void getManager() {
        Manager manager = new Manager();
        manager.setId(1L);
        manager.setStatus(ManagerStatus.CREATED);
        manager.setType(ManagerType.DIRECTOR);

        when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));

        ManagerInfoResponse result = managerService.getManager(manager.getId());

        assertNotNull(result);
        assertEquals(manager.getId(), result.getId());
    }

    @Test
    public void updateManager() {
        ManagerInfoRequest request = new ManagerInfoRequest();
        request.setStatus(ManagerStatus.CREATED);
        request.setType(ManagerType.DIRECTOR);

        Manager manager = new Manager();
        manager.setId(1L);
        manager.setId(1L);
        manager.setStatus(ManagerStatus.CREATED);
        manager.setType(ManagerType.DIRECTOR);

        when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));

        ManagerInfoResponse result = managerService.updateManager(manager.getId(), request);

        verify(managerRepository, times(1)).save(any(Manager.class));

        assertEquals(manager.getStatus(), request.getStatus());
        assertEquals(manager.getType(), request.getType());
    }

    @Test
    public void deleteManager() {
        Manager manager = new Manager();
        manager.setId(1L);

        when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));

        managerService.deleteManager(manager.getId());

        verify(managerRepository, times(1)).save(any(Manager.class));

        assertEquals(ManagerStatus.DELETED, manager.getStatus());
    }

    @Test
    public void getAllManagers() {
        Manager manager1 = new Manager();
        manager1.setId(1L);
        manager1.setStatus(ManagerStatus.CREATED);

        Manager manager2 = new Manager();
        manager2.setId(2L);
        manager2.setType(ManagerType.FINANCIER);
        manager2.setStatus(ManagerStatus.CREATED);

        List<Manager> managers = List.of(manager1, manager2);
        when(managerRepository.findAll()).thenReturn(managers);

        List<ManagerInfoResponse> result = managerService.getAllManagers();

        assertNotNull(result);
    }

    @Test
    public void getAllActualManagers() {
        Manager manager1 = new Manager();
        manager1.setId(1L);
        manager1.setStatus(ManagerStatus.CREATED);

        Manager manager2 = new Manager();
        manager2.setId(2L);
        manager2.setType(ManagerType.FINANCIER);
        manager2.setStatus(ManagerStatus.DELETED);

        List<Manager> managers = List.of(manager1, manager2);
        when(managerRepository.findByStatus(ManagerStatus.CREATED)).thenReturn(managers);

        List<ManagerInfoResponse> result1 = managerService.getAllManagers();
        List<ManagerInfoResponse> result2 = managerService.getAllActualManagers();

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2);
    }

    @Test
    public void addManagerToUser() {
        Manager manager = new Manager();
        manager.setId(1L);

        when(managerRepository.findById(manager.getId())).thenReturn(Optional.of(manager));

        User user = new User();
        user.setId(1L);


        when(userService.getUserFromBD(user.getId())).thenReturn(user);
        when(userService.updateUserData(any(User.class))).thenReturn(user);


        ManagerToUserRequest request = ManagerToUserRequest.builder()
                .managerId(manager.getId())
                .userId(user.getId())
                .build();


        managerService.addManagerToUser(request);

        verify(managerRepository, times(1)).save(any(Manager.class));
        assertEquals(user.getId(), manager.getUser().getId());
    }
}