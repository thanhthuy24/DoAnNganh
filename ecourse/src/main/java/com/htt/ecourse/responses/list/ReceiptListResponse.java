package com.htt.ecourse.responses.list;

import com.htt.ecourse.responses.ReceiptResponse;
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
