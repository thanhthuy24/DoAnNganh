package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.ScoreDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Score;
import com.htt.ecourse.service.ScoreService;
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
@RequestMapping("api/score")
public class ApiScoreController {
    private final ScoreService scoreService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createScore(
            @Valid
            @RequestBody ScoreDTO scoreDTO,
            BindingResult rs
            ) throws DataNotFoundException {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        scoreService.createScore(scoreDTO);
        return ResponseEntity.ok(scoreDTO);
    }

}