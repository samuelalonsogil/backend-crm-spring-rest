package com.backendcrm.backendcrm.service;

import com.backendcrm.backendcrm.dao.UserRepository;
import com.backendcrm.backendcrm.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        User user = userRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .orElseThrow( () ->
                        new UsernameNotFoundException("User not found with username: " + usernameOrEmail) );

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map( (role) -> new SimpleGrantedAuthority( role.getName() ) ).collect( Collectors.toSet() );

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities );
    }
}
