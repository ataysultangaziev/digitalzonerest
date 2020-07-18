package com.example.digitalzonerest.dto;

import com.example.digitalzonerest.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class UserRequestDto {

    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String password;

    public User toUser() {

        User user = new User();
        user.setUsername(username);
        user.setFirstName(firstname);
        user.setLastName(lastname);
        user.setEmail(email);
        user.setPassword(password);
        user.setCreated(new Date());
        user.setUpdated(new Date());

        return user;
    }

    public UserDto fromUser(User user) {

        UserDto userDto = new UserDto();
        userDto.setUsername(user.getUsername());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setEmail(user.getEmail());

        return userDto;
    }
}
