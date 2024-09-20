package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.CommentDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Comment;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.CommentRepository;
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
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;

    @Override
    public Comment createComment(CommentDTO commentDTO) throws DataNotFoundException {
        Lesson existingLesson = lessonRepository.findById(commentDTO.getLessonId())
                .orElseThrow(() -> new DataNotFoundException("Lesson not found"));

        Comment existingParentComment = commentRepository.findById(commentDTO.getParentId());

        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.getUserByUsername(username);

        Comment newComment = Comment.builder()
                .content(commentDTO.getContent())
                .createdDate(new Date())
                .parent(existingParentComment)
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

        return commentRepository.findByLessonId(existingLesson.getId(), pageRequest)
                .map(CommentResponse::fromComment);
    }
}
