package com.example.digitalzonerest.service;

import com.example.digitalzonerest.dto.UserDto;
import com.example.digitalzonerest.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    User registerAdmin(User user);

    List<User> getAll();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);

    User update(User oldUser, UserDto userDto);

}
