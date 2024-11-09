package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.AssignmentDoneDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Userassignmentdone;
import com.htt.ecourse.service.AssignmentDoneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/assignment-done")
public class ApiAssignmentDoneController {
    private final AssignmentDoneService assignmentDoneService;

    @GetMapping("/assignment/{assignmentId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAssignmentDone(
            @PathVariable Long assignmentId) {
        return ResponseEntity.ok(assignmentDoneService.getAssignmentDone(assignmentId));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Userassignmentdone> createAssignmentDone(
            @RequestBody AssignmentDoneDTO assignmentDoneDTO,
            BindingResult rs
    ) {
        Userassignmentdone newAssignmentDone = assignmentDoneService.createAssignmentDone(assignmentDoneDTO);
        return ResponseEntity.ok(newAssignmentDone);
    }
}
