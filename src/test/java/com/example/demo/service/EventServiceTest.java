package com.example.demo.service;

import com.example.demo.exceptions.CustomExeption;
import com.example.demo.model.db.entity.Event;
import com.example.demo.model.db.repository.EventRepository;
import com.example.demo.model.dto.request.EventInfoRequest;
import com.example.demo.model.dto.response.EventInfoResponse;
import com.example.demo.model.enums.EventStatus;
import com.example.demo.model.enums.EventType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class EventServiceTest {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Spy
    ObjectMapper objectMapper;

    @Test
    public void createEvent() {
        EventInfoRequest request = new EventInfoRequest();
        request.setName("ABC");
        request.setType(EventType.MEETING);

        Event event = new Event();
        event.setId(1L);
        event.setName("ABC");
        event.setType(EventType.MEETING);

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        EventInfoResponse result = eventService.createEvent(request);

        assertEquals(event.getName(), result.getName());
        assertEquals(event.getType(), result.getType());
        assertEquals(EventStatus.PLANNED, result.getStatus());
    }

    @Test(expected = CustomExeption.class)
    public void createEvent_exist() {
        EventInfoRequest request = new EventInfoRequest();
        request.setName("ABC");
        request.setDate(LocalDateTime.now());

        Event event = new Event();
        event.setId(1L);

        when(eventRepository.findByNameAndDate(request.getName(), request.getDate())).thenReturn(Optional.of(event));

        eventService.createEvent(request);
    }

    @Test
    public void getEvent() {
        Event event = new Event();
        event.setId(1L);
        event.setName("ABC");

        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

        EventInfoResponse result = eventService.getEvent(event.getId());

        assertNotNull(result);
        assertEquals(event.getId(), result.getId());
    }

    @Test
    public void updateEvent() {
        EventInfoRequest request = new EventInfoRequest();
        request.setName("ABC");

        Event event = new Event();
        event.setId(1L);
        event.setName("ABC");
        event.setType(EventType.MEETING);
        event.setStatus(EventStatus.COMPLEETED);

        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

        eventService.updateEvent(event.getId(), request);

        verify(eventRepository, times(1)).save(any(Event.class));

        assertEquals(EventStatus.COMPLEETED, event.getStatus());

    }

    @Test
    public void deleteEvent() {
        Event event = new Event();
        event.setId(1L);

        when(eventRepository.findById(event.getId())).thenReturn(Optional.of(event));

        eventService.deleteEvent(event.getId());

        verify(eventRepository, times(1)).save(any(Event.class));

        assertEquals(EventStatus.CANCELED, event.getStatus());
    }

    @Test
    public void getAllEvents() {
        Event event1 = new Event();
        event1.setId(1L);
        event1.setName("ABC");

        Event event2 = new Event();
        event2.setId(2L);
        event2.setName("DEF");

        List<Event> events = List.of(event1, event2);
        when(eventRepository.findAll()).thenReturn(events);

        List<EventInfoResponse> result = eventService.getAllEvents();

        assertNotNull(result);
    }

    @Test
    public void getAllFutureEvents() {
    }
}