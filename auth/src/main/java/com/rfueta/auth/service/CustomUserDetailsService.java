package com.rfueta.auth.service;

import com.rfueta.auth.model.User;
import com.rfueta.auth.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login)
            throws UsernameNotFoundException {

        User user = userRepository
                .findByUsernameOrEmail(login, login)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuario no encontrado: " + login));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),                 // principal
                user.getPassword(),                 // password encriptado
                user.isEnabled(),                   // enabled
                true,            // accountNonExpired
                true,        // credentialsNonExpired
                true,             // accountNonLocked
                List.of(new SimpleGrantedAuthority("ROLE_" + user.getRole().name()))
        );
    }
}