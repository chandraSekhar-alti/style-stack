package com.example.stylestackapp.auth.repository;

import com.example.stylestackapp.auth.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
