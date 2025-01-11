package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    boolean existsByName(String name);
    Page<Lesson> findAll(Pageable pageable);
    List<Lesson> findByCourseId(Long courseId);
    @Query("SELECT COUNT(l) FROM Lesson l WHERE l.course.id = :courseId")
    long countLessonsByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT l FROM Lesson l WHERE l.course.id = :courseId ORDER BY l.createdDate LIMIT 1")
    Lesson findFirstLesson(@Param("courseId") Long courseId);
}
