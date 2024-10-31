package com.example.demo.service;

import com.example.demo.exceptions.CustomExeption;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.UserRepository;
import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.UserInfoResponse;
import com.example.demo.model.enums.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final ObjectMapper mapper;
    private final UserRepository userRepository;

    public UserInfoResponse createUser(UserInfoRequest request) {
        validateEmai(request);
        validatePhone(request);
        validateSecondPhone(request);

        userRepository.findByEmailIgnoreCase(request.getEmail())
                .ifPresent(user -> {
                    throw new CustomExeption(String.format("User with email: %s already exist", request.getEmail()), HttpStatus.BAD_REQUEST);
                });
        userRepository.findByPhone(request.getPhone())
                .ifPresent(user -> {
                    throw new CustomExeption(String.format("User with phone: %s already exist", request.getPhone()), HttpStatus.BAD_REQUEST);
                });

        User user = mapper.convertValue(request, User.class);
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.CREATED);

        User save = userRepository.save(user);

        return mapper.convertValue(save, UserInfoResponse.class);
    }

    private void validatePhone(UserInfoRequest request) {
        if (request.getPhone().isEmpty()) {
            throw new CustomExeption("Phone can't be empty", HttpStatus.BAD_REQUEST);
        }

        if (request.getPhone().replaceAll("\\D+", "").length() != 10) {
            throw new CustomExeption("invalid phone format", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateSecondPhone(UserInfoRequest request) {
        if (!request.getSecondPhone().isEmpty()) {
            if (request.getSecondPhone().replaceAll("\\D+", "").length() != 10) {
                throw new CustomExeption("invalid second phone format", HttpStatus.BAD_REQUEST);
            }
        }
    }

    private void validateEmai(UserInfoRequest request) {
        if (!EmailValidator.getInstance().isValid(request.getEmail())) {
            throw new CustomExeption("invalid eMail format", HttpStatus.BAD_REQUEST);
        }
    }

    public User getUserFromBD(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new CustomExeption("User not found", HttpStatus.NOT_FOUND));
    }

    public UserInfoResponse getUser(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        User user = getUserFromBD(id);
        return mapper.convertValue(user, UserInfoResponse.class);
    }

    public UserInfoResponse updateUser(Long id, UserInfoRequest request) {
        if (!request.getEmail().isEmpty()) {
            validateEmai(request);
        }
        if (!request.getPhone().isEmpty()) {
            validatePhone(request);
        }
        validateSecondPhone(request);

        User user = getUserFromBD(id);

        user.setEmail(request.getEmail() == null ? user.getEmail() : request.getEmail());
        user.setPassword(request.getPassword() == null ? user.getPassword() : request.getPassword());
        user.setFirstName(request.getFirstName() == null ? user.getFirstName() : request.getFirstName());
        user.setLastName(request.getLastName() == null ? user.getLastName() : request.getLastName());
        user.setMiddleName(request.getMiddleName() == null ? user.getMiddleName() : request.getMiddleName());
        user.setBirthDate(request.getBirthDate() == null ? user.getBirthDate() : request.getBirthDate());
        user.setGender(request.getGender() == null ? user.getGender() : request.getGender());
        user.setBirthDate(request.getBirthDate() == null ? user.getBirthDate() : request.getBirthDate());
        user.setPhone(request.getPhone() == null ? user.getPhone() : request.getPhone());
        user.setSecondPhone(request.getSecondPhone() == null ? user.getSecondPhone() : request.getSecondPhone());

        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.UPDATED);

        User save = userRepository.save(user);

        return mapper.convertValue(save, UserInfoResponse.class);
    }

    public void deleteUser(Long id) {
        User user = getUserFromBD(id);
        user.setUpdatedAt(LocalDateTime.now());
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    public List<UserInfoResponse> getAllUsers() {
        return (List<UserInfoResponse>) userRepository.findAll().stream()
                .map(user -> mapper.convertValue(user, UserInfoResponse.class))
                .collect(Collectors.toList());
    }

    public User updateUserData(User user) {
        return userRepository.save(user);
    }


}
