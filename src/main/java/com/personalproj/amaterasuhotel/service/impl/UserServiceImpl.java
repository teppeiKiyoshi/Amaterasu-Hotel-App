package com.personalproj.amaterasuhotel.service.impl;

import com.personalproj.amaterasuhotel.controller.AuthenticationController;
import com.personalproj.amaterasuhotel.exception.UserAlreadyExistsException;
import com.personalproj.amaterasuhotel.model.RoleModel;
import com.personalproj.amaterasuhotel.model.UserModel;
import com.personalproj.amaterasuhotel.repository.RoleRepository;
import com.personalproj.amaterasuhotel.repository.UserRepository;
import com.personalproj.amaterasuhotel.request.RegistrationRequest;
import com.personalproj.amaterasuhotel.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public UserModel registerUser(RegistrationRequest registrationRequest) {
        UserModel user = new UserModel();
        if(userRepository.existsByEmail(registrationRequest.getEmail())){
            throw new UserAlreadyExistsException("Sorry, that email already exists.");
        }
        user.setFirstName(registrationRequest.getFirstName());
        user.setLastName(registrationRequest.getLastName());
        user.setEmail(registrationRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        RoleModel userRole = roleRepository.findByRoleName("ROLE_USER").get();
        user.setRoles(Collections.singleton(userRole));
        return userRepository.save(user);
    }

    @Override
    public List<UserModel> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void deleteUser(String email) {
        UserModel user = getUser(email);
        if(Objects.nonNull(user)){
            userRepository.deleteByEmail(email);
        }
    }

    @Override
    public UserModel getUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
