package com.example.digitalzonerest.controllers;

import com.example.digitalzonerest.dto.AdminUserDto;
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
@RequestMapping(value = "/api/v1/admin/")
@Api(description = "Operations with admin users", tags = {"Admin"})
public class AdminRestControllerV1 {

    private final UserService userService;

    @Autowired
    public AdminRestControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(value = "users/{id}")
    @ApiOperation(value = "Finds admin by id",
            notes = "Provide an id to find admin",
            response = UserDto.class
    )
    public ResponseEntity<AdminUserDto> getUserById(@PathVariable(name = "id") Long id) {

        User user = userService.findById(id);

        if (user == null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        AdminUserDto result = AdminUserDto.fromUser(user);

        return new ResponseEntity<>(result, HttpStatus.OK);

    }

    @PostMapping("/")
    @ApiOperation(value = "Creates new admin",
            response = AdminUserDto.class
    )
    public AdminUserDto registerUser(@RequestBody UserRequestDto userRequestDto) {
        User user = userRequestDto.toUser();

        User registeredUser = userService.registerAdmin(user);

        AdminUserDto userDto = AdminUserDto.fromUser(registeredUser);

        return userDto;
    }

}