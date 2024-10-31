package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Plot;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlotRepository extends JpaRepository<Plot, Long> {
    Optional<Plot> findByCadastralNo(String cadastralNo);

    Optional<Plot> findByPlotNo(Long plotNo);

    List<Plot> findByRoadNo(Long roadNo);

}
