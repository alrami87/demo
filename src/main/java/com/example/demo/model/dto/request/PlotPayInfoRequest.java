package com.example.demo.model.dto.request;

import com.example.demo.model.db.entity.Plot;
import com.example.demo.model.enums.PayStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PlotPayInfoRequest {
    Long id;
    Long plotId;
    String payDescription;
    LocalDateTime expectedDate;
    Long amount;
    LocalDateTime factdDate;
    PayStatus status;
}