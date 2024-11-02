package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.ElectricityPay;
import com.example.demo.model.db.entity.Plot;
import com.example.demo.model.enums.PayStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ElectricityPayRepository extends JpaRepository<ElectricityPay, Long> {
    Optional<ElectricityPay> findByDateAndAmountAndPlot(LocalDateTime Date, BigDecimal amount, Plot plot);
    List<ElectricityPay> findByPlot(Plot plot);
    List<ElectricityPay> findByPlotAndStatus(Plot plot, PayStatus status);
}
