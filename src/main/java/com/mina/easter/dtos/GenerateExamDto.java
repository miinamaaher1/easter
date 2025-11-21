package com.mina.easter.dtos;

import lombok.Data;

@Data
public class GenerateExamDto {
    private Long courseId;
    private int numberOfQuestions;
    private String examTitle;
}
