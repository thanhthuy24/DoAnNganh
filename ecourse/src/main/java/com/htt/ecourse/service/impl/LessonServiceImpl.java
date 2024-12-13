package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.*;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.pojo.*;
import com.htt.ecourse.repository.*;
import com.htt.ecourse.responses.LessonResponse;
import com.htt.ecourse.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {
    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final VideoRepository videoRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public Lesson createLesson(LessonDTO lessonDTO) {
        Course existCourse = courseRepository
                .findById(lessonDTO.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Can not find course by id " + lessonDTO.getCourseId()));

        Lesson newLesson = Lesson.builder()
                .name(lessonDTO.getName())
                .description(lessonDTO.getDescription())
                .course(existCourse)
                .build();
        return lessonRepository.save(newLesson);
    }

    @Override
    public Lesson getLessonById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find lesson by id " + id));
    }

    @Override
    public Page<Lesson> getAllLessons(PageRequest pageRequest) {
        return lessonRepository
                .findAll(pageRequest);
//        return lessonRepository.findAll(pageRequest);
    }

    @Override
    public Lesson updateLesson(Long id, LessonDTO lessonDTO) {
        Lesson existingLesson = getLessonById(id);
        if (existingLesson != null) {
            Course existCourse = courseRepository.findById(lessonDTO.getCourseId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot find course by id " + id));
            existingLesson.setName(lessonDTO.getName());
            existingLesson.setDescription(lessonDTO.getDescription());
            existingLesson.setCourse(existCourse);
            return lessonRepository.save(existingLesson);
        }
        return null;
    }

    @Override
    public Lesson updateActiveLesson(Long id, LessonDTO lessonDTO){
        Lesson existingLesson = getLessonById(id);
        if (existingLesson != null) {
            Course existCouse = courseRepository.findById(lessonDTO.getCourseId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                            "Cannot find course by id " + id));
            existingLesson.setName(lessonDTO.getName());
            existingLesson.setDescription(lessonDTO.getDescription());
            existingLesson.setCourse(existCouse);
            existingLesson.setIsActive(lessonDTO.getIsActive());
            return lessonRepository.save(existingLesson);
        }
        return null;
    }

    @Override
    public void deleteLesson(Long id) {
        Optional<Lesson> optionalLesson = lessonRepository.findById(id);

        Lesson lesson = optionalLesson.get();

        videoRepository.deleteAll(lesson.getVideos());

        lessonRepository.delete(lesson);
    }

    @Override
    public boolean existByName(String name) {
        return lessonRepository.existsByName(name);
    }

    @Override
    public List<LessonVideoDTO> getLessonByCourseId(Long courseId) throws DataNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long role = userRepository.getUserByUsername(username).getRole().getId();
        Long userId = userRepository.getUserByUsername(username).getId();

        if(role != 3) {
            Optional<Enrollment> checkEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
            if (checkEnrollment.isEmpty()) {
                throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "You must enroll this course first!!"
                );
            }
        }
        Course course = courseRepository.getCourseById(courseId);
        Long ownerCourseId = course.getTeacher().getId();
        Teacher teacher = teacherRepository.findByUserId(userId);

        if(role == 3) {
            Long teacherId = teacher.getId();
            if (ownerCourseId != teacherId) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "You don't have permission to access this course!!"
                );
            }
        }

        List<Lesson> lessons = lessonRepository.findByCourseId(courseId);
        return lessons.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    private LessonVideoDTO convertToDTO(Lesson lesson) {
        // Xây dựng danh sách VideoDTO bằng builder
        List<Video> videos = lesson.getVideos().stream()
                .map(video -> Video.builder()
                        .id(video.getId())
                        .name(video.getName())
                        .build())
                .collect(Collectors.toList());

        // Sử dụng builder để tạo LessonDTO
        return LessonVideoDTO.builder()
                .id(lesson.getId())
                .name(lesson.getName())
                .description(lesson.getDescription())
                .videos(videos)
//                .course(course)
                .build();
    }

    @Override
    public Video createVideo(
            Long lessonId,
            VideoDTO videoDTO) throws InvalidParamException {
        Lesson existingLesson = lessonRepository
                .findById(lessonId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find Lesson with id " + videoDTO.getLessonId()));

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

    @Override
    public Long countLessonInCourse(Long courseId) {
        Long number = lessonRepository.countLessonsByCourseId(courseId);
        if (number != null) {
            return number;
        }
        return 0L;
    }
}
