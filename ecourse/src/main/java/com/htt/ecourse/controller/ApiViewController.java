package com.htt.ecourse.controller;

import com.htt.ecourse.pojo.View;
import com.htt.ecourse.service.ViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/views")
public class ApiViewController {
    private final ViewService viewService;

    @PostMapping("/course/{courseId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createView(
            @PathVariable Long courseId
    ){
        return ResponseEntity.ok(viewService.createView(courseId));
    }

}
