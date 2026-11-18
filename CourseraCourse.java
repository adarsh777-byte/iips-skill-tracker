package com.iips.skilltracker.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "coursera_courses")
public class CourseraCourse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @NotBlank(message = "Course name is required")
    @Size(max = 200, message = "Course name must be at most 200 characters")
    private String courseName;

    @NotBlank(message = "Provider is required")
    private String provider = "Coursera";

    private String certificateId;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate completionDate;

    @Size(max = 500, message = "Skills must be at most 500 characters")
    @Column(length = 500)
    private String skillsAcquired;

    public CourseraCourse() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Student getStudent() { return student; }
    public void setStudent(Student student) { this.student = student; }

    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }

    public String getProvider() { return provider; }
    public void setProvider(String provider) { this.provider = provider; }

    public String getCertificateId() { return certificateId; }
    public void setCertificateId(String certificateId) { this.certificateId = certificateId; }

    public LocalDate getCompletionDate() { return completionDate; }
    public void setCompletionDate(LocalDate completionDate) { this.completionDate = completionDate; }

    public String getSkillsAcquired() { return skillsAcquired; }
    public void setSkillsAcquired(String skillsAcquired) { this.skillsAcquired = skillsAcquired; }
}
