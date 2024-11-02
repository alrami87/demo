package com.example.demo.model.db.entity;

import com.example.demo.model.enums.PlotStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "plots")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Plot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "plot_no")
    Long plotNo;

    @Column(name = "road_no")
    Long roadNo;

    @Column(name = "area")
    Float area;

    @Column(name = "cadastral_no")
    String cadastralNo;

    @Column(name = "address", columnDefinition = "VARCHAR(50)")
    String address;

    @Column(name = "created_at")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "status")
    PlotStatus status;

    @ManyToOne
    @JsonBackReference(value = "user_plots")
    User user;
}
