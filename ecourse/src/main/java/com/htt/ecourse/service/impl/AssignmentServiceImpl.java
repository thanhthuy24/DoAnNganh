package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.AssignmentDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.*;
import com.htt.ecourse.repository.*;
import com.htt.ecourse.responses.AssignmentResponse;
import com.htt.ecourse.responses.LessonResponse;
import com.htt.ecourse.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
public class AssignmentServiceImpl implements AssignmentService {

    private final TagRepository tagRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public Page<AssignmentResponse> getAllAssignment(PageRequest pageRequest) {
        return assignmentRepository
                .findAll(pageRequest)
                .map(AssignmentResponse::fromAssignment);
    }

    @Override
    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find Assignment with id " + id));
    }

    @Override
    public Assignment createAssignment(AssignmentDTO assignmentDTO) throws DataNotFoundException {
        Tag existTag = tagRepository
                .findById(assignmentDTO.getTagId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find Assignment with id " + assignmentDTO.getTagId()));

        Course existCourse = courseRepository
                .findById(assignmentDTO.getCourseId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find course by id: " + assignmentDTO.getCourseId()));

        Lesson existLesson = lessonRepository
                .findById(assignmentDTO.getLessonId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Can not find lesson by id: " + assignmentDTO.getLessonId()));

        Assignment newAssignment = Assignment.builder()
                .name(assignmentDTO.getName())
                .tag(existTag)
                .course(existCourse)
                .lesson(existLesson)
                .dueDate(assignmentDTO.getDueDate())
                .build();
        assignmentRepository.save(newAssignment);

        return newAssignment;
    }

    @Override
    public List<Assignment> getAssignmentByLessonId(Long lessonId) {
        return assignmentRepository.findByLessonId(lessonId);
    }

    @Override
    public List<Assignment> getAssignmentByCourseId(Long courseId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.getUserByUsername(username).getId();

        Optional<Enrollment> checkEnrollment = enrollmentRepository.findByUserIdAndCourseId(userId, courseId);
        if (checkEnrollment.isEmpty()) {
            new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "This course isn't enrolled in your list! Please enroll before participating in this course!!");
        }
        return assignmentRepository.findByCourseId(courseId);
    }

    @Override
    public Assignment updateAssignment(Long id, AssignmentDTO assignmentDTO) throws DataNotFoundException {
        Assignment existingAssignment = getAssignmentById(id);
        if(existingAssignment != null) {
            Tag existTag = tagRepository
                    .findById(assignmentDTO.getTagId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Can not find tag by id: " + assignmentDTO.getTagId()));

            Course existCourse = courseRepository
                    .findById(assignmentDTO.getCourseId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Can not find course by id: " + assignmentDTO.getCourseId()));

            Lesson existLesson = lessonRepository
                    .findById(assignmentDTO.getLessonId())
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Can not find lesson by id: " + assignmentDTO.getLessonId()));

            existingAssignment.setName(assignmentDTO.getName());
            existingAssignment.setCourse(existCourse);
            existingAssignment.setLesson(existLesson);
            existingAssignment.setTag(existTag);
            existingAssignment.setDueDate(assignmentDTO.getDueDate());
            return assignmentRepository.save(existingAssignment);

        }

        return null;
    }

    @Override
    public void deleteAssignment(Long id) {

    }
}
