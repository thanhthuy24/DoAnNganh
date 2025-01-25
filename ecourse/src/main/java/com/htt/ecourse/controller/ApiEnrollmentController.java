package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.EnrollmentDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Enrollment;
import com.htt.ecourse.repository.EnrollmentRepository;
import com.htt.ecourse.service.EnrollmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("check-enrollment")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getEnrollmentByUserAndCourse(
            @RequestParam Long userId,
            @RequestParam Long courseId
    ){
        Optional<Enrollment> enrollment = enrollmentService.findByUserIdAndCourseId(userId, courseId);
        return enrollment.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @GetMapping("check-enrollment-boolean")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> checkEnrollment(
            @RequestParam Long userId,
            @RequestParam Long courseId
    ){
        return ResponseEntity.ok(enrollmentService.checkEnrolled(userId, courseId));
    }

    @GetMapping("/get-courses")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<Enrollment>> getEnrollments(
    ){
        List<Enrollment> enrollments = enrollmentService.getEnrollmentByUser();
        return ResponseEntity.ok(enrollments);
    }

    @GetMapping("/course/{courseId}/count")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Long> getEnrollmentCountByCourse(
            @PathVariable Long courseId
    ){
        return ResponseEntity.ok(enrollmentService.getCountEnrollmentByCourseId(courseId));
    }
}
