package com.crio.rent_read.service;

import com.crio.rent_read.dto.UserDto;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.exception.ResourceNotFoundException;
import com.crio.rent_read.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return user;
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto getUserById(Long id) {
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id: "+id+" does not exists!"));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user-> modelMapper.map(user,UserDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto updateUserById(Long id, UserDto userDto) {
        User user=userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id: "+id+" does not exists!"));
        modelMapper.map(userDto,user);
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)){
            throw new ResourceNotFoundException("User with id: "+id+" does not exists!");
        }
        userRepository.deleteById(id);
    }

}
