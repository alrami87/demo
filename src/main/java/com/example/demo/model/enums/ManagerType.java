package com.example.demo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ManagerType {
    DIRECTOR("Председатель"),
    FINANCIER("Бухгалтер");

    private final String description;
}