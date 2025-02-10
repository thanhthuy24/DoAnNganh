package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.ReceiptDetailDTO;
import com.htt.ecourse.pojo.Receipt;
import com.htt.ecourse.pojo.Receiptdetail;
import com.htt.ecourse.responses.list.ReceiptDetailListResponse;
import com.htt.ecourse.responses.list.ReceiptListResponse;
import com.htt.ecourse.service.ReceiptDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/receipt_details")
@RequiredArgsConstructor
@CrossOrigin
public class ApiReceiptDetailController {
    private final ReceiptDetailService receiptDetailService;
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
public ResponseEntity<ReceiptDetailListResponse> getAllReceiptDetails(
        @RequestParam("page") int page,
        @RequestParam("limit") int limit,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String order,
        @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword
    ) {
        Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;

        PageRequest pageRequest = PageRequest.of(page, limit,
                Sort.by(direction, sortBy));
        Page<Receiptdetail> receiptPage = receiptDetailService.getAllReceiptDetails(pageRequest, keyword);

        int totalPage = receiptPage.getTotalPages();
        List<Receiptdetail> receipts = receiptPage.getContent();
        return ResponseEntity.ok(ReceiptDetailListResponse.builder()
                .receiptDetails(receipts)
                .totalPages(totalPage)
                .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReceipt(
            @PathVariable("id") Long id
    ) {
        try{
            Receiptdetail existingReceiptdetail = receiptDetailService.getReceiptDetail(id);
            return ResponseEntity.ok().body(existingReceiptdetail);
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
