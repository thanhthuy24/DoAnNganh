package com.htt.ecourse.service.impl;

import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Enrollment;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Progress;
import com.htt.ecourse.repository.*;
import com.htt.ecourse.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProgressServiceImpl implements ProgressService {
    private final ProgressRepository progressRepository;
    private final VideoRepository videoRepository;
    private final VideoCompletedRepository videoCompletedRepository;
    private final UserRepository userRepository;
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public float calculateProgress(Long courseId) {
        List<Lesson> lessonList = lessonRepository.findByCourseId(courseId);

        if (lessonList.isEmpty())
            return 0f;

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long user = userRepository.getUserByUsername(username).getId();

        Long totalVideoByLesson = 0L;
        Long totalVideoCompleted = 0L;
        for (Lesson lesson : lessonList) {
            Long videoCount = videoRepository.countByLessonId(lesson.getId());
            Long videoCompleted = videoCompletedRepository.countWatchedVideosByUserAndLessonId(user, lesson.getId());
            totalVideoByLesson += videoCount;
            totalVideoCompleted += videoCompleted;
        }

        Float progress;
        if(totalVideoByLesson > 0) {
            progress = (float) (totalVideoCompleted * 100) / totalVideoByLesson;
        } else {
            progress = 0.0f;
        }

        Optional<Progress> checkProgress = progressRepository.findByCourseIdAndUserId(courseId, user);
        if (checkProgress.isPresent()) {
            Progress existingProgress = checkProgress.get();
            existingProgress.setCompletionPercentage(progress);
            existingProgress.setStatus(progress == 100.0f ? "Completed" : "In Progress");
            existingProgress.setUpdatedDate(new Date());
            progressRepository.save(existingProgress);
            return existingProgress.getCompletionPercentage();
        }

        Progress newProcess = new Progress();
        try {
            newProcess.setUser(userRepository.findById(user)
                    .orElseThrow(() -> new DataNotFoundException("User not found")));
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            newProcess.setCourse(courseRepository.findById(courseId).orElseThrow(() -> new DataNotFoundException("Course not found")));
        } catch (DataNotFoundException e) {
            throw new RuntimeException(e);
        }

        newProcess.setCompletionPercentage(progress);
        newProcess.setStatus(progress == 100.0f ? "Completed" : "In Progress");
        newProcess.setUpdatedDate(new Date());

        progressRepository.save(newProcess);
        return progress;
    }

    @Override
    public Optional<Progress> getProgressByAdmin(Long userId, Long courseId) {
        Optional<Progress> progressList = progressRepository.findByCourseIdAndUserId(courseId, userId);
        if (progressList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return progressList;
    }

    @Override
    public Optional<Progress> getProgressByUser(Long courseId){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long role = userRepository.getUserByUsername(username).getRole().getId();
        Long userId = userRepository.getUserByUsername(username).getId();
        if(role == 1) { //role = 3 -> teacher
            Optional<Enrollment> checkEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
            if (checkEnrollment.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "You must enroll this course first!!"
                );
            }
        } else  {
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "You dont have permission!!"
            );
        }
        Optional<Progress> progressList = progressRepository.findByCourseIdAndUserId(courseId, userId);
        if (progressList.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found");
        }
        return progressList;
    }
}
