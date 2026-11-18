package com.iips.skilltracker.repository;

import com.iips.skilltracker.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SkillRepository extends JpaRepository<Skill, Long> {

    List<Skill> findByStudentIdOrderByIdDesc(Long studentId);

    Optional<Skill> findByIdAndStudentId(Long id, Long studentId);

    long countByStudentId(Long studentId);
}
