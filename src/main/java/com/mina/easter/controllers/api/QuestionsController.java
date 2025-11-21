package com.mina.easter.controllers.api;

import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.mina.easter.dtos.CourseQuestionsRequestDto;
import com.mina.easter.dtos.NewQuestionDto;
import com.mina.easter.dtos.QuestionDto;
import com.mina.easter.services.QuestionBankService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/questions")
public class QuestionsController {
    private final QuestionBankService questionBankService;

    public QuestionsController(QuestionBankService _questionBankService) {
        this.questionBankService = _questionBankService;
    }

    @PostMapping
    @Transactional
    @ResponseStatus(HttpStatus.CREATED)
    public QuestionDto submitQuestion(@RequestBody NewQuestionDto dto) {
        return questionBankService.submitQuestion(dto);
    }

    @PostMapping("list")
    @ResponseStatus(HttpStatus.OK)
    public List<QuestionDto> getCourseQuestions(@RequestBody CourseQuestionsRequestDto dto) {
        return questionBankService.getCourseQuestionsPage(dto);
    }
}
