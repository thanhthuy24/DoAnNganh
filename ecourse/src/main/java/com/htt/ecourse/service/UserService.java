package com.htt.ecourse.service;

import com.htt.ecourse.dtos.*;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    String login(String username, String password) throws DataNotFoundException, InvalidParamException;
    String loginSocial(UserLoginMailDTO userLoginMailDTO) throws InvalidParamException;

    User register(UserDTO userDTO) throws Exception;
    User getUserById(Long userId);
    User updateInformation(Long userId, UserUpdateDTO userUpdateDTO) throws Exception;
    Optional<User> getUserByUsername(String username);
    User updateRole(Long userId);

    User updateAvatar(Long userId, UserUpdateDTO userUpdateDTO);
    void changePassword(Long userId, ChangePasswordDTO changePasswordDTO) throws Exception;

    Page<UserResponse> getAllUsers(Pageable pageable, String keyword);
    Page<UserResponse> getUsersByRole(Long roleId, String key, Pageable pageable);

    UserResponse getUserByUserId(Long userId);
    User updateStatus(Long userId);

    User registerAccount(UserRegisterAccDTO userRegisterAccDTO) throws DataNotFoundException;
}
