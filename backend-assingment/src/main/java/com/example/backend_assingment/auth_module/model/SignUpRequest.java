package com.example.backend_assingment.auth_module.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class SignUpRequest {

    @NotNull(message = "Name cannot be null")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "First Name can only contain alphabetic characters")
    private String firstName;

    @NotNull(message = "Last name is required")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Last Name can only contain alphabetic characters")
    private String lastName;

    @NotNull(message = "Email cannot be null")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotNull
    @NotEmpty(message = "Email cannot be empty")
    @Pattern(regexp = "^[0-9]{10}$", message = "Mobile number must be 10 digits")
    private String mobile;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]{4,15}$", message = "Username must be 4-15 characters long and contain only alphanumeric characters")
    private String username;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, max = 15, message = "Password must be between 8 and 15 characters long")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "Password must contain at least one uppercase letter, one lowercase letter, one digit, and one special character")
    private String password;
}
