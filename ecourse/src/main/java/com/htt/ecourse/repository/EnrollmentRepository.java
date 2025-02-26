package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Enrollment;
import com.htt.ecourse.pojo.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);
    List<Enrollment> findByUserId(Long userId);
    long countEnrollmentByCourseId(Long courseId);
    Long countEnrollmentByUserId(Long userId);
    List<Enrollment> findAllByCourseId(Long courseId);

    @Query("SELECT e.user.id FROM Enrollment e where e.course.id = :courseId")
    List<Long> findAllEnrollmentByCourseId(@Param("courseId") Long courseId);

    @Query("SELECT e.user FROM Enrollment e WHERE e.course.id = :courseId")
    List<User> findUsersByCourseId(@Param("courseId") Long courseId);
}
