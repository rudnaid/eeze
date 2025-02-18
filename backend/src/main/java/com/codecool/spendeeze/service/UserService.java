package com.codecool.spendeeze.service;

import com.codecool.spendeeze.model.dto.UserRequestDTO;
import com.codecool.spendeeze.model.dto.UserResponseDTO;
import com.codecool.spendeeze.model.entity.User;
import com.codecool.spendeeze.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserResponseDTO getUserResponseDTOByPublicId(UUID userPublicId) {
        Optional<User> user = userRepository.getUserByPublicId(userPublicId);
        return user.map(this::convertToUserResponseDTO).orElseThrow(NoSuchElementException::new);
    }

    private UserResponseDTO convertToUserResponseDTO(User user) {
        return new UserResponseDTO(
                user.getPublicId(),
                user.getFirstName(),
                user.getLastName(),
                user.getCountry(),
                user.getEmail()
        );
    }

    private User convertToUser(UserRequestDTO userRequestDTO) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return objectMapper.convertValue(userRequestDTO, User.class);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public UserResponseDTO addUser(UserRequestDTO userRequestDTO) {
        User user = convertToUser(userRequestDTO);

        userRepository.save(user);

        return convertToUserResponseDTO(user);
    }

    private User getUserByPublicId(UUID userPublicId) {
        Optional<User> user = userRepository.getUserByPublicId(userPublicId);
        return user.orElseThrow(NoSuchElementException::new);
    }

    public UserResponseDTO updateUser(UUID userPublicId, UserRequestDTO userRequestDTO) {
        User user = getUserByPublicId(userPublicId);

        user.setFirstName(userRequestDTO.firstName());
        user.setLastName(userRequestDTO.lastName());
        user.setEmail(userRequestDTO.email());
        user.setCountry(userRequestDTO.country());

        userRepository.save(user);

        return convertToUserResponseDTO(user);
    }

    public int deleteUserByPublicId(UUID userPublicId) {
        return userRepository.deleteUserByPublicId(userPublicId);
    }
}
