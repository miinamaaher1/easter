package com.mina.easter.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mina.easter.entities.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    List<Question> findByCourseId(Long courseId, Pageable pg);

    @Query(value = "SELECT * FROM question WHERE course_id = :courseId ORDER BY RAND()", nativeQuery = true)
    List<Question> findRandomQuestionsByCourseId(@Param("courseId") Long courseId, Pageable pageable);

    @Query("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.options WHERE q.id = :id")
    Optional<Question> findByIdWithOptions(@Param("id") Long id);
}
