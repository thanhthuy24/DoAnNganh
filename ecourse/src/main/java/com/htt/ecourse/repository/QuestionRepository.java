package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByAssignmentId(Long id);
//    Question findById(Long id);
}
