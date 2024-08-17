package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.LessonDTO;
import com.htt.ecourse.dtos.VideoDTO;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Video;
import com.htt.ecourse.service.LessonService;
import com.htt.ecourse.service.impl.CloudinaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/lessons")
@RequiredArgsConstructor
public class ApiLessonController {
    private final LessonService lessonService;
    private final CloudinaryService cloudinaryService;
    @GetMapping("")
    public ResponseEntity<String> getLessons(
            @RequestParam("page") int page,
            @RequestParam("limit") int limit
    ) {
        return ResponseEntity.ok("get lessons");
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<String> getLessonById(
            @PathVariable("lessonId") int lessonId
    ) {
        return ResponseEntity.ok("get lessons with id: " + lessonId);
    }

    @PostMapping(value = "")
    public ResponseEntity<?> createLesson(
            @Valid @ModelAttribute LessonDTO lessonDTO,
            BindingResult rs
    ) {
        try {
            if (rs.hasErrors()) {
                List<String> errorMessages = rs.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }

            Lesson newLesson = lessonService.createLesson(lessonDTO);

            return ResponseEntity.ok(newLesson);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadVideos(
            @PathVariable("id") Long productId,
            @ModelAttribute("files") List<MultipartFile> files
    ) {try {
        Lesson existingLesson = lessonService.getLesson(productId);
        files = files == null ? new ArrayList<>() : files;
        List<Video> videos = new ArrayList<>();
        for(MultipartFile file : files) {
            if(!file.isEmpty()) {
                continue;
            }
            if(file.getSize() > 10 * 1024 * 1024) {
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body("File is too large! Maximum size is 10MB");
            }

            // tai file len cloudinary va lay URl
            String videoUrl = storeFile(file);

            //luu vao doi tuong
            Video video = lessonService.createVideo(
                    existingLesson.getId(),
                    VideoDTO.builder()
                            .name(videoUrl)
                            .build()
            );
            videos.add(video);
        }
        return ResponseEntity.ok().body(videos);
    } catch(Exception e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
    }

    private String storeFile(MultipartFile file) throws IOException {
        // tai file len cloudinary va lay URl
        Map<String, Object> uploadResult = cloudinaryService.uploadFile(file);
        return uploadResult.get("url").toString();
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<String> updateLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok("update lesson");
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok("delete lesson");
    }
}
