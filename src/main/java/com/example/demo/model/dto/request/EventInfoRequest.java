package com.example.demo.model.dto.request;

import com.example.demo.model.enums.EventStatus;
import com.example.demo.model.enums.EventType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EventInfoRequest {
    Long id;
    String name;
    LocalDateTime date;
    EventType type;
    EventStatus status;
}