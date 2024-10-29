package com.example.demo.controllers;

import com.example.demo.model.dto.request.ElectricityPayInfoRequest;
import com.example.demo.model.dto.request.PlotPayInfoRequest;
import com.example.demo.model.dto.response.ElectricityPayInfoResponse;
import com.example.demo.model.dto.response.PlotPayInfoResponse;
import com.example.demo.service.ElectricityPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.constants.Constants.ELECTRICITYPAYS;
import static com.example.demo.constants.Constants.PLOTPAYS;

@Tag(name = "Взносы за участки")
@RestController
@RequestMapping(ELECTRICITYPAYS)
@RequiredArgsConstructor

public class ElectricityPayController {

    private final ElectricityPayService electricityPayService;

    @PostMapping
    @Operation(summary = "Создать платеж")
    public ElectricityPayInfoResponse createElectricityPay(@RequestBody ElectricityPayInfoRequest request) {
        return electricityPayService.createElectricityPay(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить платеж по ID")
    public ElectricityPayInfoResponse getElectricityPay(@PathVariable Long id) {
        return electricityPayService.getElectricityPay(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить платеж по ID")
    public ElectricityPayInfoResponse updateElectricityPay(@PathVariable Long id, @RequestBody ElectricityPayInfoRequest request) {
        return electricityPayService.updateElectricityPay(id, request);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список всех платежей")
    public List<ElectricityPayInfoResponse> getAllElectricityPays() {
        return electricityPayService.getAllElectricityPays();
    }

    @GetMapping("/plotNo/{plotNo}")
    @Operation(summary = "Получить список платежей по участку")
    public List<ElectricityPayInfoResponse> getAllElectricityPaysForPlotNo(Long plotNo) {
        return electricityPayService.getAllElectricityPaysForPlotNo(plotNo);
    }

}
