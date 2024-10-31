package com.example.demo.service;

import com.example.demo.exceptions.CustomExeption;
import com.example.demo.model.db.entity.Plot;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.PlotRepository;
import com.example.demo.model.dto.request.PlotInfoRequest;
import com.example.demo.model.dto.request.PlotToUserRequest;
import com.example.demo.model.dto.response.PlotInfoResponse;
import com.example.demo.model.enums.PlotStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlotService {
    private final UserService userService;
    private final ObjectMapper mapper;
    private final PlotRepository plotRepository;

    public PlotInfoResponse createPlot(PlotInfoRequest request) {
        validateCadastralNo(request);

        plotRepository.findByPlotNo(request.getPlotNo())
                .ifPresent(plot -> {
                    throw new CustomExeption(String.format("Plot with plot No: %s already exist",
                            request.getPlotNo()), HttpStatus.BAD_REQUEST);
                });
        plotRepository.findByCadastralNo(request.getCadastralNo())
                .ifPresent(plot -> {
                    throw new CustomExeption(String.format("Plot with Cadastral No: %s already exist",
                            request.getCadastralNo()), HttpStatus.BAD_REQUEST);
                });

        Plot plot = mapper.convertValue(request, Plot.class);
        plot.setCreatedAt(LocalDateTime.now());
        plot.setStatus(PlotStatus.EMPTY);

        Plot save = plotRepository.save(plot);

        return mapper.convertValue(save, PlotInfoResponse.class);
    }

    private void validateCadastralNo(PlotInfoRequest request) {
        if (request.getCadastralNo().replaceAll("\\D+", "").length() < 13) {
            throw new CustomExeption("invalid cadastral No", HttpStatus.BAD_REQUEST);
        }
    }

    public Plot getPlotFromBD(Long id) {
        return plotRepository.findById(id).orElseThrow(() -> new CustomExeption("Plot not found", HttpStatus.NOT_FOUND));
    }

    public Plot getPlotForNoFromBD(Long plotNo) {
        return plotRepository.findByPlotNo(plotNo).orElseThrow(() -> new CustomExeption("Plot not found", HttpStatus.NOT_FOUND));
    }

    public Plot getPlotForCadastralNoFromBD(String cadastralNo) {
        return plotRepository.findByCadastralNo(cadastralNo).orElseThrow(() -> new CustomExeption("Plot not found", HttpStatus.NOT_FOUND));
    }

    public PlotInfoResponse getPlot(Long id) {
        Optional<Plot> optionalPlot = plotRepository.findById(id);

        Plot plot = getPlotFromBD(id);
        return mapper.convertValue(plot, PlotInfoResponse.class);
    }

    public PlotInfoResponse getPlotForNo(Long plotNo) {
        Optional<Plot> optionalPlot = plotRepository.findByPlotNo(plotNo);

        Plot plot = getPlotForNoFromBD(plotNo);
        return mapper.convertValue(plot, PlotInfoResponse.class);
    }

    public PlotInfoResponse getPlotForCadastralNo(String cadastralNo) {
        Optional<Plot> optionalPlot = plotRepository.findByCadastralNo(cadastralNo);

        Plot plot = getPlotForCadastralNoFromBD(cadastralNo);
        return mapper.convertValue(plot, PlotInfoResponse.class);
    }

    public PlotInfoResponse updatePlot(Long id, PlotInfoRequest request) {
        validateCadastralNo(request);

        Plot plot = getPlotFromBD(id);

        plot.setPlotNo(request.getPlotNo() == null ? plot.getPlotNo() : request.getPlotNo());
        plot.setRoadNo(request.getRoadNo() == null ? plot.getRoadNo() : request.getRoadNo());
        plot.setArea(request.getArea() == null ? plot.getArea() : request.getArea());
        plot.setCadastralNo(request.getCadastralNo() == null ? plot.getCadastralNo() : request.getCadastralNo());
        plot.setAddress(request.getAddress() == null ? plot.getAddress() : request.getAddress());

        plot.setUpdatedAt(LocalDateTime.now());

        Plot save = plotRepository.save(plot);

        return mapper.convertValue(save, PlotInfoResponse.class);

    }

    public void deletePlot(Long id) {
        Plot plot = getPlotFromBD(id);
        plot.setUpdatedAt(LocalDateTime.now());
        plot.setStatus(PlotStatus.CANCELED);
        plotRepository.save(plot);
    }

    public List<PlotInfoResponse> getAllPlots() {
        return (List<PlotInfoResponse>) plotRepository.findAll().stream()
                .map(plot -> mapper.convertValue(plot, PlotInfoResponse.class))
                .collect(Collectors.toList());
    }

    public List<PlotInfoResponse> getAllPlotsOfRoad(Long roadNo) {
        return (List<PlotInfoResponse>) plotRepository.findByRoadNo(roadNo).stream()
                .map(plot -> mapper.convertValue(plot, PlotInfoResponse.class))
                .collect(Collectors.toList());
    }

    public void addPlotToUser(PlotToUserRequest request) {
        Plot plot = plotRepository.findById(request.getPlotId()).orElseThrow(() -> new CustomExeption("Plot not found", HttpStatus.NOT_FOUND));

        User userFromDB = userService.getUserFromBD(request.getUserId());

        userFromDB.getPlots().add(plot);

        userService.updateUserData(userFromDB);

        plot.setStatus(PlotStatus.SOLD);
        if (plot.getUser() != null){
            plot.setStatus(PlotStatus.RESOLD);
        }
        plot.setUpdatedAt(LocalDateTime.now());

        plot.setUser(userFromDB);

        plotRepository.save(plot);
    }
}
