package com.crio.rent_read.repository;

import com.crio.rent_read.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String username);

    Boolean existsByEmail(String email);

}
