package com.crio.rent_read.service;

import com.crio.rent_read.dto.BookDto;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.User;
import com.crio.rent_read.entity.enums.AvailabilityStatus;
import com.crio.rent_read.exception.ResourceNotFoundException;
import com.crio.rent_read.repository.BookRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService{

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto createNewBook(BookDto bookDto) {
        Book book=modelMapper.map(bookDto, Book.class);
        book.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);
        return modelMapper.map(bookRepository.save(book),BookDto.class);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public BookDto getBookById(Long id) {
        Book book=bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Book with ID: "+id+" does not exists!"));
        return modelMapper.map(book,BookDto.class);
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    public List<BookDto> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book->modelMapper.map(book,BookDto.class))
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public BookDto updateBookById(Long id, BookDto bookDto) {
        Book book=bookRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Book with ID: "+id+" does not exists!"));
        modelMapper.map(bookDto,book);
        return modelMapper.map(bookRepository.save(book),BookDto.class);
    }

    @Override
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteBookById(Long id) {
        if(!bookRepository.existsById(id)){
            throw new ResourceNotFoundException("Book with ID: "+id+" does not exists!");
        }
        bookRepository.deleteById(id);
    }
    private User getCurrentUser(){
        return (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
