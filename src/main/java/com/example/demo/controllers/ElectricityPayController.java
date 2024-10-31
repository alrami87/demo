package com.example.demo.controllers;

import com.example.demo.model.dto.request.ElectricityPayInfoRequest;
import com.example.demo.model.dto.response.ElectricityPayInfoResponse;
import com.example.demo.service.ElectricityPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.constants.Constants.ELECTRICITYPAYS;

@Tag(name = "Платежи за электроэнергию")
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

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить платеж по ID")
    public void deleteElectricityPay(@PathVariable Long id) {
        electricityPayService.deleteElectricityPay(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список всех платежей")
    public List<ElectricityPayInfoResponse> getAllElectricityPays() {
        return electricityPayService.getAllElectricityPays();
    }

    @GetMapping("/plotNo/{plotNo}")
    @Operation(summary = "Получить список платежей по участку")
    public List<ElectricityPayInfoResponse> getAllElectricityPaysForPlotNo(@PathVariable Long plotNo) {
        return electricityPayService.getAllElectricityPaysForPlotNo(plotNo);
    }

    @GetMapping("/debts/plotNo/{plotNo}")
    @Operation(summary = "Получить список задолженностей по участку")
    public List<ElectricityPayInfoResponse> getAllDebtsForPlotNo(@PathVariable Long plotNo) {
        return electricityPayService.getAllDebtsForPlotNo(plotNo);
    }
}
