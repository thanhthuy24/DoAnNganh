package com.htt.ecourse.service;

import com.htt.ecourse.dtos.ChangePasswordDTO;
import com.htt.ecourse.dtos.UserDTO;
import com.htt.ecourse.dtos.UserUpdateDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.pojo.User;

import java.util.Optional;

public interface UserService {
    String login(String username, String password) throws DataNotFoundException, InvalidParamException;
    User register(UserDTO userDTO) throws Exception;
    User getUserById(Long userId);
    User updateInformation(Long userId, UserUpdateDTO userUpdateDTO) throws Exception;
    Optional<User> getUserByUsername(String username);
    User updateRole(Long userId);

    User updateAvatar(Long userId, UserUpdateDTO userUpdateDTO);
    void changePassword(Long userId, ChangePasswordDTO changePasswordDTO) throws Exception;
}
