package com.mina.easter.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mina.easter.dtos.CourseQuestionsRequestDto;
import com.mina.easter.dtos.NewQuestionDto;
import com.mina.easter.dtos.OptionDto;
import com.mina.easter.dtos.QuestionDto;
import com.mina.easter.entities.Option;
import com.mina.easter.entities.Question;
import com.mina.easter.repositories.CourseRepository;
import com.mina.easter.repositories.OptionRepository;
import com.mina.easter.repositories.QuestionRepository;

@Service
public class QuestionBankService {
    private final CourseRepository courseRepository;
    private final QuestionRepository questionRepository;
    private final OptionRepository optionRepository;
    
    public QuestionBankService(CourseRepository _courseRepository, QuestionRepository _questionRepository, OptionRepository _optionRepository) {
        this.courseRepository = _courseRepository;
        this.questionRepository = _questionRepository;
        this.optionRepository = _optionRepository;
    }

    public QuestionDto submitQuestion(NewQuestionDto qtn) {
        var course = courseRepository.getReferenceById(qtn.getCourseId());
        
        var question = new Question();
        question.setText(qtn.getText());
        question.setCourse(course);
        
        var savedQuestion = questionRepository.saveAndFlush(question);

        List<Option> options = new ArrayList<>();

        qtn.getOptions().forEach(opt -> {
            var option = new Option();
            option.setText(opt.getText());
            option.setCorrect(opt.isCorrect());
            option.setQuestion(savedQuestion);
            options.add(option);
        });

        optionRepository.saveAllAndFlush(options);

        var savedQuestionWithOptions = questionRepository.findById(savedQuestion.getId()).orElseThrow();

        return new QuestionDto() {
            {
                setId(savedQuestionWithOptions.getId());
                setText(savedQuestionWithOptions.getText());
                setCourseId(savedQuestionWithOptions.getCourse().getId());
                setOptions(new ArrayList<>());
                setOptions(savedQuestionWithOptions.getOptions().stream().map(option -> {
                    var opt = new OptionDto();
                    opt.setId(option.getId());
                    opt.setText(option.getText());
                    opt.setCorrect(option.isCorrect());
                    return opt;
                }).collect(Collectors.toList()));
            }
        };
    }

    public List<QuestionDto> getCourseQuestionsPage(CourseQuestionsRequestDto requestDto) {
        return questionRepository.findByCourseId(requestDto.getCourseId(), PageRequest.of(requestDto.getPageNumber(), requestDto.getPageSize()  ))
                .stream()
                .map(question -> {
                    var qtn = new QuestionDto();
                    qtn.setId(question.getId());
                    qtn.setText(question.getText());
                    qtn.setCourseId(question.getCourse().getId());
                    qtn.setOptions(question.getOptions().stream().map(option -> {
                        var opt = new OptionDto();
                        opt.setId(option.getId());
                        opt.setText(option.getText());
                        opt.setCorrect(option.isCorrect());
                        return opt;
                    }).collect(Collectors.toList()));
                    return qtn;
                })
                .collect(Collectors.toList());
    }
}
