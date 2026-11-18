package com.iips.skilltracker.controller;

import com.iips.skilltracker.entity.NptelCourse;
import com.iips.skilltracker.service.NptelService;
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
@RequestMapping("/nptel")
public class NptelController {

    private final NptelService service;

    public NptelController(NptelService service) {
        this.service = service;
    }

    @GetMapping
    public String list(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        model.addAttribute("items", service.getAllForStudent(studentId));
        return "nptel";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("item", new NptelCourse());
        addFormOptions(model);
        return "nptel-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id,
                               HttpSession session,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Long studentId = (Long) session.getAttribute("studentId");
        Optional<NptelCourse> item = service.getOwned(id, studentId);
        if (item.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Record not found.");
            return "redirect:/nptel";
        }
        model.addAttribute("item", item.get());
        addFormOptions(model);
        return "nptel-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("item") NptelCourse item,
                       BindingResult result,
                       HttpSession session,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addFormOptions(model);
            return "nptel-form";
        }
        Long studentId = (Long) session.getAttribute("studentId");
        if (!service.save(item, studentId)) {
            redirectAttributes.addFlashAttribute("error", "Record not found.");
            return "redirect:/nptel";
        }
        redirectAttributes.addFlashAttribute("success", "NPTEL Course saved successfully.");
        return "redirect:/nptel";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        Long studentId = (Long) session.getAttribute("studentId");
        service.delete(id, studentId);
        redirectAttributes.addFlashAttribute("success", "NPTEL Course deleted successfully.");
        return "redirect:/nptel";
    }

    private void addFormOptions(Model model) {
        model.addAttribute("gradeOptions", List.of("Elite", "Elite + Silver", "Elite + Gold", "Successfully Completed"));
    }
}
