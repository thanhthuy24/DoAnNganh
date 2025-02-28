package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.TokenDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Token;
import com.htt.ecourse.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/token")
@RequiredArgsConstructor
public class ApiTokenController {
    public final TokenService tokenService;
    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createToken(
            @RequestBody TokenDTO tokenDTO,
            BindingResult rs
            ) throws DataNotFoundException {
        if (rs.hasErrors()) {
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        Token token = tokenService.createToken(tokenDTO);
        return ResponseEntity.ok(token);
    }

//    xóa thông tin bằng token
    @PostMapping("/remove-token")
    public ResponseEntity<String> removeFcmToken(@RequestBody Map<String, String> request) {
        String token = request.get("token");

        tokenService.removeFcmToken(token);
        return ResponseEntity.ok("FCM Token removed successfully");
    }

//    xóa thông tin token bằng userID
    @DeleteMapping("/userId")
    public ResponseEntity<?> deleteToken(
            @PathVariable Long userId
    ){
        tokenService.removeTokensByUserId(userId);
        return ResponseEntity.ok("Token removed successfully");
    }

}


