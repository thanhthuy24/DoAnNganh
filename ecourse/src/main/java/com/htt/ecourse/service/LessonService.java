package com.htt.ecourse.service;

import com.htt.ecourse.dtos.LessonDTO;
import com.htt.ecourse.dtos.VideoDTO;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Video;
import com.htt.ecourse.responses.LessonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface LessonService {
//    Admin use
    Lesson createLesson(LessonDTO lessonDTO);
    Lesson getLessonById(Long id);
    Page<LessonResponse> getAllLessons(PageRequest pageRequest);
    Lesson updateLesson(Long id, LessonDTO lessonDTO);
    Lesson updateActiveLesson(Long id,LessonDTO lessonDTO);
    void deleteLesson(Long id);
    boolean existByName(String name);

    Video createVideo(Long lessionId, VideoDTO videoDTO) throws InvalidParamException;
}
