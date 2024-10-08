package com.htt.ecourse.service.impl;

import com.htt.ecourse.controller.ApiCommentController;
import com.htt.ecourse.dtos.CommentDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.*;
import com.htt.ecourse.repository.CommentRepository;
import com.htt.ecourse.repository.EnrollmentRepository;
import com.htt.ecourse.repository.LessonRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.responses.CommentResponse;
import com.htt.ecourse.responses.list.CommentListResponse;
import com.htt.ecourse.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CommentRepository commentRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    @Override
    public Comment createComment(CommentDTO commentDTO) throws DataNotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Long userId = user.getId();

        Lesson existingLesson = lessonRepository.findById(commentDTO.getLessonId())
                .orElseThrow(() -> new DataNotFoundException("Lesson not found"));

        Optional<Enrollment> existingEnrollment = enrollmentRepository
                .findByUserIdAndCourseId(userId, existingLesson.getCourse().getId());

        if (existingEnrollment == null || !existingEnrollment.isPresent()) {
            throw new DataNotFoundException("Enrollment not found");
        }

        Comment existingParentComment = commentRepository.getCommentById(commentDTO.getParentId());

        Comment newComment = Comment.builder()
                .content(commentDTO.getContent())
                .createdDate(new Date())
                .user(user)
                .lesson(existingLesson)
                .build();

        commentRepository.save(newComment);

        return newComment;
    }

    @Override
    public Page<CommentResponse> getCommentsByLessonId(Long lessonId, PageRequest pageRequest) throws DataNotFoundException {
        Lesson existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new DataNotFoundException("Lesson not found"));

        Page<Comment> list = commentRepository.findByLessonId(lessonId, pageRequest);

        return list.map(CommentResponse::fromComment);
    }

    @Override
    public Page<Comment> getComments(Long lessonId, PageRequest pageRequest) throws DataNotFoundException {
        Lesson existingLesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new DataNotFoundException("Lesson not found"));

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        return commentRepository.getCommentByLessonId(lessonId, pageRequest)
                .map(Comment::fromComment);
    }
}
