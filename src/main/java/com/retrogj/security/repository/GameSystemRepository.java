package com.retrogj.security.repository;

import com.retrogj.security.model.GameSystem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameSystemRepository extends JpaRepository<GameSystem, Long> {
}
