package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.AssignmentDTO;
import com.htt.ecourse.dtos.CategoryDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.responses.AssignmentListResponse;
import com.htt.ecourse.responses.AssignmentResponse;
import com.htt.ecourse.responses.CourseListResponse;
import com.htt.ecourse.service.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/assignments")
@RequiredArgsConstructor
public class ApiAssignmentController {
    private final AssignmentService assignmentService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<AssignmentListResponse> getAllAssignments(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by("createdDate").descending());
        Page<AssignmentResponse> assignmentPage = assignmentService.getAllAssignment(pageRequest);

        // lay tong so trang
        int totalPage = assignmentPage.getTotalPages();
        List<AssignmentResponse> assignments = assignmentPage.getContent();
        return ResponseEntity.ok(AssignmentListResponse.builder()
                .assignments(assignments)
                .totalPages(totalPage)
                .build());
    }

    @GetMapping("/{assignmentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Assignment> getAssignmentById(
            @PathVariable("assignmentId") Long assignmentId
    ) {
        Assignment assignmentById = assignmentService.getAssignmentById(assignmentId);
        return ResponseEntity.ok(assignmentById);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createAssignment(
            @Valid
            @ModelAttribute AssignmentDTO assignmentDTO,
            BindingResult rs) throws DataNotFoundException {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        assignmentService.createAssignment(assignmentDTO);
        return ResponseEntity.ok(assignmentDTO);
    }

}
