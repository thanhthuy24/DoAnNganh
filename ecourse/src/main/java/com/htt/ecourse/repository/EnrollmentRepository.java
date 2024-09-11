package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
//    Enrollment findByUserIdAndCourseId(Long userId, Long courseId);
    List<Enrollment> findByUserId(Long userId);
    @Query("SELECT e FROM Enrollment e WHERE e.course.id = :courseId AND e.user.id = :userId")
    List<Enrollment> findByUserIdAndCourseId(@Param("courseId") Long courseId, @Param("userId") Long userId);

}
