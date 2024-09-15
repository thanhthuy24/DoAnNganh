package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Answerchoice;
import com.htt.ecourse.responses.AnswerChoiceResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AnswerChoiceRepository extends JpaRepository<Answerchoice, Long> {
    Optional<Answerchoice> findById(Long id);
    List<Answerchoice> findByAssignmentId(Long assignmentId);
    Optional<Answerchoice> findByAssignmentIdAndQuestionIdAndChoiceId
            (Long assignmentId, Long questionId, Long choiceId);

}
