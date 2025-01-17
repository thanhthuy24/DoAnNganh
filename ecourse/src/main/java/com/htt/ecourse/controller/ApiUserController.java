package com.htt.ecourse.controller;

import com.htt.ecourse.dtos.ChangePasswordDTO;
import com.htt.ecourse.dtos.UserDTO;
import com.htt.ecourse.dtos.UserLoginDTO;
import com.htt.ecourse.dtos.UserUpdateDTO;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.UserService;
import com.htt.ecourse.service.impl.CloudinaryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor
public class ApiUserController {
    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final UserRepository userRepository;

    @GetMapping(path = "/current-user", produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin
    public ResponseEntity<Optional<User>> details(Principal user) {
        Optional<User> u = this.userService.getUserByUsername(user.getName());
        return ResponseEntity.ok(u);
    }

    @PostMapping(value = "/register", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createUser(
            @Valid @ModelAttribute UserDTO userDTO,
            BindingResult rs
    ) {
        try{
            if(rs.hasErrors()){
                List<String> errorMessages = rs.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(errorMessages);
            }
            if(!userDTO.getPassword().equals(userDTO.getRetypePassword())){
                return ResponseEntity.badRequest().body("Passwords do not match");
            }

            MultipartFile file = userDTO.getFile();
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body("File is missing or empty");
            }

            //kiem tra kích thuớc và định dạng file ảnh
            if (file.getSize() > 10 * 1024 * 1024)
                return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                        .body("File is too large, Maximum is 10MB");

            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                        .body("File must be an image");
            }

            String imageUrl = storeFile(file);

            // Thiết lập URL của hình ảnh cho CourseDTO
            userDTO.setAvatar(imageUrl);

            User user = userService.register(userDTO);
            return ResponseEntity.ok(user);
        } catch(Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    private String storeFile(MultipartFile file) throws IOException {
        Map<String, Object> uploadResult = cloudinaryService.uploadFile(file);
        return uploadResult.get("url").toString();
    }

    @PostMapping("login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody UserLoginDTO userLoginDTO) {
        //kiểm tra thông tin đăng nhập và token
        try {
            String token = userService.login(userLoginDTO.getUsername(), userLoginDTO.getPassword());
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        // trả về token trong response

    }

    @PutMapping("/update-user/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> updateInformation(
            @PathVariable("userId") Long userId,
            @Valid @ModelAttribute UserUpdateDTO userUpdateDTO,
            BindingResult rs
    ) throws Exception {
        if(rs.hasErrors()){
            List<String> errorMessages = rs.getFieldErrors()
                    .stream()
                    .map(FieldError::getDefaultMessage)
                    .toList();
            return ResponseEntity.badRequest().body(errorMessages);
        }
        userService.updateInformation(userId, userUpdateDTO);
        return ResponseEntity.ok(userUpdateDTO);
    }

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getUser(
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @PatchMapping("/update-avatar/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateAvatar(
            @PathVariable("userId") Long userId,
            @Valid @ModelAttribute UserUpdateDTO userUpdateDTO
    ) throws IOException {
        MultipartFile file = userUpdateDTO.getFile();
        if (file == null || file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is missing or empty");
        }

        //kiem tra kích thuớc và định dạng file ảnh
        if (file.getSize() > 10 * 1024 * 1024)
            return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                    .body("File is too large, Maximum is 10MB");

        String contentType = file.getContentType();
        if (contentType == null || !contentType.startsWith("image/")) {
            return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
                    .body("File must be an image");
        }

        String imageUrl = storeFile(file);

        // Thiết lập URL của hình ảnh cho CourseDTO
        userUpdateDTO.setAvatar(imageUrl);
    return ResponseEntity.ok(userService.updateAvatar(userId, userUpdateDTO));
    }

    @PatchMapping("/change-password/{userId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> changePassword(
            @PathVariable Long userId,
            @Valid @RequestBody ChangePasswordDTO changePasswordDTO
    ) throws Exception {
        userService.changePassword(userId, changePasswordDTO);
        return ResponseEntity.ok("Mật khẩu đã được thay đổi thành công");
    }

}
