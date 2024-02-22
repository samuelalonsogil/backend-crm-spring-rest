package com.backendcrm.backendcrm.dao;


import com.backendcrm.backendcrm.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String username);
    Optional<User> findByUsernameOrEmail(String username, String email);
}
