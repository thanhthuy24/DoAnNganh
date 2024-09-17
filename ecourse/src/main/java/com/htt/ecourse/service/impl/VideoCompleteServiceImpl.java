package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.VideoCompleteDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Enrollment;
import com.htt.ecourse.pojo.Video;
import com.htt.ecourse.pojo.Videocompleted;
import com.htt.ecourse.repository.EnrollmentRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.repository.VideoCompletedRepository;
import com.htt.ecourse.repository.VideoRepository;
import com.htt.ecourse.service.ProgressService;
import com.htt.ecourse.service.VideoCompleteService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VideoCompleteServiceImpl implements VideoCompleteService {
    private final VideoCompletedRepository videoCompletedRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final ProgressService progressService;

    @Override
    public Videocompleted createVideocompleted(VideoCompleteDTO videoCompleteDTO) throws DataNotFoundException {
        Video existingVideo = videoRepository.findById(videoCompleteDTO.getVideoId())
                .orElseThrow(() -> new DataNotFoundException("Video not found!"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.getUserByUsername(username).getId();

        Videocompleted checkVideoCompleted = videoCompletedRepository.findByVideoIdAndUserId(videoCompleteDTO.getVideoId(), userId);
        if (checkVideoCompleted != null) {
            throw new DataNotFoundException("Video already completed!");
        }

        Optional<Enrollment> checkEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, existingVideo.getLesson().getCourse().getId());
        if (checkEnrollment.isEmpty()) {
            throw new DateTimeException("This course isn't enrolled in your list! Please enroll before participating in this course!!");
        }

        Videocompleted videocompleted = Videocompleted.builder()
                .video(existingVideo)
                .user(userRepository.findById(userId).get())
                .completedDate(new Date())
                .build();

        videoCompletedRepository.save(videocompleted);

        progressService.calculateProgress(existingVideo.getLesson().getCourse().getId());

        return videocompleted;
    }
}
