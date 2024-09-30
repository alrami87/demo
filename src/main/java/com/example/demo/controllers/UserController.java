package com.example.demo.controllers;

import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.CarInfoResponse;
import com.example.demo.model.dto.response.UserInfoResponse;
import com.example.demo.service.CarService;
import com.example.demo.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.constants.Constants.USERS;

@Tag(name = "Пользователи")
@RestController
@RequestMapping(USERS)
@RequiredArgsConstructor

public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создать пользователя")
    public UserInfoResponse createUser(@RequestBody UserInfoRequest request) {
        return userService.createUser(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID")
    public UserInfoResponse getUser(@PathVariable Long id) {
        return userService.getUser(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить пользователя по ID")
    public UserInfoResponse updateUser(@PathVariable Long id, @RequestBody UserInfoRequest request) {
        return userService.updateUser(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя по ID")
    public void deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
    }

//    @GetMapping("/all")
//    @Operation(summary = "Получить список пользователей")
//    public List<UserInfoResponse> getAllUsers() {
//        return userService.getAllUsers();
//    }

    @GetMapping("/all")
    @Operation(summary = "Получить список пользователей")
    public Page<CarInfoResponse> getAllUsers(@RequestParam Integer page,
                                             @RequestParam Integer perPage,
                                             @RequestParam String sort,
                                             @RequestParam Sort.Direction order,
                                             @RequestParam String filter) {
        return new PageImpl<>(null);
    }

    @GetMapping("/carsOfUser/{id}")
    @Operation(summary = "Получить все автомобили пользователя")
    public List<CarInfoResponse> getAllCarsOfUser(@PathVariable Long id) {
        return userService.getAllCarsOfUser(id);
    }
}
