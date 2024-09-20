package com.example.demo.model.enums;

public enum Color {
    BLACK ("черный"),
    WHITE ("белый"),
    GREEN ("зеленый"),
    BLUE ("синий"),
    RED ("красный");

    private final String description;

    Color(String description) {
        this.description = description;
    }

    public String getDescription () {
        return description;
    }
}