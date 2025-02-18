package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByLessonId(Long lessonId, PageRequest pageRequest);
    Comment getCommentById(Long id);
    Page<Comment> getCommentByLessonId(Long lessonId, PageRequest pageRequest);
    Long countCommentByLessonId(Long lessonId);
}
