package com.example.demo.model.db.entity;

import com.example.demo.model.enums.EventStatus;
import com.example.demo.model.enums.EventType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "events")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "name", columnDefinition = "VARCHAR(50)")
    String name;

    @Column(name = "date")
    LocalDateTime date;

    @Column(name = "type", columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    EventType type;

    @Column(name = "status", columnDefinition = "VARCHAR(10)")
    @Enumerated(EnumType.STRING)
    EventStatus status;
}
