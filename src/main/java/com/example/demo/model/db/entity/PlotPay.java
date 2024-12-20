package com.example.demo.model.db.entity;

import com.example.demo.model.enums.PayStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "plot_pays")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PlotPay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne
    @JsonBackReference(value = "plot")
    Plot plot;

    @Column(name = "pay_description")
    String payDescription;

    @Column(name = "expected_date")
    LocalDateTime expectedDate;

    @Column(name = "amount")
    BigDecimal amount;

    @Column(name = "fact_date")
    LocalDateTime factDate;

    @Column(name = "status")
    PayStatus status;

}
