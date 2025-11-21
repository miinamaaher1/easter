package com.mina.easter.dtos;

import java.util.List;

import lombok.Data;

@Data
public class ExamDto {
    private Long id;
    private String title;
    private Long courseId;
    private List<QuestionDto> questions;
}
