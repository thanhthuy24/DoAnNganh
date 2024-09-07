package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.UserDTO;
import com.htt.ecourse.dtos.UserLoginDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ApiUserController {
    private final UserService userService;
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
            User user = userService.register(userDTO);
            return ResponseEntity.ok(user);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        //kiểm tra thông tin đăng nhập và token
        try {
            String token = userService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        // trả về token trong response

    }
}
