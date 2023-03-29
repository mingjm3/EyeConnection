package com.eyeconnection.server.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.eyeconnection.server.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);
}
