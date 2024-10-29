package com.example.demo.controllers;

import com.example.demo.model.dto.request.PlotInfoRequest;
import com.example.demo.model.dto.request.PlotToUserRequest;
import com.example.demo.model.dto.response.PlotInfoResponse;
import com.example.demo.service.PlotService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.example.demo.constants.Constants.PLOTS;

@Tag(name = "Земельные участки")
@RestController
@RequestMapping(PLOTS)
@RequiredArgsConstructor

public class PlotController {

    private final PlotService plotService;

    @PostMapping
    @Operation(summary = "Создать Участок")
    public PlotInfoResponse createPlot(@RequestBody PlotInfoRequest request) {
        return plotService.createPlot(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить Участок по ID")
    public PlotInfoResponse getPlot(@PathVariable Long id) {
        return plotService.getPlot(id);
    }

    @GetMapping("/plotNo/{plotNo}")
    @Operation(summary = "Получить Участок по номеру")
    public PlotInfoResponse getPlotForNo(@PathVariable Long plotNo) {
        return plotService.getPlotForNo(plotNo);
    }

    @GetMapping("/cadastralNo/{cadastralNo}")
    @Operation(summary = "Получить Участок по кадастровому номеру")
    public PlotInfoResponse getPlotForCadastralNo(@PathVariable String cadastralNo) {
        return plotService.getPlotForCadastralNo(cadastralNo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить участок по ID")
    public PlotInfoResponse updatePlot(@PathVariable Long id, @RequestBody PlotInfoRequest request) {
        return plotService.updatePlot(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить участок по ID")
    public void deletePlot(@PathVariable Long id) {
        plotService.deletePlot(id);
    }


    @GetMapping("/all")
    @Operation(summary = "Получить список участков")
    public List<PlotInfoResponse> getAllPlots() {
        return plotService.getAllPlots();
    }

    @GetMapping("/all/{roadNo}")
    @Operation(summary = "Получить список участков на линии")
    public List<PlotInfoResponse> getAllPlotsOfRoad(@PathVariable Long roadNo) {
        return plotService.getAllPlotsOfRoad(roadNo);
    }

    @PostMapping("/plotToUser")
    @Operation(summary = "Добавить участок пользователю")
    public void addPlotToUser(@RequestBody @Valid PlotToUserRequest request) {
        plotService.addPlotToUser(request);
    }
}
