package com.mina.easter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mina.easter.entities.Course;

public interface CourseRepository extends JpaRepository<Course, Long> {

}
