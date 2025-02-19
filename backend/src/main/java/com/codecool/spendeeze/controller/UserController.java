package com.codecool.spendeeze.controller;

import com.codecool.spendeeze.model.dto.UserRequestDTO;
import com.codecool.spendeeze.model.dto.UserResponseDTO;
import com.codecool.spendeeze.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponseDTO getUser(@RequestParam("userPublicId") UUID userPublicId) {
        return userService.getUserResponseDTOByPublicId(userPublicId);
    }

    @PostMapping("/register")
    public UserResponseDTO createUser(@RequestBody UserRequestDTO userRequestDTO) {
        return userService.addUser(userRequestDTO);
    }

    @PostMapping
    public UserResponseDTO updateUser(@RequestParam UUID userPublicId, @RequestBody UserRequestDTO userRequestDTO) {
        return userService.updateUser(userPublicId, userRequestDTO);
    }

    @PostMapping
    public int deleteUser(@RequestParam UUID userPublicId) {
        return userService.deleteUserByPublicId(userPublicId);
    }
}
