package com.example.demo.service;

import com.example.demo.model.dto.request.CarInfoRequest;
import com.example.demo.model.dto.response.CarInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {
    public CarInfoResponse createCar(CarInfoRequest request) {
        return CarInfoResponse.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .distanceAge(request.getDistanceAge())
                .weight(request.getWeight())
                .age(request.getAge())
                .isNew(request.getIsNew())
                .color(request.getColor())
                .build();
    }

    public CarInfoResponse getCar(Long id) {
        return null;
    }

    public CarInfoResponse updateCar(Long id, CarInfoRequest request) {
        return CarInfoResponse.builder()
                .brand(request.getBrand())
                .model(request.getModel())
                .distanceAge(request.getDistanceAge())
                .weight(request.getWeight())
                .age(request.getAge())
                .isNew(request.getIsNew())
                .color(request.getColor())
                .build();
    }

    public void deleteCar(Long id) {
    }


    public List<CarInfoResponse> getAllCars() {
        return null;
    }
}
