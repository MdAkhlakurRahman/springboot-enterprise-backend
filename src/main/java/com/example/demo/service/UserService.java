package com.example.demo.service;

import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;

public interface UserService {

    User createUser(User user);
    User getUserById(Long id);
    Page<UserResponseDTO> getUsers(int page, int size, String sortBy, String direction);
    User updateUser(Long id, User updatedUser);
    void deleteUser(Long id);
    Page<UserResponseDTO> searchUsers(String name, String email, int page, int size, String sortBy,
                                         String direction);
    Page<UserResponseDTO> searchUsersByDomain(String domain, int page, int size, String sortBy, String direction);

    Page<UserResponseDTO> filterUsers(String name, String email,Boolean active,int page,int size,String sortBy,String direction);
}
