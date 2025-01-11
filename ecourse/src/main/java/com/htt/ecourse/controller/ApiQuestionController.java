package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.QuestionChoiceDTO;
import com.htt.ecourse.dtos.QuestionDTO;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Question;
import com.htt.ecourse.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/questions")
@RequiredArgsConstructor
public class ApiQuestionController {
    private final QuestionService questionService;

    @GetMapping("/assignment/{assignmentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<QuestionChoiceDTO>> getQuestionByAssignmentId(
            @PathVariable(value = "assignmentId") Long assignmentId
    ){
        List<QuestionChoiceDTO> questionChoiceDTOList = questionService.getQuestionsByAssignmentId(assignmentId);
        return ResponseEntity.ok(questionChoiceDTOList);
    }

    @GetMapping("/count/assignment/{assignmentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> countQuestionByAssignmentId(
            @PathVariable(value = "assignmentId") Long assignmentId
    ){
        return ResponseEntity.ok(questionService.countQuestionByAssignmentId(assignmentId));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createQuestion(
            @RequestBody QuestionDTO questionDTO,
            BindingResult rs
    ){
        try {
            if (rs.hasErrors()) {
                List<String> errorMessages = rs.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            Question question = questionService.createQuestion(questionDTO);

            return ResponseEntity.ok(question);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{questionId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateQuestion(
            @PathVariable(value = "questionId") Long questionId,
            @Valid
            @RequestBody QuestionDTO questionDTO
    ){
        questionService.updateQuestion(questionId, questionDTO);

        return ResponseEntity.ok(questionDTO);
    }
}
