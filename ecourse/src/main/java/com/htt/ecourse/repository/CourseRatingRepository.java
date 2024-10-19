package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Courserating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRatingRepository extends JpaRepository<Courserating, Long> {
    Optional<Courserating> findByCourseIdAndUserId(Long courseId, Long userId);
    Page<Courserating> findByCourseId(Long courseId, PageRequest pageRequest);
    List<Courserating> findByCourseId(Long courseId);
    Long countByCourseId(Long courseId);
    Long countByRating(Long rating);
    Long countByCourseIdAndRating(Long courseId, Long rating);
}
