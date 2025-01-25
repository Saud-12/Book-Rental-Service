package com.crio.rent_read.service;

import com.crio.rent_read.dto.RentalDto;

import java.util.List;

public interface RentalService {
    RentalDto rentBook(Long userId, Long bookId);
    void returnBook(Long rentalId);
    List<RentalDto> getActiveRentals(Long userId);
    List<RentalDto> getAllRentalsOfUser(Long userId);
    List<RentalDto> getAllRentals();
}
