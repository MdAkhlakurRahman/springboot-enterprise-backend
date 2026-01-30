package com.example.demo.service;

import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import org.springframework.data.domain.Page;

public interface UserService {

    User createUser(User user);
    User getUserById(Long id);
    Page<UserResponseDTO> getUsers(int page, int size, String sortBy, String direction);
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
}
