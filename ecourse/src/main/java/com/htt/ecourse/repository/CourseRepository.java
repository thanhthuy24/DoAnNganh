package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Course;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByName(String name);
    Page<Course> findAll(Pageable pageable); //phân trang
    List<Course> findByTeacherId(Long teacherId);
}

