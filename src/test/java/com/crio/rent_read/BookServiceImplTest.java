package com.crio.rent_read;


import com.crio.rent_read.dto.BookDto;
import com.crio.rent_read.entity.Book;
import com.crio.rent_read.entity.enums.AvailabilityStatus;
import com.crio.rent_read.entity.enums.Genre;
import com.crio.rent_read.exception.ResourceNotFoundException;
import com.crio.rent_read.repository.BookRepository;
import com.crio.rent_read.service.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    private Book mockBook;
    private BookDto mockBookDto;

    @BeforeEach
    void setUp() {
        mockBook = new Book();
        mockBook.setId(1L);
        mockBook.setTitle("Test Book");
        mockBook.setAuthor("Test Author");
        mockBook.setGenre(Genre.FICTION);
        mockBook.setAvailabilityStatus(AvailabilityStatus.AVAILABLE);

        mockBookDto = new BookDto();
        mockBookDto.setId(1L);
        mockBookDto.setTitle("Test Book");
        mockBookDto.setAuthor("Test Author");
        mockBookDto.setGenre(Genre.FICTION);
    }

    @Test
    void testCreateNewBook_Success() {
        when(modelMapper.map(mockBookDto, Book.class)).thenReturn(mockBook);
        when(bookRepository.save(mockBook)).thenReturn(mockBook);
        when(modelMapper.map(mockBook, BookDto.class)).thenReturn(mockBookDto);

        BookDto createdBook = bookService.createNewBook(mockBookDto);

        assertNotNull(createdBook);
        assertEquals(mockBookDto.getId(), createdBook.getId());
        assertEquals(AvailabilityStatus.AVAILABLE, mockBook.getAvailabilityStatus());
    }

    @Test
    void testGetBookById_Success() {
        when(bookRepository.findById(1L)).thenReturn(Optional.of(mockBook));
        when(modelMapper.map(mockBook, BookDto.class)).thenReturn(mockBookDto);

        BookDto retrievedBook = bookService.getBookById(1L);

        assertNotNull(retrievedBook);
        assertEquals(mockBookDto.getId(), retrievedBook.getId());
    }

    @Test
    void testGetBookById_NotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.getBookById(1L));
    }

    @Test
    void testGetAllBooks_Success() {
        when(bookRepository.findAll()).thenReturn(Arrays.asList(mockBook));
        when(modelMapper.map(mockBook, BookDto.class)).thenReturn(mockBookDto);

        List<BookDto> books = bookService.getAllBooks();

        assertFalse(books.isEmpty());
        assertEquals(1, books.size());
    }

    @Test
    void testDeleteBookById_Success() {
        when(bookRepository.existsById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> bookService.deleteBookById(1L));
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void testDeleteBookById_NotFound() {
        when(bookRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class,
                () -> bookService.deleteBookById(1L));
    }
}
