package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Essay;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EssayRepository extends JpaRepository<Essay, Long> {
    Page<Essay> findByAssignmentId(Long assignmentId, PageRequest pageRequest);
    Essay findByQuestionId(Long questionId);
//    List<Essay> findByAssignment(Long assignmentId);
}
