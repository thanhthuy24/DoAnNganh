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
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
    public Boolean checkEnrolled(Long userId, Long courseId) {
        Optional<Enrollment> listEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
        if (listEnrollment.isPresent()) {
            return true;
        }
        return false;
    }

    @Override
    public Optional<Enrollment> findByUserIdAndCourseId(Long userId, Long courseId) {
        return enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
    }

    @Override
    public List<Enrollment> getEnrollmentByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        List<Enrollment> enrollments = enrollmentRepository.findByUserId(user.getId());
        return enrollments;
    }

    @Override
    public List<Enrollment> getCousesEnrolledByUser(Long userId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long role = userRepository.getUserByUsername(username).getRole().getId();
        if (role != 2){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Authorization!!"
            );
        }
        return enrollmentRepository.findByUserId(userId);
    }

    @Override
    public Enrollment createEnrollment(EnrollmentDTO enrollmentDTO) {
        Course existingCourse = courseRepository.findById(enrollmentDTO.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cannot find course with id " + enrollmentDTO.getCourseId()));

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

    @Override
    public Long countEnrollmentByUserId(Long userId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long role = userRepository.getUserByUsername(username).getRole().getId();
        if (role != 2){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Authorization!!"
            );
        }

        return enrollmentRepository.countEnrollmentByUserId(userId);
    }

    @Override
    public Long getCountEnrollmentByCourseId(Long courseId) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Cannot find course with id " + courseId));

        Long count = enrollmentRepository.countEnrollmentByCourseId(existingCourse.getId());
        if (count != null) {
            return count;
        }
        return 0L;
    }
}
