package com.personalproj.amaterasuhotel.service;

import com.personalproj.amaterasuhotel.exception.UserAlreadyExistsException;
import com.personalproj.amaterasuhotel.model.UserModel;

import java.util.List;

public interface UserService {

    UserModel registerUser(UserModel user) throws UserAlreadyExistsException;
    List<UserModel> getAllUsers();
    void deleteUser(String email);
    UserModel getUser(String email);
}
