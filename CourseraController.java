package com.iips.skilltracker.controller;

import com.iips.skilltracker.entity.CourseraCourse;
import com.iips.skilltracker.service.CourseraService;
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

import java.util.Optional;

@Controller
@RequestMapping("/coursera")
public class CourseraController {

    private final CourseraService service;

    public CourseraController(CourseraService service) {
        this.service = service;
    }

    @GetMapping
    public String list(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        model.addAttribute("items", service.getAllForStudent(studentId));
        return "coursera";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("item", new CourseraCourse());
        addFormOptions(model);
        return "coursera-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id,
                               HttpSession session,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Long studentId = (Long) session.getAttribute("studentId");
        Optional<CourseraCourse> item = service.getOwned(id, studentId);
        if (item.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Record not found.");
            return "redirect:/coursera";
        }
        model.addAttribute("item", item.get());
        addFormOptions(model);
        return "coursera-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("item") CourseraCourse item,
                       BindingResult result,
                       HttpSession session,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addFormOptions(model);
            return "coursera-form";
        }
        Long studentId = (Long) session.getAttribute("studentId");
        if (!service.save(item, studentId)) {
            redirectAttributes.addFlashAttribute("error", "Record not found.");
            return "redirect:/coursera";
        }
        redirectAttributes.addFlashAttribute("success", "Coursera Course saved successfully.");
        return "redirect:/coursera";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        Long studentId = (Long) session.getAttribute("studentId");
        service.delete(id, studentId);
        redirectAttributes.addFlashAttribute("success", "Coursera Course deleted successfully.");
        return "redirect:/coursera";
    }

    private void addFormOptions(Model model) {
        // no dropdown options needed for this form
    }
}
