package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Courserating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRatingRepository extends JpaRepository<Courserating, Long> {
    Optional<Courserating> findByCourseIdAndUserId(Long courseId, Long userId);
}
