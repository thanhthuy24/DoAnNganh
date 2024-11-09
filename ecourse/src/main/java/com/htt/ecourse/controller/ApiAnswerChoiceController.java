package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.AnswerChoiceDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Answerchoice;
import com.htt.ecourse.responses.AnswerChoiceResponse;
import com.htt.ecourse.service.AnswerChoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/answer-choices")
public class ApiAnswerChoiceController {
    private final AnswerChoiceService answerChoiceService;

    @PostMapping("/{assignmentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createAnswerChoice(
            @Valid
            @RequestBody List<AnswerChoiceDTO> answerChoiceDTOs,
            @PathVariable Long assignmentId,
            BindingResult rs
    ) throws DataNotFoundException {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        for (AnswerChoiceDTO answerChoiceDTO : answerChoiceDTOs) {
            answerChoiceService.createAnswerChoice(answerChoiceDTO, assignmentId);
        }

        return ResponseEntity.ok(answerChoiceDTOs);
    }

    @GetMapping("/assignment/{assignmentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<AnswerChoiceResponse>> getAnswerChoiceByAssignment(
            @PathVariable Long assignmentId
    ) {
        List<AnswerChoiceResponse> answerchoiceList = answerChoiceService.checkAnswersByAssignmentId(assignmentId);
        return ResponseEntity.ok(answerchoiceList);
    }

}
