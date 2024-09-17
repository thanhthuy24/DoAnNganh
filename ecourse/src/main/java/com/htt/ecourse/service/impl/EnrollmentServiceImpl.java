package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.EnrollmentDTO;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Enrollment;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.CourseRepository;
import com.htt.ecourse.repository.EnrollmentRepository;
import com.htt.ecourse.repository.ProgressRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.EnrollmentService;
import com.htt.ecourse.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository ;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;
    private final ProgressService progressService;

    @Override
    public Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId) {
        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
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

        progressService.calculateProgress(existingCourse.getId());

        return enrollment;
    }
}
