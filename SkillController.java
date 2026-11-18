package com.iips.skilltracker.controller;

import com.iips.skilltracker.entity.Skill;
import com.iips.skilltracker.service.SkillService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/skills")
public class SkillController {

    private final SkillService service;

    public SkillController(SkillService service) {
        this.service = service;
    }

    @GetMapping
    public String list(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        model.addAttribute("items", service.getAllForStudent(studentId));
        return "skills";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("item", new Skill());
        addFormOptions(model);
        return "skill-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id,
                               HttpSession session,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Long studentId = (Long) session.getAttribute("studentId");
        Optional<Skill> item = service.getOwned(id, studentId);
        if (item.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Record not found.");
            return "redirect:/skills";
        }
        model.addAttribute("item", item.get());
        addFormOptions(model);
        return "skill-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("item") Skill item,
                       BindingResult result,
                       HttpSession session,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addFormOptions(model);
            return "skill-form";
        }
        Long studentId = (Long) session.getAttribute("studentId");
        if (!service.save(item, studentId)) {
            redirectAttributes.addFlashAttribute("error", "Record not found.");
            return "redirect:/skills";
        }
        redirectAttributes.addFlashAttribute("success", "Skill saved successfully.");
        return "redirect:/skills";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        Long studentId = (Long) session.getAttribute("studentId");
        service.delete(id, studentId);
        redirectAttributes.addFlashAttribute("success", "Skill deleted successfully.");
        return "redirect:/skills";
    }

    private void addFormOptions(Model model) {
        model.addAttribute("categoryOptions", List.of("Programming", "Backend", "Database", "Tools", "Cloud", "Other"));
        model.addAttribute("skillLevelOptions", List.of("Beginner", "Intermediate", "Advanced"));
    }
}
