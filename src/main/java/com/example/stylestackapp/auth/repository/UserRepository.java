package com.example.stylestackapp.auth.repository;

import com.example.stylestackapp.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
