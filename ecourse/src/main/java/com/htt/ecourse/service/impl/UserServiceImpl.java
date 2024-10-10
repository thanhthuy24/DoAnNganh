package com.htt.ecourse.service.impl;

import com.htt.ecourse.components.JwtTokenUtil;
import com.htt.ecourse.dtos.UserDTO;
import com.htt.ecourse.dtos.UserUpdateDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.exceptions.PermissionDenyException;
import com.htt.ecourse.pojo.Role;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.RoleRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.DateTimeException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;
    private final AuthenticationManager authenticationManager;

    @Override
    public String login(String username, String password) throws DataNotFoundException, InvalidParamException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            throw new DataNotFoundException("Invalid username or password!");
        }
        User existingUser = optionalUser.get();
        //check password
        if(existingUser.getFacebookAccount() == 0
            && existingUser.getGoogleAccount() == 0) {
            if(!passwordEncoder.matches(password, existingUser.getPassword())){
                throw new BadCredentialsException("Invalid username or password!");
            }
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authenticationToken);
        return jwtTokenUtil.generateToken(existingUser);
    }

    @Override
    public User register(UserDTO userDTO) throws Exception {
        // kiểm tra email đã tồn tại chưa
        String email = userDTO.getEmail();
        if(userRepository.existsByEmail(email)){
            throw new DataIntegrityViolationException("Email Already Exists");
        }

        if(userRepository.existsByUsername(userDTO.getUsername())){
            throw new DataIntegrityViolationException("Username Already Exists");
        }

        Role role = roleRepository.findById(userDTO.getRoleId())
                .orElseThrow(() -> new DataNotFoundException("Role not found!"));

        if(role.getName().equals(Role.ADMIN)){
            throw new PermissionDenyException("You cannot register a ADMIN account!!");
        }

        //convert from UserDTO -> User
        User newUser = User.builder()
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .email(email)
                .phone(userDTO.getPhone())
                .avatar(userDTO.getAvatar())
                .username(userDTO.getUsername())
                .dateOfBirth(userDTO.getDateOfBirth())
                .facebookAccount(userDTO.getFacebookAccountId())
                .googleAccount(userDTO.getGoogleAccountId())
                .build();

        newUser.setRole(role);
        // kiểm tra nếu có account_id thì không yêu cầu password
        if(userDTO.getFacebookAccountId()==0 && userDTO.getGoogleAccountId()==0){
            String password = userDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            // noi sau trong phan spring security
            newUser.setPassword(encodedPassword);

        }
        return userRepository.save(newUser);
    }

    @Override
    public User getUserById(Long userId){
        return  userRepository.findById(userId)
                .orElseThrow(() -> new DateTimeException("Can not find user by id + " + userId));
    }

    @Override
    public User updateInformation(Long userId, UserUpdateDTO userUpdateDTO) throws Exception {
        User existingUser = getUserById(userId);

        if(existingUser != null){
            existingUser.setFirstName(userUpdateDTO.getFirstName());
            existingUser.setLastName(userUpdateDTO.getLastName());
            existingUser.setEmail(userUpdateDTO.getEmail());
            existingUser.setPhone(userUpdateDTO.getPhone());
            existingUser.setUsername(userUpdateDTO.getUsername());
            existingUser.setDateOfBirth(userUpdateDTO.getDateOfBirth());
            existingUser.setFacebookAccount(userUpdateDTO.getFacebookAccountId());
            existingUser.setGoogleAccount(userUpdateDTO.getGoogleAccountId());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User updateRole(Long userId) {
        User existingUser = getUserById(userId);

        Role teacherRole = roleRepository.getRoleById(3L);

        if (existingUser != null){
            existingUser.setRole(teacherRole);
            return userRepository.save(existingUser);
        }
        return null;
    }
}
