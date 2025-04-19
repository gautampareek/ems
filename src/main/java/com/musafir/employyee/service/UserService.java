package com.musafir.employyee.service;

import com.musafir.employyee.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    String deleteUserById(Long id);
}
