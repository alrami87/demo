package com.example.demo.model.dto.response;

import com.example.demo.model.db.entity.User;
import com.example.demo.model.dto.request.ManagerInfoRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerInfoResponse extends ManagerInfoRequest {
    Long id;
}
