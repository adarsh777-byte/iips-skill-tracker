package com.iips.skilltracker.repository;

import com.iips.skilltracker.entity.NptelCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NptelCourseRepository extends JpaRepository<NptelCourse, Long> {

    List<NptelCourse> findByStudentIdOrderByIdDesc(Long studentId);

    Optional<NptelCourse> findByIdAndStudentId(Long id, Long studentId);

    long countByStudentId(Long studentId);
}
