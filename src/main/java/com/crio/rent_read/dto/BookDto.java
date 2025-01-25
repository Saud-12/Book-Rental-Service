package com.crio.rent_read.dto;

import com.crio.rent_read.entity.enums.AvailabilityStatus;
import com.crio.rent_read.entity.enums.Genre;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Long id;
    @NotEmpty(message = "Title cannot be null or empty")
    @Size(min=4,max=30, message = "title should be in range of 4 - 30 characters")
    private String title;
    @NotEmpty(message = "author cannot be null or empty")
    @Size(min=4,max=15,message = "author should be in range of 4 - 15 characters")
    private String author;

    private Genre genre;
    private AvailabilityStatus availabilityStatus;
}
