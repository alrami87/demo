package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Plot;
import com.example.demo.model.db.entity.PlotPay;
import com.example.demo.model.enums.PayStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PlotPayRepository extends JpaRepository<PlotPay, Long> {
    Optional<PlotPay> findByExpectedDateAndAmountAndPlot(LocalDateTime expectedDate, BigDecimal amount, Plot plot);
    List<PlotPay> findByPlot(Plot plot);
    List<PlotPay> findByPlotAndStatus(Plot plot, PayStatus status);
}
