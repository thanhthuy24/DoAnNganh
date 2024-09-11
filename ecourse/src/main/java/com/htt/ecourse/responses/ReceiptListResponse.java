package com.htt.ecourse.responses;

import com.htt.ecourse.pojo.Assignment;
import com.htt.ecourse.pojo.Receipt;
import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReceiptListResponse {
    private List<ReceiptResponse> receipts;
    private int totalPages;
}
