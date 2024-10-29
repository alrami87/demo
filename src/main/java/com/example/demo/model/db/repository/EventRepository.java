package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByNameAndDate(String name, LocalDateTime date);
    List<Event> getAllFutureEvents(LocalDate date);
}
