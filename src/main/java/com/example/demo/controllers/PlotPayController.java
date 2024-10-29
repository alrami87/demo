package com.example.demo.controllers;

import com.example.demo.model.dto.request.PlotPayInfoRequest;
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

    @GetMapping("/plotNo/{plotNo}")
    @Operation(summary = "Получить список платежей по участку")
    public List<PlotPayInfoResponse> getAllPlotPaysForPlotNo(long plotNo) {
        return plotPayService.getAllPlotPaysForPlotNo(plotNo);
    }


}