package com.mina.easter.dtos;

import java.util.List;

import lombok.Data;

@Data
public class QuestionDto {
    private Long id;
    private String text;
    private Long courseId;
    private List<OptionDto> options;
}
