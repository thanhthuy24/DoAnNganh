package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Progress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProgressRepository extends JpaRepository<Progress, Long> {
    Optional<Progress> findByCourseIdAndUserId(Long courseId, Long userId);

}
