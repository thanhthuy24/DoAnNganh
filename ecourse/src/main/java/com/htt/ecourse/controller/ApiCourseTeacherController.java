package com.htt.ecourse.controller;

import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.responses.list.CourseListResponse;
import com.htt.ecourse.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/course-teacher")
@RequiredArgsConstructor
public class ApiCourseTeacherController {
    private final CourseService courseService;
    @GetMapping("/{teacherId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCoursesByTeacher(
            @PathVariable("teacherId") Long teacherId,
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ){
        Pageable pageRequest = PageRequest.of(page, limit,
                Sort.by("createdDate").descending());
        Page<Course> coursePage = courseService.getCoursesByTeacherId(teacherId, pageRequest);
        int totalPage = coursePage.getTotalPages();
        List<Course> courses = coursePage.getContent();
        return ResponseEntity.ok(CourseListResponse.builder()
                .courses(courses)
                .totalPages(totalPage)
                .build());
    }
}
