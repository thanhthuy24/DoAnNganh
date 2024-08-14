package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.UserDTO;
import com.htt.ecourse.dtos.UserLoginDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/users")
public class ApiUserController {
    @PostMapping("/register")
    public ResponseEntity<?> createUser(
            @Valid @RequestBody UserDTO userDTO,
            BindingResult rs
    ) {
        try{
            if(rs.hasErrors()){
                List<String> errorMessages = rs.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Passwords do not match");
            }
            return ResponseEntity.ok("Register successful");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        //kiểm tra thông tin đăng nhập và token

        // trả về token trong response
        return ResponseEntity.ok("Login successful");
    }
}
