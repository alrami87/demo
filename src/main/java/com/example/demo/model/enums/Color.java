package com.example.demo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Color {
    BLACK ("черный"),
    WHITE ("белый"),
    GREEN ("зеленый"),
    BLUE ("синий"),
    RED ("красный");

    private final String description;
}