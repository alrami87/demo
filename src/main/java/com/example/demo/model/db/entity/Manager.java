package com.example.demo.model.db.entity;

import com.example.demo.model.enums.ManagerStatus;
import com.example.demo.model.enums.ManagerType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "managers")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Manager {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "status")
    ManagerStatus status;

    @Column(name = "type")
    ManagerType type;

    @ManyToOne
    @JsonBackReference(value = "user_manager")
    User user;
}
