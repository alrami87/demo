package com.example.demo.service;

import com.example.demo.exceptions.CustomExeption;
import com.example.demo.model.db.entity.Manager;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.ManagerRepository;
import com.example.demo.model.dto.request.ManagerInfoRequest;
import com.example.demo.model.dto.request.ManagerToUserRequest;
import com.example.demo.model.dto.response.ManagerInfoResponse;
import com.example.demo.model.enums.EventStatus;
import com.example.demo.model.enums.EventType;
import com.example.demo.model.enums.ManagerStatus;
import com.example.demo.model.enums.ManagerType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ManagerService {
    private final UserService userService;
    private final ObjectMapper mapper;
    private final ManagerRepository managerRepository;

    public ManagerInfoResponse createManager(ManagerInfoRequest request) {
        managerRepository.findByTypeAndStatus(request.getType(), ManagerStatus.CREATED)
                .ifPresent(plotPay -> {
                    throw new CustomExeption(String.format("Manager position %s already exist", request.getType()),
                            HttpStatus.BAD_REQUEST);
                });

        Manager manager = mapper.convertValue(request, Manager.class);
        manager.setCreatedAt(LocalDateTime.now());
        manager.setStatus(ManagerStatus.CREATED);

        Manager save = managerRepository.save(manager);

        return mapper.convertValue(save, ManagerInfoResponse.class);
    }

    public Manager getManagerFromBD(Long id) {
        return managerRepository.findById(id).orElseThrow(() -> new CustomExeption("Manager position not found", HttpStatus.NOT_FOUND));
    }

    public ManagerInfoResponse getManager(Long id) {
        Optional<Manager> optionalManager = managerRepository.findById(id);

        Manager manager = getManagerFromBD(id);
        return mapper.convertValue(manager, ManagerInfoResponse.class);
    }

    public ManagerInfoResponse updateManager(Long id, ManagerInfoRequest request) {
        Manager manager = getManagerFromBD(id);

        manager.setType(request.getType() == null ? manager.getType() : request.getType());
        manager.setStatus(request.getStatus() == null ? manager.getStatus() : request.getStatus());

        manager.setUpdatedAt(LocalDateTime.now());

        Manager save = managerRepository.save(manager);

        return mapper.convertValue(save, ManagerInfoResponse.class);

    }

    public void deleteManager(Long id) {
        Manager manager = getManagerFromBD(id);
        manager.setUpdatedAt(LocalDateTime.now());
        manager.setStatus(ManagerStatus.DELETED);
        managerRepository.save(manager);
    }

    public List<ManagerInfoResponse> getAllManagers() {
        return (List<ManagerInfoResponse>) managerRepository.findAll().stream()
                .map(manager -> mapper.convertValue(manager, ManagerInfoResponse.class))
                .collect(Collectors.toList());
    }

    public List<ManagerInfoResponse> getAllActualManagers() {
        return (List<ManagerInfoResponse>) managerRepository.findByStatus(ManagerStatus.CREATED).stream()
                .map(manager -> mapper.convertValue(manager, ManagerInfoResponse.class))
                .collect(Collectors.toList());
    }

    public void addManagerToUser(ManagerToUserRequest request) {
        Manager manager = managerRepository.findById(request.getManagerId()).orElseThrow(() -> new CustomExeption("Manager not found", HttpStatus.NOT_FOUND));

        User userFromDB = userService.getUserFromBD(request.getUserId());

        userService.updateUserData(userFromDB);

        manager.setUpdatedAt(LocalDateTime.now());

        manager.setUser(userFromDB);

        managerRepository.save(manager);
    }
}
