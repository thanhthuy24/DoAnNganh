package com.htt.ecourse.service;

import com.htt.ecourse.dtos.EnrollmentDTO;
import com.htt.ecourse.pojo.Enrollment;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EnrollmentService {
    Enrollment getEnrollmentByCourseAndUser(Long courseId, Long userId);
    List<Enrollment> getEnrollmentByUser(Long userId);

    Enrollment createEnrollment(EnrollmentDTO enrollmentDTO);
    List<Enrollment> findByUserIdAndCourseId(@Param("courseId") Long courseId, @Param("userId") Long userId);
}
