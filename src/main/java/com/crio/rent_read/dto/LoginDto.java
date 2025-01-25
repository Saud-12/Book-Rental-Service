package com.crio.rent_read.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    @Size(min=6,max=15, message = "password should be in range of 6-15 characters")
    private String password;
}
