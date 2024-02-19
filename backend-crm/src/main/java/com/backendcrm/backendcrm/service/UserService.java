package com.backendcrm.backendcrm.service;

import com.backendcrm.backendcrm.entity.User;

import java.util.List;

public interface UserService {

    List<User> listAll();
    User findByUsername(String username);

    User findByEmail(String email);
}
