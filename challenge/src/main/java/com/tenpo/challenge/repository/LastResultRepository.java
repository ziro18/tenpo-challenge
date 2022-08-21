package com.tenpo.challenge.repository;

import com.tenpo.challenge.entity.LastResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LastResultRepository extends JpaRepository<LastResult, Long> {
    Optional<LastResult> findTopByOrderByCreatedAtDesc();
}
