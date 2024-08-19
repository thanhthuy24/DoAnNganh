package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    boolean existsByName(String name);
}