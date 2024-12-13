package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Userassignmentdone;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssignmentDoneRepository extends JpaRepository<Userassignmentdone, Long> {
    Userassignmentdone findByUserIdAndAssignmentId(Long userId, Long assignmentId);
    Long countByAssignmentId(Long assignmentId);
}
