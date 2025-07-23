package cl.tomouno.bookservice.repository;

import cl.tomouno.bookservice.model.BookCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookCategoryRepository extends JpaRepository<BookCategory, UUID> {
    boolean existsByName(String name);
    BookCategory findByName(String name);
}
