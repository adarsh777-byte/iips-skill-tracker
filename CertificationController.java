package com.iips.skilltracker.controller;

import com.iips.skilltracker.entity.Certification;
import com.iips.skilltracker.service.CertificationService;
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
@RequestMapping("/certifications")
public class CertificationController {

    private final CertificationService service;

    public CertificationController(CertificationService service) {
        this.service = service;
    }

    @GetMapping
    public String list(HttpSession session, Model model) {
        Long studentId = (Long) session.getAttribute("studentId");
        model.addAttribute("items", service.getAllForStudent(studentId));
        return "certifications";
    }

    @GetMapping("/new")
    public String showAddForm(Model model) {
        model.addAttribute("item", new Certification());
        addFormOptions(model);
        return "certification-form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") Long id,
                               HttpSession session,
                               Model model,
                               RedirectAttributes redirectAttributes) {
        Long studentId = (Long) session.getAttribute("studentId");
        Optional<Certification> item = service.getOwned(id, studentId);
        if (item.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Record not found.");
            return "redirect:/certifications";
        }
        model.addAttribute("item", item.get());
        addFormOptions(model);
        return "certification-form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("item") Certification item,
                       BindingResult result,
                       HttpSession session,
                       Model model,
                       RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            addFormOptions(model);
            return "certification-form";
        }
        Long studentId = (Long) session.getAttribute("studentId");
        if (!service.save(item, studentId)) {
            redirectAttributes.addFlashAttribute("error", "Record not found.");
            return "redirect:/certifications";
        }
        redirectAttributes.addFlashAttribute("success", "Certification saved successfully.");
        return "redirect:/certifications";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        Long studentId = (Long) session.getAttribute("studentId");
        service.delete(id, studentId);
        redirectAttributes.addFlashAttribute("success", "Certification deleted successfully.");
        return "redirect:/certifications";
    }

    private void addFormOptions(Model model) {
        model.addAttribute("statusOptions", List.of("Active", "Expired", "No Expiry"));
    }
}
