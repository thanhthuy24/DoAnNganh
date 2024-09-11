package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.EnrollmentDTO;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Enrollment;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.CourseRepository;
import com.htt.ecourse.repository.EnrollmentRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository ;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public List<Enrollment> findByUserIdAndCourseId(Long courseId, Long userId) {
        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public Enrollment getEnrollmentByCourseAndUser(Long courseId, Long userId) {
        return null;
    }

    @Override
    public List<Enrollment> getEnrollmentByUser(Long userId) {
        return enrollmentRepository.findByUserId(userId);
    }

    @Override
    public Enrollment createEnrollment(EnrollmentDTO enrollmentDTO) {
        Course existingCourse = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new DateTimeException("Cannot find course with id " + enrollmentDTO.getCourseId()));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user1 = userRepository.getUserByUsername(username);
        Enrollment enrollment = Enrollment.builder()
                .course(existingCourse)
                .user(user1)
                .build();
        enrollmentRepository.save(enrollment);
        return enrollment;
    }
}
