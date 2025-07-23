package cl.tomouno.bookservice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="book")
public class Book {

    @Id
    private UUID id;

    @Column (nullable = false)
    private String title;

    @Column (nullable = false)
    private String author;

    @Column (nullable = false)
    private String isbn;

    @Column (nullable = false)
    private String description;

    @Column (nullable = false)
    private Integer pages;

    @Column (nullable = false)
    private Integer year;

    @Column (nullable = false)
    private Integer price;

    @Column (nullable = false)
    private Integer stock;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BookCategory> categories;

}
