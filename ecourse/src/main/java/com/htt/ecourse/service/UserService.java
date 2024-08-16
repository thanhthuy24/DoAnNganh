package com.htt.ecourse.service;

import com.htt.ecourse.dtos.UserDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.User;

public interface UserService {
    String login(String username, String password);
    User register(UserDTO userDTO) throws DataNotFoundException;
}
