package com.backendcrm.backendcrm.rest;

import com.backendcrm.backendcrm.entity.User;
import com.backendcrm.backendcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<User> listAll(){
        return userService.listAll(); }
    @GetMapping("/find-by-user-or-email/{usernameOrEmail}")
    public Optional<User> findByUsernameOrEmail(@PathVariable String usernameOrEmail){
        return userService.findByUsernameOrEmail(usernameOrEmail);
    }

}
