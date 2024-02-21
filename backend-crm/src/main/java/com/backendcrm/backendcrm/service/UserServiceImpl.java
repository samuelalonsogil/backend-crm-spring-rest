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
    public List<User> listAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow( () ->
                        new UsernameNotFoundException("User not found with username: " + username) );

        Set<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map( (role) -> new SimpleGrantedAuthority( role.getName() ) ).collect( Collectors.toSet() );

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities );
    }
}
