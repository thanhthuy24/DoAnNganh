package com.htt.ecourse.controller;

import com.htt.ecourse.components.VNPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/vn-pay")
public class ApiVNPayController {

//    @Value("${vnpay.tmn_code}")
//    private String tmnCode;
//
//    @Value("${vnpay.hash_secret}")
//    private String hashSecret;
//
//    @Value("${vnpay.url}")
//    private String vnpUrl;
//
//    @Value("${vnpay.return_url}")
//    private String returnUrl;

    private final VNPayService vnPayService;

    @GetMapping("/create-payment")
    public ResponseEntity<?> createPayment(
            @RequestParam Long orderId,
            @RequestParam float amount,
            @RequestParam String orderInfo
    ) {
        try {
            String paymentUrl = vnPayService.createPaymentURL(orderId, amount, orderInfo);
            return ResponseEntity.ok(paymentUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating payment URL");
        }
    }

    @GetMapping("/return")
    public ResponseEntity<?> paymentReturn(HttpServletRequest request) {
        Map<String, String[]> params = request.getParameterMap();
        // Xử lý logic sau khi thanh toán thành công hoặc thất bại
        // Kiểm tra `vnp_ResponseCode` để biết trạng thái thanh toán
        String responseCode = request.getParameter("vnp_ResponseCode");
        if ("00".equals(responseCode)) {
            return ResponseEntity.ok("Thanh toán thành công!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Thanh toán thất bại!");
        }
    }
}
