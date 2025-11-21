package com.mina.easter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mina.easter.entities.Exam;

public interface ExamRepository extends JpaRepository<Exam, Long> {
    
}
