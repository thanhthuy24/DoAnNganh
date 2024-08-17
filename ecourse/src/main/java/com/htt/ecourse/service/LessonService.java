package com.htt.ecourse.service;

import com.htt.ecourse.dtos.LessonDTO;
import com.htt.ecourse.dtos.VideoDTO;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LessonService {
    public Lesson createLesson(LessonDTO lessonDTO);
    Lesson getLesson(Long id);
    Page<Lesson> getAllLessons(Pageable pageable);
    Lesson updateLesson(Long id, LessonDTO lessonDTO);
    void deleteLesson(Long id);
    boolean existByName(String name);
    Video createVideoDTO(Long lessionId, VideoDTO videoDTO) throws InvalidParamException;
}
