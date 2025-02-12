package com.htt.ecourse.responses.list;

import com.htt.ecourse.dtos.UserUpdateDTO;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.responses.UserResponse;
import lombok.*;

import java.util.List;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserListResponse {
    private List<UserResponse> users;
    private int totalPages;
}
