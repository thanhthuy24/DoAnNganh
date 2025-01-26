package com.htt.ecourse.service;

import com.htt.ecourse.dtos.RegisterDTO;
import com.htt.ecourse.exceptions.DataNotFoundException;
import com.htt.ecourse.pojo.Register;

import java.util.List;

public interface RegisterService {
    List<Register> getAllRegisters();
    List<Register> getRegisterInActive();
    List<Register> getRegisterActive();

    List<Register> getListFormByUserId();

    Register getRegisterByUserId(Long userId);
    Register getRegisterByUser(Long userId);
    Register getRegisterById(Long registerId) throws DataNotFoundException;
    Register createRegister(RegisterDTO registerDTO);
    Register updateRegister(RegisterDTO registerDTO, Long id) throws DataNotFoundException;
}
