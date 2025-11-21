package com.mina.easter.dtos;

import java.util.List;

import lombok.Data;

@Data
public class NewQuestionDto {
    private String text;
    private Long courseId;
    private List<NewOptionDto> options;
}
