package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequestDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    public UserRequestDTO() {
    }
    public UserRequestDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }


    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }
}
