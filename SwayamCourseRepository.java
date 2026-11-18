package com.iips.skilltracker.repository;

import com.iips.skilltracker.entity.SwayamCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SwayamCourseRepository extends JpaRepository<SwayamCourse, Long> {

    List<SwayamCourse> findByStudentIdOrderByIdDesc(Long studentId);

    Optional<SwayamCourse> findByIdAndStudentId(Long id, Long studentId);

    long countByStudentId(Long studentId);
}
