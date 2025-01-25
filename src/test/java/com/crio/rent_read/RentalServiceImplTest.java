package com.crio.rent_read;


import com.crio.rent_read.dto.BookDto;
import com.crio.rent_read.dto.RentalDto;
import com.crio.rent_read.dto.UserDto;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.Rental;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.entity.enums.AvailabilityStatus;
import com.crio.rent_read.exception.BookAlreadyReturnedException;
import com.crio.rent_read.exception.BookIsNotAvailableForRentException;
import com.crio.rent_read.exception.MaximumRentalLimitReachedException;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.repository.RentalRepository;
import com.crio.rent_read.repository.UserRepository;
import com.crio.rent_read.service.RentalServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RentalServiceImplTest {


    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private RentalServiceImpl rentalService;

    private User currentUser;
    private User mockUser;
    private Book mockBook;
    private Rental mockRental;
    private RentalDto mockRentalDto;

    @BeforeEach
    void setUp() {
        // Setup security context
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        currentUser = new User();
        currentUser.setId(1L);

        mockUser = new User();
        mockUser.setId(1L);

        mockBook = new Book();
        mockBook.setId(1L);
        mockBook.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);

        mockRental = Rental.builder()
                .id(1L)
                .user(mockUser)
                .book(mockBook)
                .rentedAt(LocalDate.now())
                .build();

        mockRentalDto = new RentalDto();
        mockRentalDto.setId(1L);
    }


    @Test
    void testReturnBook_Success() {
        // Setup current user
        when(authentication.getPrincipal()).thenReturn(currentUser);

        mockRental.setUser(mockUser);
        when(rentalRepository.findById(1L)).thenReturn(Optional.of(mockRental));

        rentalService.returnBook(1L);

        assertNotNull(mockRental.getReturnDate());
    }

    @Test
    void testGetActiveRentals_Success() {
        // Setup current user
        when(authentication.getPrincipal()).thenReturn(currentUser);

        when(userRepository.existsById(1L)).thenReturn(true);
        when(rentalRepository.findActiveRentalsByUserId(1L)).thenReturn(Arrays.asList(mockRental));
        when(modelMapper.map(mockRental, RentalDto.class)).thenReturn(mockRentalDto);

        List<RentalDto> activeRentals = rentalService.getActiveRentals(1L);

        assertFalse(activeRentals.isEmpty());
    }

    @Test
    void testGetAllRentalsOfUser_Success() {
        // Setup current user
        when(authentication.getPrincipal()).thenReturn(currentUser);

        when(userRepository.existsById(1L)).thenReturn(true);
        when(rentalRepository.findByUserId(1L)).thenReturn(Arrays.asList(mockRental));
        when(modelMapper.map(mockRental, RentalDto.class)).thenReturn(mockRentalDto);

        List<RentalDto> userRentals = rentalService.getAllRentalsOfUser(1L);

        assertFalse(userRentals.isEmpty());
    }


}
