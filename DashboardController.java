package com.iips.skilltracker.controller;

import com.iips.skilltracker.service.CertificationService;
import com.iips.skilltracker.service.CourseraService;
import com.iips.skilltracker.service.NptelService;
import com.iips.skilltracker.service.SkillService;
import com.iips.skilltracker.service.SwayamService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    private final NptelService nptelService;
    private final SwayamService swayamService;
    private final CourseraService courseraService;
    private final CertificationService certificationService;
    private final SkillService skillService;

    public DashboardController(NptelService nptelService,
                               SwayamService swayamService,
                               CourseraService courseraService,
                               CertificationService certificationService,
                               SkillService skillService) {
        this.nptelService = nptelService;
        this.swayamService = swayamService;
        this.courseraService = courseraService;
        this.certificationService = certificationService;
        this.skillService = skillService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        model.addAttribute("nptelCount", nptelService.count(studentId));
        model.addAttribute("swayamCount", swayamService.count(studentId));
        model.addAttribute("courseraCount", courseraService.count(studentId));
        model.addAttribute("certificationCount", certificationService.count(studentId));
        model.addAttribute("skillCount", skillService.count(studentId));
        return "dashboard";
    }
}
