package com.htt.ecourse.service;

import com.htt.ecourse.dtos.EnrollmentDTO;
import com.htt.ecourse.pojo.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    Boolean checkEnrolled(Long userId, Long courseId);
    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);
    List<Enrollment> getEnrollmentByUser();
    Long getCountEnrollmentByCourseId(Long courseId);
    Enrollment createEnrollment(EnrollmentDTO enrollmentDTO);
}
