package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.CourseDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Course;
import com.htt.ecourse.service.CourseService;
import com.htt.ecourse.service.impl.CloudinaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("api/courses")
@RequiredArgsConstructor
public class ApiCourseController {
    private final CloudinaryService cloudinaryService;
    private final CourseService courseService;
    //hien thi tat ca category
    @GetMapping("")
    public ResponseEntity<List<Course>> getAllCourses(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        // tao pageable tu thong tin page va limit
        Pageable pageRequest = PageRequest.of(page, limit,
                Sort.by("createdDate").descending());
        Page<Course> coursePage = courseService.getAllCourses(pageRequest);

        // lay tong so trang
        int totalPage = coursePage.getTotalPages();
        List<Course> courses = coursePage.getContent();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<String> getCourseById(
            @PathVariable("courseId") String courseId
    ) {
        return ResponseEntity.ok("get courses with id: " + courseId);
    }

    //them category
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createCourse(
            @Valid @ModelAttribute CourseDTO courseDTO,
            BindingResult rs
    ) throws IOException, DataNotFoundException {
        if (rs.hasErrors()) {
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        MultipartFile file = courseDTO.getFile();
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is missing or empty");
        }

        //kiem tra kích thuớc và định dạng file ảnh
        if (file.getSize() > 10 * 1024 * 1024)
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("File is too large, Maximum is 10MB");

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("File must be an image");
        }

        String imageUrl = storeFile(file);

        // Thiết lập URL của hình ảnh cho CourseDTO
        courseDTO.setImage(imageUrl);
        courseService.createCourse(courseDTO);
        return ResponseEntity.ok("create course + " + courseDTO);
    }

    private String storeFile(MultipartFile file) throws IOException {
        Map<String, Object> uploadResult = cloudinaryService.uploadFile(file);
        return uploadResult.get("url").toString();
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<String> updateCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok("update course");
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok("delete course");
    }

}
