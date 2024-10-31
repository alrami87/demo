package com.example.demo.service;

import com.example.demo.exceptions.CustomExeption;
import com.example.demo.model.db.entity.Plot;
import com.example.demo.model.db.entity.PlotPay;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.PlotPayRepository;
import com.example.demo.model.db.repository.PlotRepository;
import com.example.demo.model.dto.request.PlotPayInfoRequest;
import com.example.demo.model.dto.response.PlotPayInfoResponse;
import com.example.demo.model.enums.PayStatus;
import com.example.demo.model.enums.PlotStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlotPayService {
    private final ObjectMapper mapper;
    private final PlotRepository plotRepository;
    private final PlotPayRepository plotPayRepository;

    public PlotPay getPlotPayFromBD(Long id) {
        return plotPayRepository.findById(id)
                .orElseThrow(() -> new CustomExeption("Plot pay not found", HttpStatus.NOT_FOUND));
    }


    public PlotPayInfoResponse createPlotPay(PlotPayInfoRequest request) {
        Plot plot = plotRepository.findByPlotNo(request.getPlotNo())
                .orElseThrow(() -> new CustomExeption(String.format("Plot with No: %s not found",
                        request.getPlotNo()), HttpStatus.NOT_FOUND));

        if (plot.getStatus() == PlotStatus.EMPTY || plot.getStatus() == PlotStatus.CANCELED) {
            throw new CustomExeption(String.format("Plot with plot No: %s not sold",
                    request.getPlotNo()), HttpStatus.BAD_REQUEST);
        }

        plotPayRepository.findByExpectedDateAndAmountAndPlot(request.getExpectedDate(), request.getAmount(), plot)
                .ifPresent(plotPay -> {
                    throw new CustomExeption((String.format("Plot pay with this date: %s ", request.getExpectedDate())
                            + String.format(" and amount: %s already exist ", request.getAmount())
                            + String.format("for plot No: %s ", plot.getPlotNo())), HttpStatus.BAD_REQUEST);
                });

        PlotPay plotPay = mapper.convertValue(request, PlotPay.class);
        plotPay.setStatus(PayStatus.CREATED);
        plotPay.setPlot(plot);

        PlotPay save = plotPayRepository.save(plotPay);

        PlotPayInfoResponse response = mapper.convertValue(save, PlotPayInfoResponse.class);
        response.setPlotNo(plot.getPlotNo());

        return response;
    }

    public PlotPayInfoResponse getPlotPay(Long id) {
        Optional<PlotPay> optionalPlotPay = plotPayRepository.findById(id);

        PlotPay plotPay = getPlotPayFromBD(id);
        Plot plot = plotPay.getPlot();

        PlotPayInfoResponse response = mapper.convertValue(plotPay, PlotPayInfoResponse.class);
        response.setPlotNo(plot.getPlotNo());

        return response;
    }

    public PlotPayInfoResponse updatePlotPay(Long id, PlotPayInfoRequest request) {
        Plot plot = plotRepository.findByPlotNo(request.getPlotNo())
                .orElseThrow(() -> new CustomExeption(String.format("Plot with No: %s not found",
                        request.getPlotNo()), HttpStatus.NOT_FOUND));

        PlotPay plotPay = getPlotPayFromBD(id);

        plotPay.setPlot(plot);
        plotPay.setExpectedDate(request.getExpectedDate() == null ? plotPay.getExpectedDate() : request.getExpectedDate());
        plotPay.setPayDescription(request.getPayDescription() == null ? plotPay.getPayDescription() : request.getPayDescription());
        plotPay.setAmount(request.getAmount() == null ? plotPay.getAmount() : request.getAmount());
        plotPay.setStatus(request.getStatus());
        plotPay.setFactDate(request.getFactDate() == null ? plotPay.getFactDate() : request.getFactDate());

        if (request.getAmount() != null && request.getAmount().compareTo(BigDecimal.ZERO) >0) {
            plotPay.setFactDate(LocalDateTime.now());
            plotPay.setStatus(PayStatus.PAID);
        }


        PlotPay save = plotPayRepository.save(plotPay);

        PlotPayInfoResponse response = mapper.convertValue(save, PlotPayInfoResponse.class);
        response.setPlotNo(plot.getPlotNo());

        return response;
    }

    public void deletePlotPay(Long id) {
        PlotPay plotPay = getPlotPayFromBD(id);
        plotPay.setStatus(PayStatus.CANCELED);
        plotPayRepository.save(plotPay);
    }

    public List<PlotPayInfoResponse> getAllPlotPays() {
        return (List<PlotPayInfoResponse>) plotPayRepository.findAll().stream()
                .map(plotPay -> mapper.convertValue(plotPay, PlotPayInfoResponse.class))
                .collect(Collectors.toList());
    }

    public List<PlotPayInfoResponse> getAllPlotPaysForPlotNo(Long plotNo) {
        Plot plot = plotRepository.findByPlotNo(plotNo)
                .orElseThrow(() -> new CustomExeption("Plot not found", HttpStatus.NOT_FOUND));

        return (List<PlotPayInfoResponse>) plotPayRepository.findByPlot(plot).stream()
                .map(plotPay -> mapper.convertValue(plotPay, PlotPayInfoResponse.class))
                .collect(Collectors.toList());
    }

    public List<PlotPayInfoResponse> getAllDebtsForPlotNo(Long plotNo) {
        Plot plot = plotRepository.findByPlotNo(plotNo)
                .orElseThrow(() -> new CustomExeption("Plot not found", HttpStatus.NOT_FOUND));

        return (List<PlotPayInfoResponse>) plotPayRepository.findByPlotAndStatus(plot, PayStatus.OVERDUE).stream()
                .map(plotPay -> mapper.convertValue(plotPay, PlotPayInfoResponse.class))
                .collect(Collectors.toList());
    }

}
