package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Plot;
import com.example.demo.model.db.entity.PlotPay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface PlotPayRepository extends JpaRepository<PlotPay, Long> {
    Optional<PlotPay> findByExpectedDateAndAmount(LocalDateTime expectedDate, Long amount);
    Optional<PlotPay> findByPlot(Plot plot);
}
