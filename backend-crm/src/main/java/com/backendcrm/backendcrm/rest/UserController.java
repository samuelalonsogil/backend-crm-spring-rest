package com.backendcrm.backendcrm.rest;

import com.backendcrm.backendcrm.dao.RoleRepository;
import com.backendcrm.backendcrm.dao.UserRepository;
import com.backendcrm.backendcrm.dto.SignUpDto;
import com.backendcrm.backendcrm.entity.Role;
import com.backendcrm.backendcrm.entity.User;
import com.backendcrm.backendcrm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    public UserController(UserService userService) {
    }

    @GetMapping("/")
    public List<User> listAll(){
        return userRepository.findAll(); }
    @GetMapping("/{usernameOrEmail}")
    public Optional<User> findByUsernameOrEmail(@PathVariable String usernameOrEmail){
        return userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity <String> deleteUser(@PathVariable int id){
        Optional<User> user = userRepository.findById(id);
        userRepository.deleteById(id);
        return new ResponseEntity<>( "User " + "'" + user.orElseThrow().getUsername() + "'" + " deleted successfully!. " , HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity <String> updateUser(@PathVariable int id, @RequestBody SignUpDto signUpDto){
        User user = userRepository.findById(id).orElseThrow();

        System.out.println("User: " + user);
        System.out.println("UserDto: " + signUpDto);

        user.setUsername(signUpDto.getUsername() );
        if ( userRepository.existsByUsername(user.getUsername()  ) && !(user.getUsername().equals( signUpDto.getUsername() ) ) ){
            return new ResponseEntity<>("Username is already taken! ", HttpStatus.BAD_REQUEST);
        }
        user.setEmail(signUpDto.getEmail() );
        if ( userRepository.existsByEmail(signUpDto.getEmail() ) && !(user.getEmail().equals( signUpDto.getEmail() ) ) ){
            return new ResponseEntity<>("Email is already registered! ", HttpStatus.BAD_REQUEST);
        }
        user.setFirstName(signUpDto.getFirstName() );
        user.setLastName(signUpDto.getLastName() );
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword() ) );
        user.setEnabled(signUpDto.isEnabled() );

        Collection<Role> roles = user.getRoles();
        if (user.isEnabled() ) {
            roles.clear();
            roles.add(roleRepository.findByName("ROLE_USER").orElseThrow() );
            roles.add(roleRepository.findByName("ROLE_ADMIN").orElseThrow() );

            user.setRoles( roles );
        }else{
            roles.clear();
            roles.add(roleRepository.findByName("ROLE_USER").orElseThrow() );

            user.setRoles(roles);
        }

        userRepository.save(user);

        return new ResponseEntity<>( "User " + "'" + user.getUsername() + "'" + " updated successfully!. " + "\n" +
                "username: " + user.getUsername() + "\n" +
                "email: " + user.getEmail() + "\n" +
                "password: " + user.getPassword() + "\n" +
                "first name: " + user.getFirstName() + "\n" +
                "last name: " + user.getLastName() + "\n" +
                "enabled: " + user.isEnabled() + "\n" +
                "roles: " + user.getRoles() + "\n"
                , HttpStatus.OK);
    }

}
