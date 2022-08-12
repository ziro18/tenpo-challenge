package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.tenpo.challenge.entity.User user = userRepository.findByUsername(username)
                .orElseThrow((() -> new UsernameNotFoundException("Username not found : " + username)));

        return new User(user.getUsername(), user.getPassword(), Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
    }
}
