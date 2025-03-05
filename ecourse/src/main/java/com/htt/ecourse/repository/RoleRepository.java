package com.htt.ecourse.repository;

import com.htt.ecourse.pojo.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role getRoleById(Long roleId);
    Role findByName(String name);

}
