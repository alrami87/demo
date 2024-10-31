package com.example.demo.model.dto.request;

import com.example.demo.model.enums.PayStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlotPayInfoRequest {
    Long plotNo;
    String payDescription;
    LocalDateTime expectedDate;
    BigDecimal amount;
    LocalDateTime factDate;
    PayStatus status;
}