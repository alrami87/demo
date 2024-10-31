package com.example.demo.controllers;

import com.example.demo.model.dto.request.ManagerInfoRequest;
import com.example.demo.model.dto.request.ManagerToUserRequest;
import com.example.demo.model.dto.request.PlotToUserRequest;
import com.example.demo.model.dto.response.ManagerInfoResponse;
import com.example.demo.service.ManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.demo.constants.Constants.MANAGERS;

@Tag(name = "Должности")
@RestController
@RequestMapping(MANAGERS)
@RequiredArgsConstructor

public class ManagerController {

    private final ManagerService managerService;

    @PostMapping
    @Operation(summary = "Создать должность")
    public ManagerInfoResponse createManager(@RequestBody ManagerInfoRequest request) {
        return managerService.createManager(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить должность по ID")
    public ManagerInfoResponse getManager(@PathVariable Long id) {
        return managerService.getManager(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить должность по ID")
    public ManagerInfoResponse updateManager(@PathVariable Long id, @RequestBody ManagerInfoRequest request) {
        return managerService.updateManager(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить должность по ID")
    public void deleteManager(@PathVariable Long id) {
        managerService.deleteManager(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список всех должностей")
    public List<ManagerInfoResponse> getAllManagers() {
        return managerService.getAllManagers();
    }

    @GetMapping("/allActual")
    @Operation(summary = "Получить список всех актуальных должностей")
    public List<ManagerInfoResponse> getAllActualManagers() {
        return managerService.getAllActualManagers();
    }

    @PostMapping("/managerToUser")
    @Operation(summary = "Назначить пользователя на должность")
    public void addManagerToUser(@RequestBody @Valid ManagerToUserRequest request) {
        managerService.addManagerToUser(request);
    }
}
