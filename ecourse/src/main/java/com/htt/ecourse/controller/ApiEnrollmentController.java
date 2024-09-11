package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.EnrollmentDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Enrollment;
import com.htt.ecourse.repository.EnrollmentRepository;
import com.htt.ecourse.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/enrollments")
@RequiredArgsConstructor
public class ApiEnrollmentController {
    private final EnrollmentService enrollmentService;
    private final EnrollmentRepository enrollmentRepository;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createEnrollment(
            @RequestBody EnrollmentDTO enrollmentDTO,
            BindingResult rs
    ){
        if (rs.hasErrors()) {
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        enrollmentService.createEnrollment(enrollmentDTO);
        return ResponseEntity.ok(enrollmentDTO);
    }

    @GetMapping("/user/{userId}/course/{courseId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getEnrollmentByUserAndCourse(
            @PathVariable(value = "userId") Long userId,
            @PathVariable(value = "courseId") Long courseId
    ){
        List<Enrollment> enrollments = enrollmentRepository.findByUserIdAndCourseId(courseId, userId);
        if (enrollments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Enrollment>> getEnrollmentsByUser(
            @PathVariable("userId") Long userId
    ){
        List<Enrollment> enrollments = enrollmentService.getEnrollmentByUser(userId);
        return ResponseEntity.ok(enrollments);
    }
}
