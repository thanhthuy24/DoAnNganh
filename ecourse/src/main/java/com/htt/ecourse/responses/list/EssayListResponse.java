package com.htt.ecourse.responses.list;

import com.htt.ecourse.pojo.Essay;
import com.htt.ecourse.responses.EssayResponse;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EssayListResponse {
    private List<Essay> essays;
    private int totalPages;
}
