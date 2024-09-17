package com.htt.ecourse.controller;

import com.htt.ecourse.service.ProgressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/progress")
public class ApiProgressController {
    private final ProgressService progressService;

    @PostMapping("/course/{courseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createProgress(
            @PathVariable Long courseId
    ){
        return ResponseEntity.ok(progressService.calculateProgress(courseId));
    }

}
