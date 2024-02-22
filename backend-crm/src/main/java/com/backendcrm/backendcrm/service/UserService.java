package com.backendcrm.backendcrm.service;

import com.backendcrm.backendcrm.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    List<User> listAll();
    Optional<User> findByUsernameOrEmail(String usernameOrEmail);

}
