package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.CommentDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Comment;
import com.htt.ecourse.pojo.Courserating;
import com.htt.ecourse.repository.LessonRepository;
import com.htt.ecourse.responses.AssignmentResponse;
import com.htt.ecourse.responses.CommentResponse;
import com.htt.ecourse.responses.list.AssignmentListResponse;
import com.htt.ecourse.responses.list.CommentListRes;
import com.htt.ecourse.responses.list.CommentListResponse;
import com.htt.ecourse.responses.list.RatingListResponse;
import com.htt.ecourse.service.CommentService;
import com.htt.ecourse.service.LessonService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.json.JSONException;
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
            ) throws DataNotFoundException, JSONException {
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

//    @PostMapping("/{commentId}")
//    @ResponseStatus(HttpStatus.CREATED)
//    public ResponseEntity<?> createCommentChild(
//            @Valid
//            @RequestBody CommentDTO commentDTO,
//            @PathVariable Long commentId,
//            BindingResult rs
//    ) throws DataNotFoundException {
//        if(rs.hasErrors()){
//            List<String> errorMessages = rs.getFieldErrors()
//                    .stream()
//                    .map(FieldError::getDefaultMessage)
//                    .toList();
//            return ResponseEntity.badRequest().body(errorMessages);
//        }
//        commentService.createCommentChild(commentDTO, commentId);
//        return ResponseEntity.ok(commentDTO);
//    }

    @GetMapping("/{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CommentListRes> getComments(
            @PathVariable("lessonId") Long lessonId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) throws DataNotFoundException {
        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by("createdDate").descending());
        Page<Comment> list = commentService.getComments(lessonId, pageRequest);

        int totalPages = list.getTotalPages();

        List<Comment> cmts = list.getContent();
        return ResponseEntity.ok(CommentListRes.builder()
                .comments(cmts)
                .totalPages(totalPages)
                .build());
    }

    @GetMapping("/count/lesson/{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> countCommentsByLessonId(
            @PathVariable("lessonId") Long lessonId
    ){
        return ResponseEntity.ok(commentService.countCommentByLessonId(lessonId));
    }
}
