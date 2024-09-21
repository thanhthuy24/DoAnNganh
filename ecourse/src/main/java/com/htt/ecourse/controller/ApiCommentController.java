package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.CommentDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.repository.LessonRepository;
import com.htt.ecourse.responses.AssignmentResponse;
import com.htt.ecourse.responses.CommentResponse;
import com.htt.ecourse.responses.list.AssignmentListResponse;
import com.htt.ecourse.responses.list.CommentListResponse;
import com.htt.ecourse.service.CommentService;
import com.htt.ecourse.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/comments")
public class ApiCommentController {
    private final CommentService commentService;
    private final LessonService lessonService;
    private final LessonRepository lessonRepository;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createComment(
            @Valid
            @RequestBody CommentDTO commentDTO,
            BindingResult rs
            ) throws DataNotFoundException {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        commentService.createComment(commentDTO);
        return ResponseEntity.ok(commentDTO);
    }

    @GetMapping("/lesson/{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getLessonComments(
            @PathVariable("lessonId") Long lessonId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) throws DataNotFoundException {

        if (page < 0 || limit <= 0) {
            return ResponseEntity.badRequest().body("Page and limit must be positive numbers.");
        }

        if (lessonRepository.findById(lessonId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Lesson not found.");
        }

        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by("createdDate").descending());
        Page<CommentResponse> commentResponsePage = commentService.getCommentsByLessonId(lessonId, pageRequest);

        // lay tong so trang
        int totalPage = commentResponsePage.getTotalPages();
        List<CommentResponse> comments = commentResponsePage.getContent();
        return ResponseEntity.ok(CommentListResponse.builder()
                .comments(comments)
                .totalPages(totalPage)
                .build());
    }

}
