package com.mina.easter.controllers.web;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.mina.easter.dtos.GenerateExamDto;
import com.mina.easter.services.ExamGenerationService;
import com.mina.easter.services.LookupService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/exams")
public class ExamsController {
    private final ExamGenerationService examGenerationService;
    private final LookupService lookupService;

    public ExamsController(ExamGenerationService examGenerationService, LookupService lookupService) {
        this.examGenerationService = examGenerationService;
        this.lookupService = lookupService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public long generateExam(@RequestBody GenerateExamDto dto) {
        return examGenerationService.generateExamForCourse(dto);
    }

    @GetMapping("/{examId}")
    public String getExamById(Model model, @PathVariable Long examId) {
        model.addAttribute("exam", examGenerationService.getExamById(examId));
        return "exam-content";
    }

    @GetMapping
    public String examsPage(Model model) {
        model.addAttribute("examIds", examGenerationService.getAllExamIds());
        model.addAttribute("courses", lookupService.getAllCourses());
        return "exams-list";
    }
}
