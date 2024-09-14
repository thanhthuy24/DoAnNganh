package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Assignment;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    Page<Assignment> findAll(Pageable pageable);
    List<Assignment> findByLessonId(Long lessonId);
    List<Assignment> findByCourseId(Long courseId);
//    Assignment findByAssignmentId(Long id);
}
