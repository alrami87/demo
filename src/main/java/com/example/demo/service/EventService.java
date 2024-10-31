package com.example.demo.service;

import com.example.demo.exceptions.CustomExeption;
import com.example.demo.model.db.entity.Event;
import com.example.demo.model.db.repository.EventRepository;
import com.example.demo.model.dto.request.EventInfoRequest;
import com.example.demo.model.dto.response.EventInfoResponse;
import com.example.demo.model.enums.EventStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventService {
    private final ObjectMapper mapper;
    private final EventRepository eventRepository;

    public Event getEventFromBD(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new CustomExeption("Event not found", HttpStatus.NOT_FOUND));
    }

    public EventInfoResponse createEvent(EventInfoRequest request) {

        eventRepository.findByNameAndDate(request.getName(), request.getDate())
                .ifPresent(event -> {
                    throw new CustomExeption((String.format("Event with this name: %s ", request.getName())
                            + String.format(" and date : %s already exist", request.getDate())),
                            HttpStatus.BAD_REQUEST);
                });

        Event event = mapper.convertValue(request, Event.class);
        event.setStatus(EventStatus.PLANNED);

        Event save = eventRepository.save(event);

        return mapper.convertValue(event, EventInfoResponse.class);
    }


    public EventInfoResponse getEvent(Long id) {
        //Optional<Event> optionalEvent = eventRepository.findById(id);

        Event event = getEventFromBD(id);
        return mapper.convertValue(event, EventInfoResponse.class);
    }

    public EventInfoResponse updateEvent(Long id, EventInfoRequest request) {
        Event event = getEventFromBD(id);

        event.setName(request.getName() == null ? event.getName() : request.getName());
        event.setDate(request.getDate() == null ? event.getDate() : request.getDate());
        event.setType(request.getType() == null ? event.getType() : request.getType());
        event.setStatus(request.getStatus() == null ? event.getStatus() : request.getStatus());

        Event save = eventRepository.save(event);

        return mapper.convertValue(event, EventInfoResponse.class);
    }

    public void deleteEvent(Long id) {
        Event event = getEventFromBD(id);
        event.setStatus(EventStatus.CANCELED);
        eventRepository.save(event);
    }

    public List<EventInfoResponse> getAllEvents() {
        return (List<EventInfoResponse>) eventRepository.findAll().stream()
                .map(event -> mapper.convertValue(event, EventInfoResponse.class))
                .collect(Collectors.toList());
    }

    public List<EventInfoResponse> getAllFutureEvents() {
        return (List<EventInfoResponse>) eventRepository.findAllByStatusAndDateAfter(EventStatus.PLANNED, LocalDateTime.now()).stream()
                .map(event -> mapper.convertValue(event, EventInfoResponse.class))
                .collect(Collectors.toList());
    }
}
