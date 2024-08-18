package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.TagDTO;
import com.htt.ecourse.pojo.Tag;
import com.htt.ecourse.service.TagService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/tags")
@RequiredArgsConstructor
public class ApiTagController {
    private final TagService tagService;

    @GetMapping("")
    public ResponseEntity<List<Tag>> getAllTags() {
        List<Tag> getAllTags = tagService.getAllTags();
        return ResponseEntity.ok(getAllTags);
    }

    @PostMapping("")
    public ResponseEntity<?> createTag(
            @Valid
            @RequestBody TagDTO tagDTO,
           BindingResult rs) {
        if (rs.hasErrors()) {
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        tagService.createTag(tagDTO);
        return ResponseEntity.ok("insert category successfully!");
    }
}
