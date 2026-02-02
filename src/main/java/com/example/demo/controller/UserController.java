package com.example.demo.controller;

import com.example.demo.dto.UserRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // ------------------- CREATE USER -------------------
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO userRequestDTO){
        // DTO → Entity
        User userEntity = UserMapper.toEntity(userRequestDTO);
        // Service call
        User savedUser = userService.createUser(userEntity);
        // Entity → DTO
        UserResponseDTO userResponseDTO = UserMapper.toDTO(savedUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long id){
        //Service call
        User user = userService.getUserById(id);
        UserResponseDTO dto = UserMapper.toDTO(user);

        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<UserResponseDTO>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        Page<UserResponseDTO> usersPage = userService.getUsers(page, size, sortBy, direction);
        return ResponseEntity.ok(usersPage);
    }

   @GetMapping("/search")
   public ResponseEntity<Page<UserResponseDTO>> searchUsers(
           @RequestParam(required = false) String name,
           @RequestParam(required = false) String email,
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size,
           @RequestParam(defaultValue = "id") String sortBy,
           @RequestParam(defaultValue = "asc") String direction
   ){
       Page<UserResponseDTO> result = userService.searchUsers(name, email, page, size, sortBy, direction);
       return ResponseEntity.ok(result);
   }



    @GetMapping("/search/domain")
    public ResponseEntity<Page<UserResponseDTO>>  searchUserByDomain(
            @RequestParam String domain,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        Page<UserResponseDTO> result = userService.searchUsersByDomain(domain,page,size,sortBy,direction);
       return ResponseEntity.ok(result);
    }

    @GetMapping("/filter")
    public ResponseEntity<Page<UserResponseDTO>> filterUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Boolean active,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        Page<UserResponseDTO> userPage = userService.filterUsers(name, email, active, page, size, sortBy, direction);
        return ResponseEntity.ok(userPage);
    }


    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id,@Valid @RequestBody UserRequestDTO dto){
        User updatedEntity = UserMapper.toEntity(dto);
        //Service call
        User updatedUser = userService.updateUser(id , updatedEntity);

        UserResponseDTO responseDTO = UserMapper.toDTO(updatedUser);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully");
    }

}
