package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.ReceiptDetailDTO;
import com.htt.ecourse.pojo.Receipt;
import com.htt.ecourse.pojo.Receiptdetail;
import com.htt.ecourse.service.ReceiptDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/receipt_details")
@RequiredArgsConstructor
public class ApiReceiptDetailController {
    private final ReceiptDetailService receiptDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getReceipt(
            @PathVariable("id") Long id
    ) {
        try{
            Receiptdetail existingReceiptdetail = receiptDetailService.getReceiptDetail(id);
            return ResponseEntity.ok(existingReceiptdetail);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createReceiptDetail(
            @Valid @RequestBody ReceiptDetailDTO receiptDetailDTO
    ) {
        try{
            Receiptdetail newReceiptDetail = receiptDetailService.createReceiptDetail(receiptDetailDTO);
            return ResponseEntity.ok().body(newReceiptDetail);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/receipt/{id}")
    public ResponseEntity<?> getReceiptDetails(
            @PathVariable("id") Long id
    ) {
        try{
            List<Receiptdetail> receiptDeatils = receiptDetailService.findByReceiptId(id);
            return ResponseEntity.ok(receiptDeatils);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
