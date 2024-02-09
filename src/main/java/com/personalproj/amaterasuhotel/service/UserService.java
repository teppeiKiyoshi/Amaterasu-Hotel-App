package com.personalproj.amaterasuhotel.service;

import com.personalproj.amaterasuhotel.exception.UserAlreadyExistsException;
import com.personalproj.amaterasuhotel.model.UserModel;
import com.personalproj.amaterasuhotel.request.RegistrationRequest;

import java.util.List;

public interface UserService {

    UserModel registerUser(RegistrationRequest registrationRequest) throws UserAlreadyExistsException;
    List<UserModel> getAllUsers();
    void deleteUser(String email);
    UserModel getUser(String email);
}
