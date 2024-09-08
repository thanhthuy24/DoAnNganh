package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.TeacherDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Teacher;
import com.htt.ecourse.responses.TeacherListResponse;
import com.htt.ecourse.service.TeacherService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("api/teachers")
@RequiredArgsConstructor
public class ApiTeacherController {
    private final TeacherService teacherService;
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TeacherListResponse> getAllTeachers(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        // tao pageable tu thong tin page va limit
        Pageable pageRequest = PageRequest.of(page, limit);
        Page<Teacher> teacherPage = teacherService.getAllTeachers(pageRequest);

        // lay tong so trang
        int totalPage = teacherPage.getTotalPages();
        List<Teacher> teachers = teacherPage.getContent();
        return ResponseEntity.ok(TeacherListResponse.builder()
                .teachers(teachers)
                .totalPages(totalPage)
                .build());
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTeacher(
            @Valid
            @RequestBody TeacherDTO teacherDTO,
            BindingResult rs) throws DataNotFoundException {
        if (rs.hasErrors()) {
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        teacherService.createTeacher(teacherDTO);
        return ResponseEntity.ok(teacherDTO);
    }

    @GetMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getTeacherById(
            @PathVariable("teacherId") Long teacherId
    ){
        Teacher teacher = teacherService.getTeacherById(teacherId);
        return ResponseEntity.ok(teacher);
    }

    @PutMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateTeacher(
            @PathVariable("teacherId") Long teacherId,
            @Valid @RequestBody TeacherDTO teacherDTO
    ){
        teacherService.updateTeacher(teacherId, teacherDTO);
        return  ResponseEntity.ok(teacherDTO);
    }

    @DeleteMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteTeacher(
            @PathVariable("teacherId") Long teacherId

    ){
        teacherService.deleteTeacher(teacherId);
        return  ResponseEntity.ok("delete teacher successfully!!");
    }

}

