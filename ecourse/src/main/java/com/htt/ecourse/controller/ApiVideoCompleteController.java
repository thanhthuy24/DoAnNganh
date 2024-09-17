package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.VideoCompleteDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Videocompleted;
import com.htt.ecourse.service.VideoCompleteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/video-completed")
public class ApiVideoCompleteController {
    private final VideoCompleteService videoCompleteService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createVideoComplete(
            @RequestBody VideoCompleteDTO videoCompleteDTO,
            BindingResult rs
            ) throws DataNotFoundException {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        videoCompleteService.createVideocompleted(videoCompleteDTO);
        return ResponseEntity.ok(videoCompleteDTO);
    }

}
