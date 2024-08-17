package com.htt.ecourse.controller;

import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/lessons")
public class ApiLessonController {
    @GetMapping("")
    public ResponseEntity<String> getLessons(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok("get lessons");
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<String> getLessonById(
            @PathVariable("lessonId") int lessonId
    ) {
        return ResponseEntity.ok("get lessons with id: " + lessonId);
    }

//    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    public ResponseEntity<String> createLesson(
//            @Valid @ModelAttribute
//    )
}
