package com.htt.ecourse.responses.list;

import com.htt.ecourse.pojo.Notification;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationListResponse {
    private List<Notification> notifications;
    private int totalPages;
}
