package com.iips.skilltracker.service;

import com.iips.skilltracker.entity.Student;
import com.iips.skilltracker.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    /** Simple database login: returns the student when email and password match. */
    public Optional<Student> authenticate(String email, String password) {
        return studentRepository.findByEmail(email)
                .filter(student -> student.getPassword().equals(password));
    }

    public Student getById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Student not found with id " + id));
    }
}
