package com.example.demo.service;

import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.exception.UserNotFoundException;
import com.example.demo.mapper.UserMapper;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;




@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        log.info("Creating new user with email {}", user.getEmail());
        User savedUser = userRepository.save(user);
        log.info("User created successfully with id: {}",savedUser.getId());
        return savedUser;
    }


    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> {
            log.warn("User with id {} not found", id);
            return new UserNotFoundException("User not found with id: " +id);
        });
    }

    @Override
    public Page<UserResponseDTO> getUsers(int page, int size, String sortBy, String direction) {
        log.info("Fetching users: page={},size={}, sortBy={}, direction={}",page,size,sortBy,direction);
        Sort sort = direction.equalsIgnoreCase("asc")
                    ?Sort.by(sortBy).ascending()
                    :Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page,size,sort);
        Page<User> usersPage = userRepository.findAll(pageable);
        return usersPage.map(UserMapper::toDTO);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User user = userRepository.findById(id).orElseThrow(() -> {
            log.warn("User with id {} not found", id);
            return new UserNotFoundException("User not found with id: " +id);
        });

        user.setEmail(updatedUser.getEmail());
        user.setName(updatedUser.getName());
        User savedUser = userRepository.save(user);
        log.info("Updated user with id {}",id);
        return savedUser;
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).orElseThrow(() ->{
            log.warn("User with id {} not found",id);
            return new UserNotFoundException("User not found with id: " +id);
        });
        userRepository.deleteById(id);
        log.info("User with id {} deleted", id);
    }

    @Override
    public Page<UserResponseDTO> searchUsers(String name, String email, int page, int size, String sortBy,
                                             String direction){
        log.info("Searching users: name={}, email={}, page={}, size={}, sortBy={}, direction={}",
                name, email, page, size, sortBy, direction
        );

            // 1️⃣ Normalize inputs (treat blank as null)
            if (name != null && name.trim().isEmpty()) {
                name = null;
            }
            if (email != null && email.trim().isEmpty()) {
                email = null;
            }

            // 2️⃣ Build Sort
            Sort sort = direction.equalsIgnoreCase("asc")
                    ? Sort.by(sortBy).ascending()
                    : Sort.by(sortBy).descending();

            // 3️⃣ Build Pageable
            Pageable pageable = PageRequest.of(page, size, sort);

            // 4️⃣ Decide which repository method to call
            Page<User> usersPage;

            if (name != null && email != null) {
                usersPage = userRepository.findByNameContainingIgnoreCaseAndEmailContainingIgnoreCase(
                                name, email, pageable
                        );
            } else if (name != null) {
                usersPage = userRepository.findByNameContainingIgnoreCase(name, pageable);
            } else if (email != null) {
                usersPage = userRepository.findByEmailContainingIgnoreCase(email, pageable);
            } else {
                // No filters → return all users (reuse Ticket 11 logic)
                usersPage = userRepository.findAll(pageable);
            }

            // 5️⃣ Convert Entity → DTO
            return usersPage.map(UserMapper::toDTO);
        }
}

