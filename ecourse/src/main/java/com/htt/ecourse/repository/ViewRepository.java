package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.View;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ViewRepository extends JpaRepository<View, Long> {
    View findByUserIdAndCourseId(Long userId, Long courseId);
}
