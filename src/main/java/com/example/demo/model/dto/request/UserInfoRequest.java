package com.example.demo.model.dto.request;

import com.example.demo.model.enums.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfoRequest {
    @NotEmpty
    String email;
    String password;
    String firstName;
    String lastName;
    String middleName;
    Integer age;
    Gender gender;
}
