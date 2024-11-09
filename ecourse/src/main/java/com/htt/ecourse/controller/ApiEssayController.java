package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.EssayDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Essay;
import com.htt.ecourse.responses.EssayResponse;
import com.htt.ecourse.responses.list.EssayListResponse;
import com.htt.ecourse.service.AssignmentService;
import com.htt.ecourse.service.EssayService;
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
@RequiredArgsConstructor
@RequestMapping("api/essays")
public class ApiEssayController {
    private final EssayService essayService;
    private final AssignmentService assignmentService;

    @GetMapping("/assignment/{assignmentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getEssayByAssignmentId(
            @PathVariable("assignmentId") Long assignmentId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {

        if (page < 0 || limit <= 0) {
            return ResponseEntity.badRequest().body("Page and limit must be positive numbers.");
        }

        if (assignmentService.getAssignmentById(assignmentId) == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Assignment not found.");
        }

        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by("createdDate").descending());
        Page<EssayResponse> essayPage = essayService.getEssaysByAssignment(assignmentId, pageRequest);

        int totalPages = essayPage.getTotalPages();
        List<EssayResponse> essays = essayPage.getContent();
        return ResponseEntity.ok(EssayListResponse.builder()
                        .essays(essays)
                        .totalPages(totalPages)
                .build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createEssay(
            @Valid @RequestBody EssayDTO essayDTO,
            BindingResult rs
    ) throws DataNotFoundException {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        essayService.createEssay(essayDTO);
        return ResponseEntity.ok(essayDTO);
    }

    @PutMapping("/{essayId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateEssay(
            @Valid
            @PathVariable Long essayId,
            @RequestBody EssayDTO essayDTO,
            BindingResult rs
    )throws DataNotFoundException{
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        essayService.updateEssay(essayId, essayDTO);
        return ResponseEntity.ok(essayDTO);
    }

}
