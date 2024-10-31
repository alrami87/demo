package com.example.demo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ManagerType {
    DIRECTOR("Председатель"),
    ASSISTANT_DIRECTOR("Помощник председателя"),
    FINANCIER("Бухгалтер"),
    ASSISTANT_FINANCIER("Помощник бухгалтера");

    private final String description;
}