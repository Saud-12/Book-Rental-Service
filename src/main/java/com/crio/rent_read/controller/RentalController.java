package com.crio.rent_read.controller;

import com.crio.rent_read.dto.RentalDto;
import com.crio.rent_read.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rentals")
@RequiredArgsConstructor
public class RentalController {

    private final RentalService rentalService;

    @PostMapping("/users/{userId}/books/{bookId}")
    public ResponseEntity<RentalDto> rentBook(@PathVariable Long bookId,@PathVariable Long userId){
        return new ResponseEntity<>(rentalService.rentBook(userId,bookId), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> returnBook(@PathVariable Long id){
        rentalService.returnBook(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/active-rentals/users/{userId}")
    public ResponseEntity<List<RentalDto>> getActiveRentalsOfUser(@PathVariable Long userId){
        return ResponseEntity.ok(rentalService.getActiveRentals(userId));
    }

    @GetMapping("all-rentals/users/{userId}")
    public ResponseEntity<List<RentalDto>> getAllRentalsOfUser(@PathVariable Long userId){
        return ResponseEntity.ok(rentalService.getAllRentalsOfUser(userId));
    }

    @GetMapping()
    public ResponseEntity<List<RentalDto>> getAllRentals(){
        return ResponseEntity.ok(rentalService.getAllRentals());
    }
}
