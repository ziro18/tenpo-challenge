package com.tenpo.challenge.service.impl;

import com.tenpo.challenge.dto.RegisterDTO;
import com.tenpo.challenge.dto.UserResponse;
import com.tenpo.challenge.entity.User;
import com.tenpo.challenge.exception.UserDuplicatedException;
import com.tenpo.challenge.mapper.UserMapper;
import com.tenpo.challenge.repository.UserRepository;
import com.tenpo.challenge.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserResponse createUser(RegisterDTO registerDTO) {
        User user = UserMapper.from(registerDTO);
        LOGGER.info("Start to save user {}", user);

        userRepository
                .findByUsernameOrEmail(user.getUsername(), user.getEmail())
                .ifPresent(x -> {
                    throw new UserDuplicatedException(user.getUsername(), user.getEmail());
                });
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(user);
        return UserMapper.from(user);
    }
}
