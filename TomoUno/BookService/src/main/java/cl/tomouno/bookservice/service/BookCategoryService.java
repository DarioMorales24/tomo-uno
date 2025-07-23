package cl.tomouno.bookservice.service;

import cl.tomouno.bookservice.dto.BookRequestDto;
import cl.tomouno.bookservice.model.Book;
import cl.tomouno.bookservice.model.BookCategory;
import cl.tomouno.bookservice.repository.BookCategoryRepository;
import cl.tomouno.bookservice.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookCategoryService {

    private final BookRepository bookRepository;

    public BookCategoryService(BookRepository bookRepository, BookCategoryRepository bookCategoryRepository) {
        this.bookRepository = bookRepository;
    }

    public void save(BookRequestDto dto) {
        Book book = new Book();
        book.setId(UUID.randomUUID());
        book.setTitle(dto.getTitle());
        book.setAuthor(dto.getAuthor());
        book.setIsbn(dto.getIsbn());
        book.setDescription(dto.getDescription());
        book.setPages(dto.getPages());
        book.setPrice(dto.getPrice());
        book.setStock(dto.getStock());

        List<BookCategory> categoryList = dto.getCategories().stream().map(name -> new BookCategory(UUID.randomUUID(), book, name)).toList();

        book.setCategories(categoryList);

        bookRepository.save(book);
    }
}
