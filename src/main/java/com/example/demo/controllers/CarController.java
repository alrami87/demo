package com.example.demo.controllers;

import com.example.demo.model.dto.request.CarInfoRequest;
import com.example.demo.model.dto.response.CarInfoResponse;
import com.example.demo.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.constants.Constants.CARS;

@Tag(name = "Автомобили")
@RestController
@RequestMapping(CARS)
@RequiredArgsConstructor

public class CarController {

    private final CarService carService;

    @PostMapping
    @Operation(summary = "Создать автомобиль")
    public CarInfoResponse createCar(@RequestBody CarInfoRequest request) {
        return carService.createCar(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить автомобиль по ID")
    public CarInfoResponse getCar(@PathVariable Long id) {
        return carService.getCar(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить автомобиль по ID")
    public CarInfoResponse updateCar(@PathVariable Long id, @RequestBody CarInfoRequest request) {
        return carService.updateCar(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить автомобиль по ID")
    public void deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список автомобилей")
    public List<CarInfoResponse> getAllCars(){
        return carService.getAllCars();
    }

}
