package cl.tomouno.bookservice.repository;

import cl.tomouno.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    boolean existsByTitle(String title);
    Book findByIsbn(String isbn);

}
