package com.example.demo.controllers;

import com.example.demo.model.dto.request.PlotPayInfoRequest;
import com.example.demo.model.dto.response.PlotPayInfoResponse;
import com.example.demo.model.dto.response.PlotPayInfoResponse;
import com.example.demo.service.PlotPayService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.constants.Constants.PLOTPAYS;

@Tag(name = "Членские взносы и прочие платежы за участки")
@RestController
@RequestMapping(PLOTPAYS)
@RequiredArgsConstructor

public class PlotPayController {

    private final PlotPayService plotPayService;

    @PostMapping
    @Operation(summary = "Создать платеж")
    public PlotPayInfoResponse createPlotPay(@RequestBody PlotPayInfoRequest request) {
        return plotPayService.createPlotPay(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить платеж по ID")
    public PlotPayInfoResponse getPlotPay(@PathVariable Long id) {
        return plotPayService.getPlotPay(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить платеж по ID")
    public PlotPayInfoResponse updatePlotPay(@PathVariable Long id, @RequestBody PlotPayInfoRequest request) {
        return plotPayService.updatePlotPay(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить платеж по ID")
    public void deletePlotPay(@PathVariable Long id) {
        plotPayService.deletePlotPay(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список всех платежей")
    public List<PlotPayInfoResponse> getAllPlotPays() {
        return plotPayService.getAllPlotPays();
    }
    
    @GetMapping("/plotNo/{plotNo}")
    @Operation(summary = "Получить список платежей по участку")
    public List<PlotPayInfoResponse> getAllPlotPaysForPlotNo(@PathVariable Long plotNo) {
        return plotPayService.getAllPlotPaysForPlotNo(plotNo);
    }

    @GetMapping("/debts/plotNo/{plotNo}")
    @Operation(summary = "Получить список задолженностей по участку")
    public List<PlotPayInfoResponse> getAllDebtsForPlotNo(@PathVariable Long plotNo) {
        return plotPayService.getAllDebtsForPlotNo(plotNo);
    }

}