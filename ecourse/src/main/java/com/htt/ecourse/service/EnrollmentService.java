package com.htt.ecourse.service;

import com.htt.ecourse.dtos.EnrollmentDTO;
import com.htt.ecourse.pojo.Enrollment;

import java.util.List;

public interface EnrollmentService {
    List<Enrollment> getEnrollmentByCourseAndUser(Long courseId, Long userId);
    List<Enrollment> getEnrollmentByUser(Long userId);

    Enrollment createEnrollment(EnrollmentDTO enrollmentDTO);
}
