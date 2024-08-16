package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.UserDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Role;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.RoleRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public String login(String username, String password) {
        return null;
    }

    @Override
    public User register(UserDTO userDTO) throws DataNotFoundException {
        // kiểm tra email đã tồn tại chưa
        String email = userDTO.getEmail();
        if(userRepository.existsByEmail(email)){
            throw new DataIntegrityViolationException("Email Already Exists");
        }

        //convert from UserDTO -> User
        User newUser = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(email)
                .phone(userDTO.getPhone())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccount(userDTO.getFacebookAccountId())
                .googleAccount(userDTO.getGoogleAccountId())
                .build();
        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role Not Found"));
        newUser.setRole(role);
        // kiểm tra nếu có account_id thì không yêu cầu password
        if(userDTO.getFacebookAccountId()==0 && userDTO.getGoogleAccountId()==0){
            String password = userDTO.getPassword();
            // noi sau trong phan spring security

        }
        return userRepository.save(newUser);
    }
}
