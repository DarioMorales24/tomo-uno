package cl.tomouno.bookservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookResponseDto {
    private UUID id;
    private String title;
    private String author;
    private String isbn;
    private String description;
    private Integer pages;
    private Integer year;
    private Integer price;
    private Integer stock;
    private List<String> categories;
}
