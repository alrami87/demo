package com.example.demo.service;

import com.example.demo.exceptions.CustomExeption;
import com.example.demo.model.db.entity.ElectricityPay;
import com.example.demo.model.db.entity.Plot;
import com.example.demo.model.db.repository.ElectricityPayRepository;
import com.example.demo.model.db.repository.PlotRepository;
import com.example.demo.model.dto.request.ElectricityPayInfoRequest;
import com.example.demo.model.dto.response.ElectricityPayInfoResponse;
import com.example.demo.model.dto.response.PlotPayInfoResponse;
import com.example.demo.model.enums.PayStatus;
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
public class ElectricityPayService {
    private final ObjectMapper mapper;
    private final PlotRepository plotRepository;
    private final ElectricityPayRepository electricityPayRepository;

    public ElectricityPay getElectricityPayFromBD(Long id) {
        return electricityPayRepository.findById(id)
                .orElseThrow(() -> new CustomExeption("Electricity pay not found", HttpStatus.NOT_FOUND));
    }

    public ElectricityPayInfoResponse createElectricityPay(ElectricityPayInfoRequest request) {
        Plot plot = plotRepository.findByPlotNo(request.getPlotNo())
                .orElseThrow(() -> new CustomExeption("Plot not found", HttpStatus.NOT_FOUND));

        electricityPayRepository.findByDateAndAmountAndPlot(request.getDate(), request.getAmount(), plot)
                .ifPresent(plotPay -> {
                    throw new CustomExeption((String.format("Plot pay with this date: %s ", request.getDate())
                            + String.format(" and amount: %s already exist ", request.getAmount())
                            + String.format("for plot No: %s ", plot.getPlotNo())), HttpStatus.BAD_REQUEST);
                });

        ElectricityPay electricityPay = mapper.convertValue(request, ElectricityPay.class);
        electricityPay.setPlot(plot);
        electricityPay.setStatus(PayStatus.PAID);

        ElectricityPay save = electricityPayRepository.save(electricityPay);

        ElectricityPayInfoResponse response = mapper.convertValue(save, ElectricityPayInfoResponse.class);
        response.setPlotNo(plot.getPlotNo());

        return response;
    }

    public ElectricityPayInfoResponse getElectricityPay(Long id) {
        Optional<ElectricityPay> optionalElectricityPay = electricityPayRepository.findById(id);

        ElectricityPay electricityPay = getElectricityPayFromBD(id);
        Plot plot = electricityPay.getPlot();

        ElectricityPayInfoResponse response = mapper.convertValue(electricityPay, ElectricityPayInfoResponse.class);
        response.setPlotNo(plot.getPlotNo());

        return response;
    }

    public ElectricityPayInfoResponse updateElectricityPay(Long id, ElectricityPayInfoRequest request) {
        Plot plot = plotRepository.findById(request.getPlotNo())
                .orElseThrow(() -> new CustomExeption("Plot not found", HttpStatus.NOT_FOUND));

        ElectricityPay electricityPay = getElectricityPayFromBD(id);

        electricityPay.setPlot(plot);
        electricityPay.setAmount(request.getAmount() == null ? electricityPay.getAmount() : request.getAmount());

        if (request.getAmount() != null) {
            electricityPay.setDate(LocalDateTime.now());
            electricityPay.setStatus(PayStatus.PAID);
        }

        ElectricityPay save = electricityPayRepository.save(electricityPay);

        ElectricityPayInfoResponse response = mapper.convertValue(save, ElectricityPayInfoResponse.class);
        response.setPlotNo(plot.getPlotNo());

        return response;

    }

    public void deleteElectricityPay(Long id) {
        ElectricityPay electricityPay = getElectricityPayFromBD(id);
        electricityPay.setStatus(PayStatus.CANCELED);
        electricityPayRepository.save(electricityPay);
    }

    public List<ElectricityPayInfoResponse> getAllElectricityPays() {
        return (List<ElectricityPayInfoResponse>) electricityPayRepository.findAll().stream()
                .map(electricityPay -> mapper.convertValue(electricityPay, ElectricityPayInfoResponse.class))
                .collect(Collectors.toList());
    }

    public List<ElectricityPayInfoResponse> getAllElectricityPaysForPlotNo(Long plotNo) {
        Plot plot = plotRepository.findByPlotNo(plotNo)
                .orElseThrow(() -> new CustomExeption("Plot not found", HttpStatus.NOT_FOUND));

        return (List<ElectricityPayInfoResponse>) electricityPayRepository.findByPlot(plot).stream()
                .map(electricityPay -> mapper.convertValue(electricityPay, ElectricityPayInfoResponse.class))
                .collect(Collectors.toList());
    }

    public List<ElectricityPayInfoResponse> getAllDebtsForPlotNo(Long plotNo) {
        Plot plot = plotRepository.findByPlotNo(plotNo)
                .orElseThrow(() -> new CustomExeption("Plot not found", HttpStatus.NOT_FOUND));

        return (List<ElectricityPayInfoResponse>) electricityPayRepository.findByPlotAndStatus(plot, PayStatus.OVERDUE)
                .stream().map(electricityPay -> mapper.convertValue(electricityPay, ElectricityPayInfoResponse.class))
                .collect(Collectors.toList());
    }

}
