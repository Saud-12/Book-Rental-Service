package com.crio.rent_read.service;

import com.crio.rent_read.dto.LoginDto;
import com.crio.rent_read.dto.SignupDto;
import com.crio.rent_read.dto.UserDto;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public UserDto signup(SignupDto signupDto){
        if(userRepository.existsByEmail(signupDto.getEmail())){
            throw new BadCredentialsException("User with eamil "+signupDto.getEmail()+" already exists!");
        }
        User user=modelMapper.map(signupDto,User.class);
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        return modelMapper.map(userRepository.save(user),UserDto.class);
    }

    public UserDto login(LoginDto loginDto){
        Authentication autentication=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getEmail(),loginDto.getPassword()));
        User user=(User)autentication.getPrincipal();
        return modelMapper.map(user,UserDto.class);
    }
}
