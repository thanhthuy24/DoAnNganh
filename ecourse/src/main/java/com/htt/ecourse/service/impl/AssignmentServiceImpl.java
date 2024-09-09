package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.AssignmentDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Tag;
import com.htt.ecourse.repository.AssignmentRepository;
import com.htt.ecourse.repository.CourseRepository;
import com.htt.ecourse.repository.LessonRepository;
import com.htt.ecourse.repository.TagRepository;
import com.htt.ecourse.service.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentServiceImpl implements AssignmentService {

    private final TagRepository tagRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final AssignmentRepository assignmentRepository;

    @Override
    public Page<Assignment> getAllAssignment(Pageable pageable) {
        return assignmentRepository.findAll(pageable);
    }

    @Override
    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id)
                .orElseThrow(() -> new DateTimeException("Can not find Assignment by id: " + id));
    }

    @Override
    public Assignment createAssignment(AssignmentDTO assignmentDTO) throws DataNotFoundException {
        Tag existTag = tagRepository
                .findById(assignmentDTO.getTagId())
                .orElseThrow(() -> new DataNotFoundException("Can not find tag by id: " + assignmentDTO.getTagId()));

        Course existCourse = courseRepository
                .findById(assignmentDTO.getCourseId())
                .orElseThrow(() -> new DataNotFoundException("Can not find course by id: " + assignmentDTO.getCourseId()));

        Lesson existLesson = lessonRepository
                .findById(assignmentDTO.getLessonId())
                .orElseThrow(() -> new DataNotFoundException("Can not find lesson by id: " + assignmentDTO.getLessonId()));

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
        return assignmentRepository.findByCourseId(courseId);
    }

    @Override
    public Assignment updateAssignment(Long id, Long assignmentId, AssignmentDTO assignmentDTO) throws DataNotFoundException {
        Assignment existingAssignment = getAssignmentById(id);
        if(existingAssignment != null) {
            Tag existTag = tagRepository
                    .findById(assignmentDTO.getTagId())
                    .orElseThrow(() -> new DataNotFoundException("Can not find tag by id: " + assignmentDTO.getTagId()));

            Course existCourse = courseRepository
                    .findById(assignmentDTO.getCourseId())
                    .orElseThrow(() -> new DataNotFoundException("Can not find course by id: " + assignmentDTO.getCourseId()));

            Lesson existLesson = lessonRepository
                    .findById(assignmentDTO.getLessonId())
                    .orElseThrow(() -> new DataNotFoundException("Can not find lesson by id: " + assignmentDTO.getLessonId()));

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
