package com.backendcrm.backendcrm.rest;

import com.backendcrm.backendcrm.dao.RoleRepository;
import com.backendcrm.backendcrm.dao.UserRepository;
import com.backendcrm.backendcrm.dto.LoginDto;
import com.backendcrm.backendcrm.dto.SignUpDto;
import com.backendcrm.backendcrm.entity.Role;
import com.backendcrm.backendcrm.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("sign-in")
    public ResponseEntity <String> authenticateUser(@RequestBody LoginDto loginDto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken( loginDto.getUsername(), loginDto.getPassword() ) );

        SecurityContextHolder.getContext().setAuthentication( authentication );
        return new ResponseEntity<>( "User signed-in successfully!. " + "\n" + " Welcome " + loginDto.getUsername(), HttpStatus.OK);
    }

    @PostMapping("sign-up")
    public ResponseEntity <?> registerUser(@RequestBody SignUpDto signUpDto){
        System.out.println(" registerUser ");

        /* add check for user exists in DB for username*/
        if ( userRepository.existsByUsername(signUpDto.getUsername() ) ){
            return new ResponseEntity<>("Username is already taken! ", HttpStatus.BAD_REQUEST);
        }

        /* add check for user exists in DB for email */
        if ( userRepository.existsByEmail(signUpDto.getEmail() ) ){
            return new ResponseEntity<>("Email is already registered! ", HttpStatus.BAD_REQUEST);
        }

        /* create USER object */
        User user = new User();
        user.setUsername(signUpDto.getUsername() );
        user.setEmail(signUpDto.getEmail() );
        user.setFirstName(signUpDto.getFirstName() );
        user.setLastName(signUpDto.getLastName() );
        user.setPassword(passwordEncoder.encode(signUpDto.getPassword() ) );
        user.setEnabled(signUpDto.isEnabled() );

        user.setRoles( Arrays.asList( roleRepository.findByName("ROLE_USER").get() ) );
        if (user.isEnabled() ) user.setRoles( Arrays.asList( roleRepository.findByName("ROLE_USER").get(), roleRepository.findByName("ROLE_ADMIN").get() ) );

        userRepository.save(user);
        return new ResponseEntity<>( "User registered successfully!. " , HttpStatus.OK);
    }


}
