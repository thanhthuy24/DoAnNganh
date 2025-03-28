package com.htt.ecourse.service;

import com.htt.ecourse.dtos.LessonDTO;
import com.htt.ecourse.dtos.LessonVideoDTO;
import com.htt.ecourse.dtos.LessonVideoIntro;
import com.htt.ecourse.dtos.VideoDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Video;
import com.htt.ecourse.responses.LessonResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface LessonService {
//    Admin use
    Lesson createLesson(LessonDTO lessonDTO);
    Lesson getLessonById(Long id);
    Page<Lesson> getAllLessons(String keyword, PageRequest pageRequest);
    Lesson updateLesson(Long id, LessonDTO lessonDTO);
    Lesson updateActiveLesson(Long id,LessonDTO lessonDTO);
    void deleteLesson(Long id);
    boolean existByName(String name);

    List<LessonVideoDTO> getLessonByCourseId(Long courseId) throws DataNotFoundException;
    Video createVideo(Long lessonId, VideoDTO videoDTO) throws InvalidParamException;

    Long countLessonInCourse(Long courseId);

    LessonVideoIntro getFirstLesson(Long courseId);
}
