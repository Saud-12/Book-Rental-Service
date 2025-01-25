package com.crio.rent_read.service;

import com.crio.rent_read.dto.BookDto;

import java.util.List;

public interface BookService {

    BookDto createNewBook(BookDto bookDto);
    BookDto getBookById(Long id);
    List<BookDto> getAllBooks();
    BookDto updateBookById(Long id,BookDto bookDto);
    void deleteBookById(Long id);

}
