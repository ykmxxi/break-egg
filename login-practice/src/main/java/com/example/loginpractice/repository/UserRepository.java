package com.example.loginpractice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.loginpractice.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(final String username);

    boolean existsByNickname(final String nickname);

    Optional<User> findByUsername(final String username);

}
