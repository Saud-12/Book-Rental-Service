package com.crio.rent_read.service;

import com.crio.rent_read.dto.RentalDto;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.Rental;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.entity.enums.AvailabilityStatus;
import com.crio.rent_read.exception.BookAlreadyReturnedException;
import com.crio.rent_read.exception.BookIsNotAvailableForRentException;
import com.crio.rent_read.exception.MaximumRentalLimitReachedException;
import com.crio.rent_read.exception.ResourceNotFoundException;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.repository.RentalRepository;
import com.crio.rent_read.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService{
    private final RentalRepository rentalRepository;
    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Transactional
    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public RentalDto rentBook(Long userId, Long bookId) {
        User currentUser=getCurrentUser();

        if(currentUser.getId() != userId) {
            throw new AccessDeniedException("You do not have permission to perform this operation");
        }

        User user=userRepository.findById(userId).orElseThrow(()->new ResourceNotFoundException("User with id: "+userId+" does not exists!"));
        Book book=bookRepository.findById(bookId).orElseThrow(()->new ResourceNotFoundException("Book with id: "+bookId+" does not exists!"));

        if(getActiveRentals(userId).size()==2){
            throw new MaximumRentalLimitReachedException("User has already reached maximum book rental limit!");
        }
        if(book.getAvailabilityStatus()==AvailabilityStatus.NOT_AVAILABLE){
            throw new BookIsNotAvailableForRentException("Book is not available for rent at the moment!");
        }
        book.setAvailabilityStatus(AvailabilityStatus.NOT_AVAILABLE);
        Rental rental=Rental.builder()
                .user(user)
                .book(book)
                .rentedAt(LocalDate.now())
                .build();

        return modelMapper.map(rentalRepository.save(rental), RentalDto.class);
    }

    @Transactional
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @Override
    public void returnBook(Long id) {
        Rental rental=rentalRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Rental with id: "+id+" does not exists!"));
        User currentUser=getCurrentUser();

        if(currentUser.getId() != rental.getUser().getId()) {
            throw new AccessDeniedException("You do not have permission to perform this operation");
        }

        if(rental.getReturnDate()!=null){
            throw new BookAlreadyReturnedException("Book with id: "+rental.getBook().getId()+" has already been returned");
        }
        Book book=rental.getBook();
        book.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        bookRepository.save(book);
        rental.setReturnDate(LocalDate.now());
        rentalRepository.save(rental);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public List<RentalDto> getActiveRentals(Long userId) {

        User currentUser=getCurrentUser();

        if(currentUser.getId() != userId) {
            throw new AccessDeniedException("You do not have permission to perform this operation");
        }

        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User with id: "+userId+" does not exists!");
        }
        return rentalRepository.findActiveRentalsByUserId(userId).stream()
                .map(rental-> modelMapper.map(rental,RentalDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public List<RentalDto> getAllRentalsOfUser(Long userId) {
        User currentUser=getCurrentUser();

        if(currentUser.getId() != userId) {
            throw new AccessDeniedException("You do not have permission to perform this operation");
        }

        if(!userRepository.existsById(userId)){
            throw new ResourceNotFoundException("User with id: "+userId+" does not exists!");
        }
        return rentalRepository.findByUserId(userId).stream()
                .map(rental-> modelMapper.map(rental,RentalDto.class))
                .collect(Collectors.toList());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    public List<RentalDto> getAllRentals() {
        return rentalRepository.findAll().stream()
                .map(rental->modelMapper.map(rental,RentalDto.class))
                .collect(Collectors.toList());
    }

    private User getCurrentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
