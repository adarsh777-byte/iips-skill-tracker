package com.iips.skilltracker.config;

import com.iips.skilltracker.entity.Certification;
import com.iips.skilltracker.entity.CourseraCourse;
import com.iips.skilltracker.entity.NptelCourse;
import com.iips.skilltracker.entity.Skill;
import com.iips.skilltracker.entity.Student;
import com.iips.skilltracker.entity.SwayamCourse;
import com.iips.skilltracker.repository.CertificationRepository;
import com.iips.skilltracker.repository.CourseraCourseRepository;
import com.iips.skilltracker.repository.NptelCourseRepository;
import com.iips.skilltracker.repository.SkillRepository;
import com.iips.skilltracker.repository.StudentRepository;
import com.iips.skilltracker.repository.SwayamCourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/** Inserts demo data on first start-up. Nothing is inserted twice when the app restarts. */
@Component
public class DataInitializer implements CommandLineRunner {

    private static final String DEMO_EMAIL = "student@iips.edu";

    private final StudentRepository studentRepository;
    private final NptelCourseRepository nptelRepository;
    private final SwayamCourseRepository swayamRepository;
    private final CourseraCourseRepository courseraRepository;
    private final CertificationRepository certificationRepository;
    private final SkillRepository skillRepository;

    public DataInitializer(StudentRepository studentRepository,
                           NptelCourseRepository nptelRepository,
                           SwayamCourseRepository swayamRepository,
                           CourseraCourseRepository courseraRepository,
                           CertificationRepository certificationRepository,
                           SkillRepository skillRepository) {
        this.studentRepository = studentRepository;
        this.nptelRepository = nptelRepository;
        this.swayamRepository = swayamRepository;
        this.courseraRepository = courseraRepository;
        this.certificationRepository = certificationRepository;
        this.skillRepository = skillRepository;
    }

    @Override
    public void run(String... args) {
        Student student = studentRepository.findByEmail(DEMO_EMAIL).orElseGet(this::createDemoStudent);
        Long studentId = student.getId();

        if (nptelRepository.countByStudentId(studentId) == 0) {
            nptelRepository.save(nptel(student, "Programming in Java", "NPTEL-JAVA-01", "12 Weeks",
                    LocalDate.of(2025, 4, 20), 82, "Elite"));
            nptelRepository.save(nptel(student, "Data Structures and Algorithms", "NPTEL-DSA-01", "8 Weeks",
                    LocalDate.of(2025, 10, 12), 76, "Elite"));
        }

        if (swayamRepository.countByStudentId(studentId) == 0) {
            SwayamCourse course = new SwayamCourse();
            course.setStudent(student);
            course.setCourseName("Data Structures");
            course.setCourseId("SWAYAM-DS-01");
            course.setDuration("8 Weeks");
            course.setCompletionDate(LocalDate.of(2025, 5, 30));
            course.setScore(78);
            course.setCertificateAvailable(true);
            swayamRepository.save(course);
        }

        if (courseraRepository.countByStudentId(studentId) == 0) {
            CourseraCourse course = new CourseraCourse();
            course.setStudent(student);
            course.setCourseName("Java Programming");
            course.setProvider("Coursera");
            course.setCertificateId("CRS-JAVA-001");
            course.setCompletionDate(LocalDate.of(2025, 3, 15));
            course.setSkillsAcquired("Java, OOP, Programming");
            courseraRepository.save(course);
        }

        if (certificationRepository.countByStudentId(studentId) == 0) {
            certificationRepository.save(certification(student, "Java Programming Certification", "Oracle",
                    "ORC-JAVA-001", LocalDate.of(2025, 2, 10), null, "Active"));
            certificationRepository.save(certification(student, "AWS Cloud Foundations", "AWS",
                    "AWS-001", LocalDate.of(2025, 8, 20), LocalDate.of(2028, 8, 20), "Active"));
        }

        if (skillRepository.countByStudentId(studentId) == 0) {
            skillRepository.save(skill(student, "Java", "Programming", "Advanced"));
            skillRepository.save(skill(student, "Spring Boot", "Backend", "Intermediate"));
            skillRepository.save(skill(student, "MySQL", "Database", "Intermediate"));
            skillRepository.save(skill(student, "DSA", "Programming", "Advanced"));
            skillRepository.save(skill(student, "Git", "Tools", "Intermediate"));
        }
    }

    private Student createDemoStudent() {
        Student student = new Student();
        student.setName("Demo Student");
        student.setRollNumber("MCA001");
        student.setEmail(DEMO_EMAIL);
        student.setPassword("1234");
        student.setCourse("MCA");
        student.setSemester(9);
        return studentRepository.save(student);
    }

    private NptelCourse nptel(Student student, String name, String courseId, String duration,
                              LocalDate completionDate, int score, String grade) {
        NptelCourse course = new NptelCourse();
        course.setStudent(student);
        course.setCourseName(name);
        course.setCourseId(courseId);
        course.setDuration(duration);
        course.setCompletionDate(completionDate);
        course.setScore(score);
        course.setGrade(grade);
        course.setCertificateAvailable(true);
        return course;
    }

    private Certification certification(Student student, String name, String organization, String certificateId,
                                        LocalDate issueDate, LocalDate expiryDate, String status) {
        Certification certification = new Certification();
        certification.setStudent(student);
        certification.setCertificationName(name);
        certification.setIssuingOrganization(organization);
        certification.setCertificateId(certificateId);
        certification.setIssueDate(issueDate);
        certification.setExpiryDate(expiryDate);
        certification.setStatus(status);
        return certification;
    }

    private Skill skill(Student student, String name, String category, String level) {
        Skill skill = new Skill();
        skill.setStudent(student);
        skill.setSkillName(name);
        skill.setCategory(category);
        skill.setSkillLevel(level);
        return skill;
    }
}
