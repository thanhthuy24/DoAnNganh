package com.htt.ecourse.responses.list;

import com.htt.ecourse.pojo.Receiptdetail;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReceiptDetailListResponse {
    private List<Receiptdetail> receiptDetails;
    private int totalPages;
}
