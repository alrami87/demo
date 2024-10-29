package com.example.demo.model.dto.response;

import com.example.demo.model.dto.request.ElectricityPayInfoRequest;
import com.example.demo.model.dto.request.UserInfoRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ElectricityPayInfoResponse extends ElectricityPayInfoRequest {
    Long id;
}
