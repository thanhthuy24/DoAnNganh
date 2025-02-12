package com.htt.ecourse.service.impl;

import com.htt.ecourse.components.JwtTokenUtil;
import com.htt.ecourse.dtos.*;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.exceptions.InvalidParamException;
import com.htt.ecourse.exceptions.PermissionDenyException;
import com.htt.ecourse.pojo.Lesson;
import com.htt.ecourse.pojo.Role;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.pojo.Video;
import com.htt.ecourse.repository.RoleRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.responses.UserResponse;
import com.htt.ecourse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.DateTimeException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
        String username = userDTO.getUsername();
        if(userRepository.existsByUsername(username)){
            throw new DataIntegrityViolationException("username Already Exists");
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
                .email(userDTO.getEmail())
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

    @Override
    public User updateAvatar(Long userId, UserUpdateDTO userUpdateDTO) {
        User existingUser = getUserById(userId);
        existingUser.setAvatar(userUpdateDTO.getAvatar());
        existingUser.setFirstName(userUpdateDTO.getFirstName());
        existingUser.setLastName(userUpdateDTO.getLastName());
        existingUser.setEmail(userUpdateDTO.getEmail());
        existingUser.setPhone(userUpdateDTO.getPhone());
        existingUser.setUsername(userUpdateDTO.getUsername());
        existingUser.setDateOfBirth(userUpdateDTO.getDateOfBirth());
        return userRepository.save(existingUser);
    }

    @Override
    public void changePassword(Long userId, ChangePasswordDTO changePasswordDTO) throws Exception {
        User existingUser = getUserById(userId);
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), existingUser.getPassword())){
            throw new IllegalArgumentException("Old password does not match!");
        }
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getRetypePassword())){
            throw new IllegalArgumentException("New password and retype password does not match!");
        }
        String encodedPassword = passwordEncoder.encode(changePasswordDTO.getNewPassword());

        existingUser.setPassword(encodedPassword);
        userRepository.save(existingUser);
    }

    @Override
    public Page<UserResponse> getAllUsers(Pageable pageable, String keyword) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long role = userRepository.getUserByUsername(username).getRole().getId();
        if (role != 2){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Authorization!!"
            );
        }

        Page<User> user = userRepository.searchUsersAll(keyword, pageable);
        return user.map(this::convertToDTO);
    }

    @Override
    public Page<UserResponse> getUsersByRole(Long roleId, String key, Pageable pageable) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long role = userRepository.getUserByUsername(username).getRole().getId();
        if (role != 2){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Authorization!!"
            );
        }

        Page<User> user = userRepository.findUsersByRoleId(roleId, key, pageable);
        return user.map(this::convertToDTO);
    }

    @Override
    public UserResponse getUserByUserId(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Can not find user by id + " + userId));
        return convertToDTO(user);
    }

    @Override
    public User updateStatus(Long userId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long role = userRepository.getUserByUsername(username).getRole().getId();
        if (role != 2){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN, "Authorization!!"
            );
        }

        User getUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        getUser.setIsActive(!getUser.getIsActive());
        return userRepository.save(getUser);
    }

    @Override
    public User registerAccount(UserRegisterAccDTO userRegisterAccDTO) throws DataNotFoundException {
        String username = userRegisterAccDTO.getUsername();
        if(userRepository.existsByUsername(username)){
            throw new DataIntegrityViolationException("username Already Exists");
        }

        Role role = roleRepository.findById(userRegisterAccDTO.getRoleId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found!"));

        //convert from UserDTO -> User
        User newUser = User.builder()
                .firstName(userRegisterAccDTO.getFirstName())
                .lastName(userRegisterAccDTO.getLastName())
                .email(userRegisterAccDTO.getEmail())
                .phone(userRegisterAccDTO.getPhone())
                .username(userRegisterAccDTO.getUsername())
                .dateOfBirth(new Date())
                .facebookAccount(userRegisterAccDTO.getFacebookAccountId())
                .googleAccount(userRegisterAccDTO.getGoogleAccountId())
                .build();

        newUser.setRole(role);
        // kiểm tra nếu có account_id thì không yêu cầu password
        if(userRegisterAccDTO.getFacebookAccountId()==0 && userRegisterAccDTO.getGoogleAccountId()==0){
            String password = userRegisterAccDTO.getPassword();
            String encodedPassword = passwordEncoder.encode(password);
            // noi sau trong phan spring security
            newUser.setPassword(encodedPassword);
        }
        return userRepository.save(newUser);
    }

    private UserResponse convertToDTO(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .avatar(user.getAvatar())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .dateOfBirth(user.getDateOfBirth())
                .googleAccountId(user.getGoogleAccount())
                .email(user.getEmail())
                .phone(user.getPhone())
                .username(user.getUsername())
                .facebookAccountId(user.getFacebookAccount())
                .roleId(user.getRole().getId())
                .isActive(user.getIsActive())
                .build();
    }
}
