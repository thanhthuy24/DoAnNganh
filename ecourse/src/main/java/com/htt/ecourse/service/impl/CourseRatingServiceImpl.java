package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.CourseRatingDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.*;
import com.htt.ecourse.repository.*;
import com.htt.ecourse.service.CourseRatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseRatingServiceImpl implements CourseRatingService {
    private final EnrollmentRepository enrollmentRepository;
    private final ProgressRepository progressRepository;
    private final CourseRatingRepository courseRatingRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    @Override
    public Courserating createRating(CourseRatingDTO courseRatingDTO) throws DataNotFoundException {
        Course existingCourse = courseRepository
                .findById(courseRatingDTO.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found!!"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Optional<Enrollment> checkEnrollment = enrollmentRepository.findByUserIdAndCourseId(user.getId(), courseRatingDTO.getCourseId());
        if (checkEnrollment == null) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "You must register course before rating!!!");
        }

        Optional<Progress> checkProgress = progressRepository
                .findByCourseIdAndUserId(existingCourse.getId(), user.getId());
        if (checkProgress.get().getStatus() == "In Progress") {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "You must complete!!!");
        }

        Optional<Courserating> existingRating = courseRatingRepository
                .findByCourseIdAndUserId(courseRatingDTO.getCourseId(), user.getId());

        if (!existingRating.isEmpty()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Rating already exist!!!");
        }

        Courserating newRating = Courserating.builder()
                .rating(courseRatingDTO.getRating())
                .course(existingCourse)
                .ratingDate(new Date())
                .comment(courseRatingDTO.getComment())
                .user(user)
                .build();

        courseRatingRepository.save(newRating);
        return newRating;

    }

    @Override
    public Page<Courserating> getRatingByCourseId(Long courseId, PageRequest pageRequest) throws DataNotFoundException {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found!!"));

        return courseRatingRepository.findByCourseId(courseId, pageRequest)
                .map(Courserating::fromRating);
    }

    @Override
    public Float averageRatingByCourseId(Long courseId) throws DataNotFoundException {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found!!"));

        List<Courserating> getCourseRating = courseRatingRepository.findByCourseId(existingCourse.getId());
        Long countRating = courseRatingRepository.countByCourseId(existingCourse.getId());

        if (countRating == 0) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Don't have any rating to count!!!");
        }

        Long sum = 0L;
        for (Courserating courseRating : getCourseRating) {
            sum += courseRating.getRating();
        }

        // Calculate average and round to 1 decimal place
        Float averageRating = (float) sum / countRating;
        averageRating = Math.round(averageRating * 10) / 10.0f;

        return averageRating;
    }

    @Override
    public Long countAll(Long courseId) throws DataNotFoundException {
        Course existingCourse = getExistingCourse(courseId);
        Long countRating = courseRatingRepository.countByCourseId(existingCourse.getId());

        if (countRating == 0) {
            return 0L;
        }
        return countRating;
    }

    private Course getExistingCourse(Long courseId) throws DataNotFoundException {
        return courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found!!"));
    }

    @Override
    public Long countRatingByCourseIdByRating(Long courseId, Long rating) throws DataNotFoundException {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found!!"));

        Long countRating = courseRatingRepository.countByCourseIdAndRating(existingCourse.getId(), rating);
        if (countRating == 0) {
            return 0L;
        }
        return countRating;
    }

    @Override
    public Float averageRatingByStar(Long rate, Long courseId) throws DataNotFoundException {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Course not found!!"));

        List<Courserating> getCourseRating = courseRatingRepository.findByCourseId(existingCourse.getId());
        //đếm số luong tai khoan danh gia khoa hoc nay
        Long countAll = courseRatingRepository.countByCourseId(existingCourse.getId());
        Long countRate = courseRatingRepository.countByCourseIdAndRating(courseId, rate);

        if (countAll == 0) {
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Don't have any rating to count!!!");
        }

        Float averageRating = (float) countRate / countAll;

        // Nhân với 100 để chuyển thành phần trăm và làm tròn
        Float percentageRating = (float) Math.round(averageRating * 100);

        return percentageRating;
    }
}
