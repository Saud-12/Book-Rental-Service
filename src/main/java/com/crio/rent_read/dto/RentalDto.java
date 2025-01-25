package com.crio.rent_read.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalDto {
    private Long id;
    private BookDto book;
    private LocalDate rentedAt;
    private LocalDate returnDate;
}
