package com.example.demo.service;

import com.example.demo.model.db.entity.Plot;
import com.example.demo.model.db.entity.PlotPay;
import com.example.demo.model.db.repository.PlotPayRepository;
import com.example.demo.model.db.repository.PlotRepository;
import com.example.demo.model.dto.request.PlotPayInfoRequest;
import com.example.demo.model.dto.response.PlotPayInfoResponse;
import com.example.demo.model.enums.PayStatus;
import com.example.demo.model.enums.PlotStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlotPayServiceTest {

    @InjectMocks
    private PlotPayService plotPayService;

    @Mock
    private PlotPayRepository plotPayRepository;

    @Mock
    private PlotService plotService;

    @Mock
    private PlotRepository plotRepository;

    @Spy
    ObjectMapper objectMapper;

    @Test
    public void createPlotPay() {
        PlotPayInfoRequest request = new PlotPayInfoRequest();
        Plot plot = new Plot();
        plot.setId(1L);
        plot.setStatus(PlotStatus.SOLD);

        when(plotRepository.findByPlotNo(plot.getPlotNo())).thenReturn(Optional.of(plot));

        PlotPay plotPay = new PlotPay();
        plotPay.setPlot(plot);
        plotPay.setId(1L);
        plotPay.setPayDescription("ABCD");
        plotPay.setExpectedDate(LocalDateTime.now());
        plotPay.setAmount(BigDecimal.valueOf(100.0));

        when(plotPayRepository.findByExpectedDateAndAmountAndPlot(plotPay.getExpectedDate(), plotPay.getAmount(), plotPay.getPlot()))
                .thenReturn(Optional.of(plotPay));
        when(plotPayRepository.save(any(PlotPay.class))).thenReturn(plotPay);


//        request.setPlotNo(plotPay.getPlot().getPlotNo());
//        request.setPayDescription(plotPay.getPayDescription());
//        request.setAmount(plotPay.getAmount());


        PlotPayInfoResponse result = plotPayService.createPlotPay(request);
        result.setPlotNo(plotPay.getPlot().getPlotNo());



//
//        PlotPayInfoResponse result;
//        result = plotPayService.createPlotPay(request);
//
//        assertEquals(plotPay.getId(), result.getId());
//        assertEquals(plotPay.getPlot().getPlotNo(), result.getPlotNo());
//        assertEquals(plotPay.getPayDescription(), result.getPayDescription());
//        assertEquals(plotPay.getStatus(), result.getStatus());

    }

    @Test
    public void getPlotPay() {
        Plot plot = new Plot();
        plot.setId(1L);
        plot.setStatus(PlotStatus.SOLD);

        PlotPay plotPay = new PlotPay();
        plotPay.setId(1L);
        plotPay.setPayDescription("SPb...");
        plotPay.setPlot(plot);

        when(plotPayRepository.findById(plotPay.getId())).thenReturn(Optional.of(plotPay));

        PlotPayInfoResponse result = plotPayService.getPlotPay(plotPay.getId());
        result.setPlotNo(plotPay.getPlot().getPlotNo());

        assertNotNull(result);
        assertEquals(plotPay.getId(), result.getId());
        assertEquals(plotPay.getPayDescription(), result.getPayDescription());
    }

    @Test
    public void updatePlotPay() {

    }

    @Test
    public void deletePlotPay() {
        PlotPay plotPay = new PlotPay();
        plotPay.setId(1L);

        when(plotPayRepository.findById(plotPay.getId())).thenReturn(Optional.of(plotPay));

        plotPayService.deletePlotPay(plotPay.getId());

        verify(plotPayRepository, times(1)).save(any(PlotPay.class));

        assertEquals(PayStatus.CANCELED, plotPay.getStatus());
    }

    @Test
    public void getAllPlotPays() {
        PlotPay plotPay1 = new PlotPay();
        plotPay1.setId(1L);
        plotPay1.setPayDescription("ABC");

        PlotPay plotPay2 = new PlotPay();
        plotPay2.setId(2L);
        plotPay2.setPayDescription("DEF");

        List<PlotPay> plotPays = List.of(plotPay1, plotPay2);
        when(plotPayRepository.findAll()).thenReturn(plotPays);

        List<PlotPayInfoResponse> result = plotPayService.getAllPlotPays();

        assertNotNull(result);
    }


    @Test
    public void getAllPlotPaysForPlotNo() {
        Plot plot1 = new Plot();
        plot1.setId(1L);
        plot1.setStatus(PlotStatus.SOLD);

        when(plotRepository.findByPlotNo(plot1.getPlotNo())).thenReturn(Optional.of(plot1));

        PlotPay plotPay1 = new PlotPay();
        plotPay1.setId(1L);
        plotPay1.setPlot(plot1);

        Plot plot2 = new Plot();
        plot2.setId(2L);
        plot2.setStatus(PlotStatus.SOLD);

        when(plotRepository.findByPlotNo(plot2.getPlotNo())).thenReturn(Optional.of(plot2));

        PlotPay plotPay2 = new PlotPay();
        plotPay2.setId(2L);
        plotPay2.setPlot(plot2);



        List<PlotPay> plotPays = List.of(plotPay1, plotPay2);
        when(plotPayRepository.findByPlot(plot1)).thenReturn(plotPays);

        List<PlotPayInfoResponse> result1 = plotPayService.getAllPlotPaysForPlotNo(plot1.getPlotNo());
        List<PlotPayInfoResponse> result2 = plotPayService.getAllPlotPaysForPlotNo(plot2.getPlotNo());

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

        PlotPay plotPay1 = new PlotPay();
        plotPay1.setId(1L);
        plotPay1.setPlot(plot);
        plotPay1.setStatus(PayStatus.PAID);

        PlotPay plotPay2 = new PlotPay();
        plotPay2.setId(2L);
        plotPay2.setPlot(plot);
        plotPay2.setStatus(PayStatus.OVERDUE);



        List<PlotPay> plotPays = List.of(plotPay1, plotPay2);
        when(plotPayRepository.findByPlotAndStatus(plot, PayStatus.OVERDUE)).thenReturn(plotPays);

        List<PlotPayInfoResponse> result1 = plotPayService.getAllPlotPaysForPlotNo(plot.getPlotNo());
        List<PlotPayInfoResponse> result2 = plotPayService.getAllDebtsForPlotNo(plot.getPlotNo());

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2);
    }
}