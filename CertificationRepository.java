package com.iips.skilltracker.repository;

import com.iips.skilltracker.entity.Certification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, Long> {

    List<Certification> findByStudentIdOrderByIdDesc(Long studentId);

    Optional<Certification> findByIdAndStudentId(Long id, Long studentId);

    long countByStudentId(Long studentId);
}
