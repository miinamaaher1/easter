package com.mina.easter.dtos;

import lombok.Data;

@Data
public class CourseQuestionsRequestDto {
    private Long courseId;
    private int pageNumber;
    private int pageSize;
}
