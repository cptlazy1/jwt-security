package com.retrogj.security.repository;

import com.retrogj.security.model.Photo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    Photo findByFileName(String fileName);
}
