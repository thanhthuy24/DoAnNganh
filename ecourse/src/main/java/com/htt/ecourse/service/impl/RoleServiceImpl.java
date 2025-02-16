package com.htt.ecourse.service.impl;

import com.htt.ecourse.pojo.Role;
import com.htt.ecourse.repository.RoleRepository;
import com.htt.ecourse.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
