package com.crio.rent_read.repository;

import com.crio.rent_read.dto.RentalDto;
import com.crio.rent_read.entity.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RentalRepository extends JpaRepository<Rental,Long> {

    @Query("""
            SELECT r
            FROM Rental r
            WHERE r.user.id=:userId
            AND r.returnDate IS NULL
            """)
    List<Rental> findActiveRentalsByUserId(@Param("userId") Long userId);
    List<Rental> findByUserId(Long userId);
}
