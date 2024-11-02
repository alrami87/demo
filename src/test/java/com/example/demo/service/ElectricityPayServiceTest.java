package com.example.demo.service;

import com.example.demo.model.db.entity.ElectricityPay;
import com.example.demo.model.db.entity.Plot;
import com.example.demo.model.db.entity.ElectricityPay;
import com.example.demo.model.db.entity.ElectricityPay;
import com.example.demo.model.db.repository.ElectricityPayRepository;
import com.example.demo.model.db.repository.PlotRepository;
import com.example.demo.model.dto.response.ElectricityPayInfoResponse;
import com.example.demo.model.dto.response.ElectricityPayInfoResponse;
import com.example.demo.model.dto.response.ElectricityPayInfoResponse;
import com.example.demo.model.enums.PayStatus;
import com.example.demo.model.enums.PlotStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ElectricityPayServiceTest {
    @InjectMocks
    private ElectricityPayService electricityPayService;

    @Mock
    private ElectricityPayRepository electricityPayRepository;

    @Mock
    private PlotRepository plotRepository;

    @Spy
    ObjectMapper objectMapper;

    @Test
    public void createElectricityPay() {
    }

    @Test
    public void getElectricityPay() {
        Plot plot = new Plot();
        plot.setId(1L);
        plot.setStatus(PlotStatus.SOLD);

        ElectricityPay electricityPay = new ElectricityPay();
        electricityPay.setId(1L);
        electricityPay.setPlot(plot);

        when(electricityPayRepository.findById(electricityPay.getId())).thenReturn(Optional.of(electricityPay));

        ElectricityPayInfoResponse result = electricityPayService.getElectricityPay(1L);
        result.setPlotNo(electricityPay.getPlot().getPlotNo());

        assertNotNull(result);
        assertEquals(electricityPay.getId(), result.getId());
    }

    @Test
    public void updateElectricityPay() {

    }

    @Test
    public void deleteElectricityPay() {
        ElectricityPay electricityPay = new ElectricityPay();
        electricityPay.setId(1L);

        when(electricityPayRepository.findById(electricityPay.getId())).thenReturn(Optional.of(electricityPay));

        electricityPayService.deleteElectricityPay(electricityPay.getId());

        verify(electricityPayRepository, times(1)).save(any(ElectricityPay.class));

        assertEquals(PayStatus.CANCELED, electricityPay.getStatus());
    }

    @Test
    public void getAllElectricityPays() {
        ElectricityPay electricityPay1 = new ElectricityPay();
        electricityPay1.setId(1L);

        ElectricityPay electricityPay2 = new ElectricityPay();
        electricityPay2.setId(2L);

        List<ElectricityPay> electricityPays = List.of(electricityPay1, electricityPay2);
        when(electricityPayRepository.findAll()).thenReturn(electricityPays);

        List<ElectricityPayInfoResponse> result = electricityPayService.getAllElectricityPays();

        assertNotNull(result);
    }

    @Test
    public void getAllElectricityPaysForPlotNo() {
        Plot plot1 = new Plot();
        plot1.setId(1L);
        plot1.setStatus(PlotStatus.SOLD);

        when(plotRepository.findByPlotNo(plot1.getPlotNo())).thenReturn(Optional.of(plot1));

        ElectricityPay electricityPay1 = new ElectricityPay();
        electricityPay1.setId(1L);
        electricityPay1.setPlot(plot1);

        Plot plot2 = new Plot();
        plot2.setId(2L);
        plot2.setStatus(PlotStatus.SOLD);

        when(plotRepository.findByPlotNo(plot2.getPlotNo())).thenReturn(Optional.of(plot2));

        ElectricityPay electricityPay2 = new ElectricityPay();
        electricityPay2.setId(2L);
        electricityPay2.setPlot(plot2);



        List<ElectricityPay> electricityPays = List.of(electricityPay1, electricityPay2);
        when(electricityPayRepository.findByPlot(plot1)).thenReturn(electricityPays);

        List<ElectricityPayInfoResponse> result1 = electricityPayService.getAllElectricityPaysForPlotNo(plot1.getPlotNo());
        List<ElectricityPayInfoResponse> result2 = electricityPayService.getAllElectricityPaysForPlotNo(plot2.getPlotNo());

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2);
    }

    @Test
    public void getAllDebtsForPlotNo() {
        Plot plot = new Plot();
        plot.setId(1L);
        plot.setStatus(PlotStatus.SOLD);

        when(plotRepository.findByPlotNo(plot.getPlotNo())).thenReturn(Optional.of(plot));

        ElectricityPay electricityPay1 = new ElectricityPay();
        electricityPay1.setId(1L);
        electricityPay1.setPlot(plot);
        electricityPay1.setStatus(PayStatus.PAID);

        ElectricityPay electricityPay2 = new ElectricityPay();
        electricityPay2.setId(2L);
        electricityPay2.setPlot(plot);
        electricityPay2.setStatus(PayStatus.OVERDUE);



        List<ElectricityPay> electricityPays = List.of(electricityPay1, electricityPay2);
        when(electricityPayRepository.findByPlotAndStatus(plot, PayStatus.OVERDUE)).thenReturn(electricityPays);

        List<ElectricityPayInfoResponse> result1 = electricityPayService.getAllElectricityPaysForPlotNo(plot.getPlotNo());
        List<ElectricityPayInfoResponse> result2 = electricityPayService.getAllDebtsForPlotNo(plot.getPlotNo());

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2);
    }
}