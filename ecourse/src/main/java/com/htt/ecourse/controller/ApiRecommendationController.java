package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.CourseDTO;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/ai-recommend")
public class ApiRecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getRecommendation(
            @PathVariable Long userId
    ) {
        List<CourseDTO> recommendCourses = recommendationService.getRecommendedCourses(userId);
        return ResponseEntity.ok(recommendCourses);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getRecommendation2(
            @PathVariable Long userId
    ) {
        List<Course> recommendCourses = recommendationService.getRecommendedCourses2(userId);
        return ResponseEntity.ok(recommendCourses);
    }
}
