package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Score;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    Optional<Score> findById(Long id);
    Optional<Score> findByUserIdAndAssignmentId(Long userId, Long assignmentId);
}