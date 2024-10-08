package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.RegisterDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Register;
import com.htt.ecourse.service.RegisterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/register")
public class ApiRegisterController {
    private final RegisterService registerService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllRegister() {
        List<Register> list = registerService.getAllRegisters();
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{registerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Register> getRegisterById(
            @PathVariable Long registerId
    ) throws DataNotFoundException {
        Register registerById = registerService.getRegisterById(registerId);
        return ResponseEntity.ok(registerById);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createRegister(
            @Valid
            @RequestBody RegisterDTO registerDTO,
            BindingResult rs
    ){
        if (rs.hasErrors()) {
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        registerService.createRegister(registerDTO);
        return ResponseEntity.ok(registerDTO);
    }

    @PatchMapping("/update/{registerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateRegister(
            @PathVariable Long registerId,
            @Valid @ModelAttribute RegisterDTO registerDTO,
            BindingResult rs
    ) throws DataNotFoundException {
        if (rs.hasErrors()) {
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }

        registerService.updateRegister(registerDTO, registerId);
        return ResponseEntity.ok(registerDTO);
    }

}