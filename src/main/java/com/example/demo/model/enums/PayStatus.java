package com.example.demo.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PayStatus {
    CREATED("Сформирован"),
    PAID("Оплачен"),
    OVERDUE("Просрочен"),
    CANCELED("Отменен"),
    APROVED("Подтвержден");

    private final String description;
}
