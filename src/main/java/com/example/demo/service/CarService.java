package com.example.demo.service;

import com.example.demo.model.db.entity.Car;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.CarRepository;
import com.example.demo.model.dto.request.CarInfoRequest;
import com.example.demo.model.dto.request.CarToUserRequest;
import com.example.demo.model.dto.response.CarInfoResponse;
import com.example.demo.model.dto.response.UserInfoResponse;
import com.example.demo.model.enums.CarStatus;
import com.example.demo.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {

    private final UserService userService;
    private final ObjectMapper mapper;
    private final CarRepository carRepository;

    public CarInfoResponse createCar(CarInfoRequest request) {
        Car car = mapper.convertValue(request, Car.class);
        car.setCreatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.CREATED);

        Car save = carRepository.save(car);

        return mapper.convertValue(save, CarInfoResponse.class);
    }

    private Car getCarFromBDFromBD(Long id) {
        return carRepository.findById(id).orElse(new Car());
    }

    public CarInfoResponse getCar(Long id) {
        Optional<Car> optionalCar = carRepository.findById(id);

       Car car = getCarFromBDFromBD(id);
        return mapper.convertValue(car, CarInfoResponse.class);
    }

    public CarInfoResponse updateCar(Long id, CarInfoRequest request) {
        Car car = getCarFromBDFromBD(id);

        car.setBrand(request.getBrand() == null ? car.getBrand() : request.getBrand());
        car.setModel(request.getModel() == null ? car.getModel() : request.getModel());
        car.setColor(request.getColor() == null ? car.getColor() : request.getColor());
        car.setPrice(request.getPrice() == null ? car.getPrice() : request.getPrice());
        car.setYear(request.getYear() == null ? car.getYear() : request.getYear());
        car.setIsNew(request.getIsNew() == null ? car.getIsNew() : request.getIsNew());

        car.setUpdatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.UPDATED);

        Car save = carRepository.save(car);

        return mapper.convertValue(save, CarInfoResponse.class);
    }

    public void deleteCar(Long id) {
        Car car = getCarFromBDFromBD(id);
        car.setUpdatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.DELETED);
        carRepository.save(car);
    }

    public Page<CarInfoResponse> getAllCars(Integer page, Integer perPage, String sort, Sort.Direction order, String filter) {

        Pageable pageRequest = PaginationUtil.getPageRequest(page, perPage, sort, order);


        Page<Car> all = carRepository.findAllByStatusNot(pageRequest, CarStatus.DELETED);

        List<CarInfoResponse> content = all.getContent().stream()
                .map(car -> mapper.convertValue(car, CarInfoResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content, pageRequest, all.getTotalElements());
    }

    public void addCarToUser(CarToUserRequest request) {
        Car car = carRepository.findById(request.getCarId()).orElse(null);

        if (car == null) {
            return;
        }

        User userFromDB = userService.getUserFromBD(request.getUserId());

        if (userFromDB == null) {
            return;
        }

        userFromDB.getCars().add(car);

        userService.updateUserData(userFromDB);

        car.setUser(userFromDB);
        carRepository.save(car);
    }

    public List<CarInfoResponse> getAllCarsOfUser(Long id) {
        return carRepository.findAllCarsOfUser(id).stream()
                .map(car -> mapper.convertValue(car, CarInfoResponse.class))
                .collect(Collectors.toList());
    }
}
