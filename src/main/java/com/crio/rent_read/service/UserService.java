package com.crio.rent_read.service;

import com.crio.rent_read.dto.RentalDto;
import com.crio.rent_read.dto.SignupDto;
import com.crio.rent_read.dto.UserDto;
import com.crio.rent_read.entity.enums.Role;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto getUserById(Long id);
    List<UserDto> getAllUsers();
    UserDto updateUserById(Long id,UserDto userDto);
    void deleteUserById(Long id);
}
