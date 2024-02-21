package com.backendcrm.backendcrm.rest;

import com.backendcrm.backendcrm.entity.User;
import com.backendcrm.backendcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> listAll(){
        return userService.listAll();
    }

    @GetMapping("/find-by-username/{username}")
    public Optional<User> findByUserName(@PathVariable String username){
        return userService.findByUsername(username);
    }

    @GetMapping("/find-by-email/{email}")
    public User findByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }
}
