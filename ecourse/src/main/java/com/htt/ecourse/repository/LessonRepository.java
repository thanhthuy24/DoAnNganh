package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    boolean existsByName(String name);
    Page<Lesson> findAll(Pageable pageable);
    List<Lesson> findByCourseId(Long courseId);
}
