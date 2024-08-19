package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.ReceiptDTO;
import com.htt.ecourse.pojo.Receipt;
import com.htt.ecourse.service.ReceipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/receipts")
@RequiredArgsConstructor
public class ApiReceiptController {
    private final ReceipService receipService;

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
            receipService.createReceipt(receiptDTO);
            return ResponseEntity.ok(receiptDTO);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getReceipts(
            @PathVariable("userId") Long userId
    ) {
        try{
            List<Receipt> receipts = receipService.findByUserId(userId);
            return ResponseEntity.ok(receipts);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReceipt(
            @PathVariable("id") Long id
    ) {
        try{
            Receipt existingReceipt = receipService.getReceipt(id);
            return ResponseEntity.ok(existingReceipt);
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
