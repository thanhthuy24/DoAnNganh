package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.RegisterDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Register;
import com.htt.ecourse.pojo.Teacher;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.RegisterRepository;
import com.htt.ecourse.repository.TeacherRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.RegisterService;
import com.htt.ecourse.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final RegisterRepository registerRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final TeacherRepository teacherRepository;

    @Override
    public List<Register> getAllRegisters() {
        List<Register> list = registerRepository.findAll();
        if (list.isEmpty())
            return (List<Register>) new DataNotFoundException("Không có đơn cần phê duyệt");
        return list;
    }

    @Override
    public List<Register> getRegisterInActive() {
        List<Register> list = registerRepository.findByStatus(false);
        if (list.isEmpty())
            return null;
        return list;
    }

    @Override
    public List<Register> getRegisterActive() {
        List<Register> list = registerRepository.findByStatus(true);
        if (list.isEmpty())
            return null;
        return list;
    }

    @Override
    public List<Register> getListFormByUserId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.getUserByUsername(username).getId();

        List<Register> list = registerRepository.findRegisterByUserId(userId);

        return list;
    }

    @Override
    public Register getRegisterByUserId(Long userId) {
        if (registerRepository.findByUserId(userId) == null) {
            return null;
        }
        Register r = registerRepository.findByUserId(userId);
        if(r.getStatus() == true){
            return r;
        }
        return null;
    }

    public Register getRegisterByUser(Long userId) {
        Register r = registerRepository.findByUserId(userId);
        if (r != null) {
            return r;
        }
        return null;
    }

    @Override
    public Register getRegisterById(Long registerId) throws DataNotFoundException {
        return registerRepository.findById(registerId)
                .orElseThrow(() -> new DataNotFoundException("Không tìm thấy ID như trên!!"));
    }

    @Override
    public Register createRegister(RegisterDTO registerDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Long userId = userRepository.getUserByUsername(username).getId();

        Register newRegister = Register.builder()
                .reason(registerDTO.getReason())
                .position(registerDTO.getPosition())
                .status(false)
                .user(userRepository.findById(userId).get())
                .build();
        registerRepository.save(newRegister);
        return newRegister;
    }

    @Override
    public Register updateRegister(RegisterDTO registerDTO, Long id) throws DataNotFoundException {
        Register existingRegister = registerRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Not found"));
        if (existingRegister != null) {
            User existingUser = userRepository.findById(registerDTO.getUserId())
                    .orElseThrow(() -> new DataNotFoundException("User not found!!"));

            existingRegister.setPosition(registerDTO.getPosition());
            existingRegister.setStatus(true);
            existingRegister.setReason(registerDTO.getReason());
            existingRegister.setUser(existingUser);

//            userService.updateRole(existingUser.getId());
//
//            Teacher newTeacher = Teacher.builder()
//                    .description(registerDTO.getReason())
//                    .position(registerDTO.getPosition())
//                    .user(existingUser)
//                    .build();
//            teacherRepository.save(newTeacher);
            return registerRepository.save(existingRegister);
        }
        return null;
    }
}
