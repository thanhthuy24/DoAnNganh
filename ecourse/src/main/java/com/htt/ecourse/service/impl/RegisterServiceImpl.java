package com.htt.ecourse.service.impl;

import com.htt.ecourse.dtos.RegisterDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Register;
import com.htt.ecourse.pojo.User;
import com.htt.ecourse.repository.RegisterRepository;
import com.htt.ecourse.repository.TeacherRepository;
import com.htt.ecourse.repository.UserRepository;
import com.htt.ecourse.service.RegisterService;
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
        List<Register> list = registerRepository.findByStatus(0L);
        if (list.isEmpty())
            return null;
        return list;
    }

    @Override
    public List<Register> getRegisterActive() {
        List<Register> list = registerRepository.findByStatus(1L);
        if (list.isEmpty())
            return null;
        return list;
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

            return registerRepository.save(existingRegister);
        }
        return null;
    }
}
