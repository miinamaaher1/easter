package com.mina.easter.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.mina.easter.dtos.CourseDto;
import com.mina.easter.repositories.CourseRepository;

@Service
public class LookupService {
    private final CourseRepository courseRepository;

    public LookupService(CourseRepository _courseRepository) {
        this.courseRepository = _courseRepository;
    }

    public List<CourseDto> getAllCourses() {
        return courseRepository.findAll().stream().map(c -> {
            var dto = new CourseDto();
            dto.setId(c.getId());
            dto.setName(c.getName());
            return dto;
        }).toList();
    }
}
