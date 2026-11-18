package com.iips.skilltracker.controller;

import com.iips.skilltracker.entity.SwayamCourse;
import com.iips.skilltracker.service.SwayamService;
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
@RequestMapping("/swayam")
public class SwayamController {

    private final SwayamService service;

    public SwayamController(SwayamService service) {
        this.service = service;
    }

    @GetMapping
    public String list(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        model.addAttribute("items", service.getAllForStudent(studentId));
        return "swayam";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("item", new SwayamCourse());
        addFormOptions(model);
        return "swayam-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id,
                               HttpSession session,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Long studentId = (Long) session.getAttribute("studentId");
        Optional<SwayamCourse> item = service.getOwned(id, studentId);
        if (item.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Record not found.");
            return "redirect:/swayam";
        }
        model.addAttribute("item", item.get());
        addFormOptions(model);
        return "swayam-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("item") SwayamCourse item,
                       BindingResult result,
                       HttpSession session,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addFormOptions(model);
            return "swayam-form";
        }
        Long studentId = (Long) session.getAttribute("studentId");
        if (!service.save(item, studentId)) {
            redirectAttributes.addFlashAttribute("error", "Record not found.");
            return "redirect:/swayam";
        }
        redirectAttributes.addFlashAttribute("success", "SWAYAM Course saved successfully.");
        return "redirect:/swayam";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        Long studentId = (Long) session.getAttribute("studentId");
        service.delete(id, studentId);
        redirectAttributes.addFlashAttribute("success", "SWAYAM Course deleted successfully.");
        return "redirect:/swayam";
    }

    private void addFormOptions(Model model) {
        // no dropdown options needed for this form
    }
}
