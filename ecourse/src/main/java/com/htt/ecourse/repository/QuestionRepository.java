package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByAssignmentId(Long id);
    Optional<Question> findById(Long id);
    Long countQuestionsByAssignmentId(Long assignmentId);
}
