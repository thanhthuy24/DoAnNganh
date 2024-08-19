package com.htt.ecourse.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.Date;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptResponse {
    @JsonProperty("order_date")
    private Date orderDate;
    @JsonProperty("total_money")
    @Min(value = 0, message = "Total money must be >= 0")
    private Float totalMoney;
    @JsonProperty("user_id")
    private Long userId;
    @JsonProperty("payment_method")
    private String paymentMethod;
}
