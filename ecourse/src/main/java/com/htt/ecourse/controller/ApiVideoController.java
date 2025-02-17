package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.VideoDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/videos")
@RequiredArgsConstructor
public class ApiVideoController {
    public final VideoService videoService;

    @GetMapping("/count/lesson/{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> getLessonCount(
            @PathVariable Long lessonId
    ) {
        return ResponseEntity.ok(videoService.countVideoByLessonId(lessonId));
    }

    @GetMapping("/lesson/{lessonId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getVideoByLessonId(
            @PathVariable Long lessonId
    ) throws DataNotFoundException {
        return ResponseEntity.ok(videoService.findByLessonId(lessonId));
    }

    @GetMapping("/count/course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> countVideoByCourseId(
            @PathVariable Long courseId
    ){
        return ResponseEntity.ok(videoService.countVideoByCourseId(courseId));
    }
}
