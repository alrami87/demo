package com.example.demo.model.dto.request;

import com.example.demo.model.db.entity.User;
import com.example.demo.model.enums.Gender;
import com.example.demo.model.enums.ManagerStatus;
import com.example.demo.model.enums.ManagerType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

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
