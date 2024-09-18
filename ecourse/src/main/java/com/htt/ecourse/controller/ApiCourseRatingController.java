package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.CourseRatingDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.service.CourseRatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/rating")
@RequiredArgsConstructor
public class ApiCourseRatingController {
    private final CourseRatingService courseRatingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createRating(
            @Valid
            @RequestBody CourseRatingDTO courseRatingDTO,
            BindingResult rs
            ) throws DataNotFoundException {
        if (rs.hasErrors()) {
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        courseRatingService.createRating(courseRatingDTO);
        return ResponseEntity.ok(courseRatingDTO);
    }



}
