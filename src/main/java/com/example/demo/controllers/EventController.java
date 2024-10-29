package com.example.demo.controllers;

import com.example.demo.model.dto.request.EventInfoRequest;
import com.example.demo.model.dto.response.EventInfoResponse;
import com.example.demo.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.constants.Constants.EVENTS;
import static com.example.demo.constants.Constants.USERS;

@Tag(name = "Пользователи")
@RestController
@RequestMapping(EVENTS)
@RequiredArgsConstructor

public class EventController {

    private final EventService eventService;

    @PostMapping
    @Operation(summary = "Создать событие")
    public EventInfoResponse createEvent(@RequestBody EventInfoRequest request) {
        return  eventService.createEvent(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить событие по ID")
    public EventInfoResponse getEvent(@PathVariable Long id) {
        return  eventService.getEvent(id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить событие по ID")
    public EventInfoResponse updateEvent(@PathVariable Long id, @RequestBody EventInfoRequest request) {
        return eventService.updateEvent(id, request);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить событие по ID")
    public void deleteEvent(@PathVariable Long id) {
        eventService.deleteEvent(id);
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список событий")
    public List<EventInfoResponse> getAllEvents() {
        return eventService.getAllEvents();
    }

    @GetMapping("/all")
    @Operation(summary = "Получить список будущихсобытий")
    public List<EventInfoResponse> getAllEventsInFuture() {
        return null; //eventService.getAllEventsInFuture();
    }

    
}
