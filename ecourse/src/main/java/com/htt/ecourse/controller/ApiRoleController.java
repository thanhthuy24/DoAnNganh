package com.htt.ecourse.controller;

import com.htt.ecourse.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/roles")
@RequiredArgsConstructor
public class ApiRoleController {
    private final RoleService roleService;

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRole());
    }
}
