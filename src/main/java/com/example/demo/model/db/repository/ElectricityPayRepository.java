package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.ElectricityPay;
import com.example.demo.model.db.entity.Plot;
import com.example.demo.model.db.entity.PlotPay;
import com.example.demo.model.db.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface ElectricityPayRepository extends JpaRepository<ElectricityPay, Long> {
    Optional<ElectricityPay> findByPlot(Plot plot);
}
