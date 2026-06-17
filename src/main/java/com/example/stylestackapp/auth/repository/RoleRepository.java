package com.example.stylestackapp.auth.repository;

import com.example.stylestackapp.auth.entity.Role;
import com.example.stylestackapp.common.enums.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);
}
