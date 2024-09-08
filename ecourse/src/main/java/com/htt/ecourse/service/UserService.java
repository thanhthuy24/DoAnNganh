package com.htt.ecourse.service;

import com.htt.ecourse.dtos.UserDTO;
import com.htt.ecourse.dtos.UserUpdateDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.pojo.User;

public interface UserService {
    String login(String username, String password) throws DataNotFoundException, InvalidParamException;
    User register(UserDTO userDTO) throws Exception;
    User getUserById(Long userId);
    User updateInformation(Long userId, UserUpdateDTO userUpdateDTO) throws Exception;
}
