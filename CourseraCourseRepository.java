package com.iips.skilltracker.repository;

import com.iips.skilltracker.entity.CourseraCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseraCourseRepository extends JpaRepository<CourseraCourse, Long> {

    List<CourseraCourse> findByStudentIdOrderByIdDesc(Long studentId);

    Optional<CourseraCourse> findByIdAndStudentId(Long id, Long studentId);

    long countByStudentId(Long studentId);
}
