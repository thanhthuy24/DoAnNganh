package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.CategoryDTO;
import com.htt.ecourse.dtos.CourseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
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
//@RequiredArgsConstructor
public class ApiCourseCController {
    //hien thi tat ca category
    @GetMapping("")
    public ResponseEntity<String> getAllCourses(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok("get courses");
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
            ) throws IOException {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        List<MultipartFile> files = courseDTO.getFiles();
        files = files==null?new ArrayList<>():files;
        for(MultipartFile file : files) {
            if(file.getSize() == 0){
                continue;
            }

            //kiem tra kích thuớc và định dạng file ảnh
            if(file.getSize() > 10 * 1024 * 1024)
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body("File is too large, Maximum is 10MB");

            String contentType = file.getContentType();
            if( contentType == null || !contentType.startsWith("image/") ) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an image");
            }

            String filename = storeFile(file);
        }

        return ResponseEntity.ok("create course + " + courseDTO);
    }

    private String storeFile(MultipartFile file) throws IOException {
        String filename = StringUtils.cleanPath(file.getOriginalFilename());

        //them uuid vao truoc ten file de dam bao ten file la duy nhat
        String uniqueFilename = UUID.randomUUID().toString() + "_" + filename;

        //duong dan den thu muc ma ban muon luu file
        java.nio.file.Path uploadDir = Paths.get("uploads");

        //kiem tra va tao thu muc neu no khong ton tai
        if(!Files.exists(uploadDir)){
            Files.createDirectories(uploadDir);
        }
        //duong dan day du den file
        java.nio.file.Path destination = Paths.get(uploadDir.toString(), uniqueFilename);

        //sao chep file vao thu muc dich
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFilename;
    }

    @PutMapping("/{cateId}")
    public ResponseEntity<String> updateCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok("update course");
    }

    @DeleteMapping("/{cateId}")
    public ResponseEntity<String> deleteCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok("delete course");
    }

}
