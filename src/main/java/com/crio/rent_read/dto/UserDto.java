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
public class UserDto {
    private Long id;
    @NotEmpty(message="first name cannot be empty or null")
    @Size(min=4,max=15, message = "first name should be in range of 4-15 characters")
    private String firstName;
    @NotEmpty(message="last name cannot be empty or null")
    @Size(min=4,max=15, message="last name should be in range of 4-15 characters")
    private String lastName;
    @Email
    private String email;
    private Role role;
}
