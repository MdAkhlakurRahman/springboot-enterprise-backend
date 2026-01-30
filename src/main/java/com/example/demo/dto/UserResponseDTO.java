package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;


    public UserResponseDTO() {
    }
    public UserResponseDTO(Long id, String name, String email) {
        this.name = name;
        this.email = email;
        this.id = id;
    }


    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
}
