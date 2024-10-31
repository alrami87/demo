package com.example.demo.model.dto.request;

import com.example.demo.model.enums.ManagerStatus;
import com.example.demo.model.enums.ManagerType;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ManagerInfoRequest {
    ManagerStatus status;
    ManagerType type;
}
