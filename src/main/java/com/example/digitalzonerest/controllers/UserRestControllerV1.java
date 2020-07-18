package com.example.digitalzonerest.controllers;

import com.example.digitalzonerest.dto.UserDto;
import com.example.digitalzonerest.dto.UserRequestDto;
import com.example.digitalzonerest.model.User;
import com.example.digitalzonerest.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/users/")
@Api(description = "User operations", tags = {"User"})
public class UserRestControllerV1 {

    private final UserService userService;

    @Autowired
    public UserRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "{id}")
    @ApiOperation(value = "Finds user by id",
            notes = "Provide an id to find user",
            response = UserDto.class
    )
    public ResponseEntity<UserDto> getUserById(@PathVariable(name = "id") Long id){
        User user = userService.findById(id);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        UserDto result = UserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/")
    @ApiOperation(value = "Creates new user",
            response = UserDto.class
    )
    public UserDto registerUser(@RequestBody UserRequestDto userRequestDto) {
        User user = userRequestDto.toUser();

        User registeredUser = userService.register(user);

        UserDto userDto = UserDto.fromUser(registeredUser);

        return userDto;
    }

    @PatchMapping("/")
    public UserDto updateUser(@RequestBody UserDto userDto) {

        User user = userService.findById(userDto.getId());

        User updatedUser = userService.update(user, userDto);

        UserDto userDto1 = UserDto.fromUser(updatedUser);

        return userDto1;
    }

}