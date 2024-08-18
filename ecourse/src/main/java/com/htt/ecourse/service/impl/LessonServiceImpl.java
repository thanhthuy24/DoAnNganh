package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.LessonDTO;
import com.htt.ecourse.dtos.VideoDTO;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Video;
import com.htt.ecourse.repository.CourseRepository;
import com.htt.ecourse.repository.LessonRepository;
import com.htt.ecourse.repository.VideoRepository;
import com.htt.ecourse.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final VideoRepository videoRepository;
    @Override
    public Lesson createLesson(LessonDTO lessonDTO) {
        Course existCourse = courseRepository
                .findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new DateTimeException("Can nit find course by id " + lessonDTO.getCourseId()));

        Lesson newLesson = Lesson.builder()
                .name(lessonDTO.getName())
                .description(lessonDTO.getDescription())
                .course(existCourse)
                .build();
        return lessonRepository.save(newLesson);
    }

    @Override
    public Lesson getLesson(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new DateTimeException("Can nit find lesson by id " + id));
    }

    @Override
    public Page<Lesson> getAllLessons(Pageable pageable) {
        return lessonRepository.findAll(pageable);
    }

    @Override
    public Lesson updateLesson(Long id, LessonDTO lessonDTO) {
        Lesson existingLesson = getLesson(id);
        if (existingLesson != null) {
            Course existCouse = courseRepository.findById(lessonDTO.getCourseId())
                    .orElseThrow(() -> new DateTimeException("Can nit find course by id " + id));
            existingLesson.setName(lessonDTO.getName());
            existingLesson.setDescription(lessonDTO.getDescription());
            existingLesson.setCourse(existCouse);
            return lessonRepository.save(existingLesson);
        }
        return null;
    }

    @Override
    public void deleteLesson(Long id) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);
        optionalLesson.ifPresent(lessonRepository::delete);
    }

    @Override
    public boolean existByName(String name) {
        return lessonRepository.existsByName(name);
    }

    @Override
    public Video createVideo(
            Long lessonId,
            VideoDTO videoDTO) throws InvalidParamException {
        Lesson existingLesson = lessonRepository
                .findById(lessonId)
//                .findById(videoDTO.getLessonId())
                .orElseThrow(() -> new DateTimeException("Can not find Lesson with id " + videoDTO.getLessonId()));

        Video newVideo = Video.builder()
                .name(videoDTO.getName())
                .lesson(existingLesson)
                .build();

        //khong cho insert qua 5 video trong 1 lesson
        int size = videoRepository.findByLessonId(lessonId).size();
        if (size >= Video.MAXIMUM_VIDEOS_PER_LESSON) {
            throw new InvalidParamException("Number of videos must be <= " + Video.MAXIMUM_VIDEOS_PER_LESSON);
        }

        return videoRepository.save(newVideo);
    }
}
