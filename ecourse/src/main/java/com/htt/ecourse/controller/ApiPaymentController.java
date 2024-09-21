package com.htt.ecourse.controller;

import com.htt.ecourse.components.MomoService;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Cart;
import com.htt.ecourse.service.ReceiptService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/payment")
public class ApiPaymentController {
    private final MomoService momoService;
    private final ReceiptService receiptService;

    @PostMapping("/create-payment")
    @ResponseStatus(HttpStatus.CREATED)
    public String createPayment(
            @RequestBody Map<String, String> params
    ) throws Exception {
        String orderId = params.get("orderId");
        String amount = params.get("amount");
        String returnUrl = params.get("returnUrl");

        if (orderId == null || amount == null || returnUrl == null) {
            throw new IllegalArgumentException("Missing required parameters");
        }

        float amountValue;
        try {
            amountValue = Float.parseFloat(amount);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid amount format", e);
        }

        return this.momoService.createPaymentRequest(orderId, amountValue, returnUrl);
    }

    @PostMapping("/update-payment")
    @ResponseStatus(HttpStatus.CREATED)
    public void pay(@RequestBody List<Cart> carts) throws DataNotFoundException {
        this.receiptService.addReceipt(carts);
    }
}
