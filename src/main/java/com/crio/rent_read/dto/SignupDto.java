package com.crio.rent_read.dto;

import com.crio.rent_read.entity.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {
    @NotEmpty(message="first name cannot be empty or null")
    @Size(min=4,max=15,message = "first name should be in rnage of 4-15 characters")
    private String firstName;

    @NotEmpty(message="last name cannot be empty or null")
    @Size(min=4,max=15,message = "last name should be in range of 4-15 characters")
    private String lastName;

    @Email
    private String email;

    @NotEmpty
    @Size(min=6,max=15, message = "password should be in range of 6-15 characters")
    private String password;

    private Role role;
}
