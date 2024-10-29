package com.example.demo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlotStatus {
    EMPTY("Не разработанный"),
    SOLD("Продан"),
    RESOLD("Перепродан"),
    CANCELED("Расформирован");

    private final String description;
}
