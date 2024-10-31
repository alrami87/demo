package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Manager;
import com.example.demo.model.enums.ManagerStatus;
import com.example.demo.model.enums.ManagerType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByTypeAndStatus(ManagerType type, ManagerStatus status);
    List<Manager> findByStatus(ManagerStatus status);
}
