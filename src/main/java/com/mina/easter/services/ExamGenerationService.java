package com.mina.easter.services;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mina.easter.dtos.ExamDto;
import com.mina.easter.dtos.OptionDto;
import com.mina.easter.dtos.QuestionDto;
import com.mina.easter.dtos.GenerateExamDto;
import com.mina.easter.entities.Exam;
import com.mina.easter.repositories.CourseRepository;
import com.mina.easter.repositories.ExamRepository;
import com.mina.easter.repositories.QuestionRepository;

@Service
public class ExamGenerationService {
    private final ExamRepository examRepository;
    private final CourseRepository courseRepository;
    private final QuestionRepository questionRepository;

    public ExamGenerationService(ExamRepository _examRepository, CourseRepository _courseRepository, QuestionRepository _questionRepository) {
        this.examRepository = _examRepository;
        this.courseRepository = _courseRepository;
        this.questionRepository = _questionRepository;
    }

    public long generateExamForCourse(GenerateExamDto dto) {
        var course = courseRepository.findById(dto.getCourseId()).orElseThrow();

        var questions = questionRepository.findRandomQuestionsByCourseId(dto.getCourseId(), PageRequest.of(0, dto.getNumberOfQuestions()));

        Exam exam = new Exam();
        exam.setTitle(dto.getExamTitle());
        exam.setCourse(course);
        exam.setQuestions(questions);
        examRepository.save(exam);

        return exam.getId();
    }

    public ExamDto getExamById(Long examId) {
        var exam = examRepository.findById(examId).orElseThrow();
        var dto = new ExamDto();
        dto.setId(exam.getId());
        dto.setTitle(exam.getTitle());
        dto.setCourseId(exam.getCourse().getId());
        dto.setQuestions(exam.getQuestions().stream().map(q -> {
            var qDto = new QuestionDto();
            qDto.setId(q.getId());
            qDto.setText(q.getText());
            qDto.setCourseId(q.getCourse().getId());
            qDto.setOptions(q.getOptions().stream().map(o -> {
                var oDto = new OptionDto();
                oDto.setId(o.getId());
                oDto.setText(o.getText());
                oDto.setCorrect(o.isCorrect());
                return oDto;
            }).toList());
            return qDto;
        }).toList());
        return dto;
    }

    public List<Long> getAllExamIds() {
        return examRepository.findAll().stream().map(Exam::getId).toList();
    }
}
