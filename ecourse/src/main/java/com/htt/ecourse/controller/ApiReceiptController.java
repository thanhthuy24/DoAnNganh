package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.ReceiptDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/receipts")
public class ApiReceiptController {
    @PostMapping("")
    public ResponseEntity<?> createReceipt(
            @Valid @RequestBody ReceiptDTO receiptDTO,
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

            return ResponseEntity.ok("create receipt successful");
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getReceiptByUserId(
            @PathVariable("userId") Long userId
    ) {
        try{
            return ResponseEntity.ok("Lấy danh sách receipt bằng user id = " + userId);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateReceipt(
            @Valid @PathVariable Long id,
            @Valid @RequestBody ReceiptDTO receiptDTO
    ) {
        try{
            return ResponseEntity.ok("update receipt của id = " + id);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReceipt(
            @Valid @PathVariable Long id
    ) {
        try{
            return ResponseEntity.ok("xóa receipt của id = " + id);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
