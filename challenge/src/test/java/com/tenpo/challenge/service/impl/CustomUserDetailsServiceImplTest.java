package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.util.UserTestFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.jdbc.Sql;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql("/initUser.sql")
class CustomUserDetailsServiceImplTest {
    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserTestFactory userTestFactory;

    @Test
    void whenUsernameExits_thenReturnsNewUser() {
        com.tenpo.challenge.entity.User user = userTestFactory.buildUserValid();

        UserDetails userDetails = userDetailsService.loadUserByUsername(userTestFactory.USERNAME_VALID);

        assertThat(userDetails.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDetails.getAuthorities()).isEqualTo( Collections.singleton(new SimpleGrantedAuthority("ADMIN")));
    }

    @Test
    void whenUsernameNotExits_thenThrowUsernameNotFoundException() {
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(userTestFactory.USERNAME_INVALID))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessage("Username not found : " + userTestFactory.USERNAME_INVALID);

    }
}