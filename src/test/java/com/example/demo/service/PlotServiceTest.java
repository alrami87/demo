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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlotServiceTest {

    @InjectMocks
    private PlotService plotService;

    @Mock
    private PlotRepository plotRepository;

    @Mock
    private UserService userService;

    @Spy
    ObjectMapper objectMapper;

    @Test
    public void createPlot() {
        PlotInfoRequest request = new PlotInfoRequest();
        request.setCadastralNo("78:40:0019126:03");

        Plot plot = new Plot();
        plot.setId(1L);
        plot.setPlotNo(1L);
        plot.setRoadNo(2L);
        plot.setArea(10F);
        plot.setAddress("Len.obl, Ivanovka");

        when(plotRepository.save(any(Plot.class))).thenReturn(plot);

        PlotInfoResponse result = plotService.createPlot(request);

        assertEquals(plot.getId(), result.getId());
        assertEquals(plot.getPlotNo(), result.getPlotNo());
        assertEquals(plot.getRoadNo(), result.getRoadNo());
        assertEquals(plot.getArea(), result.getArea());
        assertEquals(plot.getAddress(), result.getAddress());
    }

    @Test(expected = CustomExeption.class)
    public void createPlot_badCadastralNo() {
        PlotInfoRequest request = new PlotInfoRequest();
        request.setCadastralNo("11:22:33");

        PlotInfoRequest result = plotService.createPlot(request);
    }

    @Test(expected = CustomExeption.class)
    public void createPlot_plotExist() {
        PlotInfoRequest request = new PlotInfoRequest();
        request.setCadastralNo("78:40:0019126:03");

        Plot plot = new Plot();
        plot.setId(1L);

        when(plotRepository.findByCadastralNo(anyString())).thenReturn(Optional.of(plot));

        plotService.createPlot(request);
    }

    @Test
    public void getPlot() {
        Plot plot = new Plot();
        plot.setId(1L);
        plot.setAddress("SPb...");

        when(plotRepository.findById(plot.getId())).thenReturn(Optional.of(plot));

        PlotInfoResponse result = plotService.getPlot(plot.getId());

        assertNotNull(result);
        assertEquals(plot.getId(), result.getId());
    }

    @Test
    public void getPlotForNo() {
        Plot plot = new Plot();
        plot.setId(1L);
        plot.setPlotNo(2L);

        when(plotRepository.findByPlotNo(anyLong())).thenReturn(Optional.of(plot));

        PlotInfoResponse result = plotService.getPlotForNo(plot.getId());

        assertNotNull(result);
    }

    @Test
    public void getPlotForCadastralNo() {
        Plot plot = new Plot();
        plot.setId(1L);
        plot.setCadastralNo("78:40:0019126:03");

        when(plotRepository.findByCadastralNo(anyString())).thenReturn(Optional.of(plot));

        PlotInfoResponse result = plotService.getPlotForCadastralNo(plot.getCadastralNo());

        assertNotNull(result);
    }

    @Test
    public void updatePlot() {
        PlotInfoRequest request = new PlotInfoRequest();
        request.setCadastralNo("78:40:0019126:03");
        request.setPlotNo(1L);
        request.setRoadNo(2L);
        request.setArea(10F);
        request.setAddress("Len.obl, Ivanovka");

        Plot plot = new Plot();
        plot.setId(1L);
        plot.setPlotNo(1L);
        plot.setRoadNo(2L);
        plot.setArea(10F);
        plot.setAddress("Len.obl, Ivanovka");

        when(plotRepository.findById(plot.getId())).thenReturn(Optional.of(plot));

        PlotInfoResponse result = plotService.updatePlot(plot.getId(), request);

        verify(plotRepository, times(1)).save(any(Plot.class));

        assertEquals(plot.getPlotNo(), request.getPlotNo());
        assertEquals(plot.getRoadNo(), request.getRoadNo());
        assertEquals(plot.getArea(), request.getArea());
        assertEquals(plot.getAddress(), request.getAddress());
    }

    @Test
    public void deletePlot() {
        Plot plot = new Plot();
        plot.setId(1L);

        when(plotRepository.findById(plot.getId())).thenReturn(Optional.of(plot));

        plotService.deletePlot(plot.getId());

        verify(plotRepository, times(1)).save(any(Plot.class));

        assertEquals(PlotStatus.CANCELED, plot.getStatus());
    }

    @Test
    public void getAllPlots() {
        Plot plot1 = new Plot();
        plot1.setId(1L);
        plot1.setAddress("SPb");

        Plot plot2 = new Plot();
        plot2.setId(2L);
        plot2.setAddress("LO");

        List<Plot> plots = List.of(plot1, plot2);
        when(plotRepository.findAll()).thenReturn(plots);

        List<PlotInfoResponse> result = plotService.getAllPlots();

        assertNotNull(result);
    }

    @Test
    public void getAllPlotsOfRoad() {
        Plot plot1 = new Plot();
        plot1.setId(1L);
        plot1.setRoadNo(1L);
        plot1.setAddress("SPb");

        Plot plot2 = new Plot();
        plot2.setId(2L);
        plot1.setRoadNo(2L);
        plot2.setAddress("LO");

        List<Plot> plots = List.of(plot1, plot2);
        when(plotRepository.findByRoadNo(plot1.getRoadNo())).thenReturn(plots);

        List<PlotInfoResponse> result1 = plotService.getAllPlotsOfRoad(plot1.getRoadNo());
        List<PlotInfoResponse> result2 = plotService.getAllPlotsOfRoad(plot2.getRoadNo());

        assertNotNull(result1);
        assertNotNull(result2);
        assertNotSame(result1, result2);
    }

    @Test
    public void addPlotToUser() {
        Plot plot = new Plot();
        plot.setId(1L);

        when(plotRepository.findById(plot.getId())).thenReturn(Optional.of(plot));

        User user = new User();
        user.setId(1L);
        user.setPlots(new ArrayList<>());


        when(userService.getUserFromBD(user.getId())).thenReturn(user);
        when(userService.updateUserData(any(User.class))).thenReturn(user);


        PlotToUserRequest request = PlotToUserRequest.builder()
                .plotId(plot.getId())
                .userId(user.getId())
                .build();


        plotService.addPlotToUser(request);

        verify(plotRepository, times(1)).save(any(Plot.class));
        assertEquals(user.getId(), plot.getUser().getId());
    }
}