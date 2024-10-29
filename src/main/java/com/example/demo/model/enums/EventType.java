package com.example.demo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EventType {
    MEETING("Собрание"),
    VOTING("Голосование"),
    DEBTS("Уплата долгов");

    private final String description;
}