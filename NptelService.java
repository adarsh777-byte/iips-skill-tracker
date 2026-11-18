package com.iips.skilltracker.service;

import com.iips.skilltracker.entity.NptelCourse;
import com.iips.skilltracker.repository.NptelCourseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class NptelService {

    private final NptelCourseRepository repository;
    private final StudentService studentService;

    public NptelService(NptelCourseRepository repository, StudentService studentService) {
        this.repository = repository;
        this.studentService = studentService;
    }

    public List<NptelCourse> getAllForStudent(Long studentId) {
        return repository.findByStudentIdOrderByIdDesc(studentId);
    }

    public Optional<NptelCourse> getOwned(Long id, Long studentId) {
        return repository.findByIdAndStudentId(id, studentId);
    }

    /** Saves a new record, or updates an existing one only if it belongs to the student. */
    @Transactional
    public boolean save(NptelCourse item, Long studentId) {
        if (item.getId() != null && repository.findByIdAndStudentId(item.getId(), studentId).isEmpty()) {
            return false;
        }
        item.setStudent(studentService.getById(studentId));
        repository.save(item);
        return true;
    }

    @Transactional
    public void delete(Long id, Long studentId) {
        repository.findByIdAndStudentId(id, studentId).ifPresent(repository::delete);
    }

    public long count(Long studentId) {
        return repository.countByStudentId(studentId);
    }
}
