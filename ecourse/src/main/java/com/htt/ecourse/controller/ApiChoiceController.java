package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.ChoiceDTO;
import com.htt.ecourse.pojo.Choice;
import com.htt.ecourse.service.ChoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/choices")
@RequiredArgsConstructor
public class ApiChoiceController {
    private final ChoiceService choiceService;

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createChoice(
            @Valid
            @RequestBody ChoiceDTO choiceDTO,
            BindingResult rs
    ){
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        choiceService.createChoice(choiceDTO);
        return ResponseEntity.ok(choiceDTO);
    }

    @PutMapping("/{choiceId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateChoice(
            @Valid
            @PathVariable(value = "choiceId") Long choiceId,
            @RequestBody ChoiceDTO choiceDTO
    ){
        choiceService.updateChoice(choiceId, choiceDTO);
        return ResponseEntity.ok(choiceDTO);
    }
}
