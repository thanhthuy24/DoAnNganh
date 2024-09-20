package com.htt.ecourse.service;

import com.htt.ecourse.dtos.EnrollmentDTO;
import com.htt.ecourse.pojo.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {
    Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId);
    List<Enrollment> getEnrollmentByUser();

    Enrollment createEnrollment(EnrollmentDTO enrollmentDTO);
}
