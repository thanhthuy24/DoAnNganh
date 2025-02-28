package com.htt.ecourse.responses.list;

import com.htt.ecourse.pojo.Receipt;
import com.htt.ecourse.responses.ReceiptResponse;
import lombok.*;

import java.util.List;
@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptListResponse {
    private List<Receipt> receipts;
    private int totalPages;
}
