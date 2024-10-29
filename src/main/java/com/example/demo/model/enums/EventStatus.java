package com.example.demo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventStatus {
    PLANNED("Запланировано"),
    CANCELED("Отменено"),
    COMPLEETED("Закончено");

    private final String description;
}