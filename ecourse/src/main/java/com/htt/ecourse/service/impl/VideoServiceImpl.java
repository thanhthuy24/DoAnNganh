package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.VideoDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Video;
import com.htt.ecourse.repository.LessonRepository;
import com.htt.ecourse.repository.VideoRepository;
import com.htt.ecourse.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService {
    private final VideoRepository videoRepository;
    private final LessonRepository lessonRepository;

    @Override
    public Long countVideoByLessonId(Long lessonId) {
        Long number = videoRepository.countByLessonId(lessonId);
        if (number != null) {
            return number;
        }
        return 0L;
    }

    @Override
    public List<Video> findByLessonId(Long lessonId) throws DataNotFoundException {
        Lesson existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy bài học tương ứng"));
        List<Video> list = videoRepository.findByLessonId(lessonId);
        if (list != null) {
            return list;
        }
        return null;
    }

    @Override
    public Video updateVideo(Long videoId, VideoDTO videoDTO) {
        Video existingVideo = videoRepository.findById(videoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find video with thid id"));
        if (existingVideo != null) {
            Lesson existingLesson = lessonRepository.findById(videoDTO.getLessonId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy bài học tương ứng"));
            existingVideo.setName(videoDTO.getName());
            existingVideo.setDescription(videoDTO.getDescription());
            existingVideo.setLesson((existingLesson));
            return videoRepository.save(existingVideo);
        }
        return null;
    }
}
