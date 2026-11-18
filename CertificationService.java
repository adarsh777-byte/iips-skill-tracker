package com.iips.skilltracker.service;

import com.iips.skilltracker.entity.Certification;
import com.iips.skilltracker.repository.CertificationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CertificationService {

    private final CertificationRepository repository;
    private final StudentService studentService;

    public CertificationService(CertificationRepository repository, StudentService studentService) {
        this.repository = repository;
        this.studentService = studentService;
    }

    public List<Certification> getAllForStudent(Long studentId) {
        return repository.findByStudentIdOrderByIdDesc(studentId);
    }

    public Optional<Certification> getOwned(Long id, Long studentId) {
        return repository.findByIdAndStudentId(id, studentId);
    }

    /** Saves a new record, or updates an existing one only if it belongs to the student. */
    @Transactional
    public boolean save(Certification item, Long studentId) {
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
