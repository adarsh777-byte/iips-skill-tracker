package com.iips.skilltracker.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

/** Makes the logged-in student's name available to every page (used by the header). */
@ControllerAdvice
public class GlobalModelAdvice {

    @ModelAttribute("loggedStudentName")
    public String loggedStudentName(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session == null ? null : (String) session.getAttribute("studentName");
    }
}
