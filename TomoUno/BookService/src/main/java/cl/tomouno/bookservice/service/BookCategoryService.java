package cl.tomouno.bookservice.service;

import cl.tomouno.bookservice.dto.BookRequestDto;
import cl.tomouno.bookservice.model.Book;
import cl.tomouno.bookservice.model.BookCategory;
import cl.tomouno.bookservice.repository.BookCategoryRepository;
import cl.tomouno.bookservice.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class BookCategoryService {

    @Autowired
    private final BookRepository bookRepository;

    public BookCategoryService(BookRepository bookRepository, BookCategoryRepository bookCategoryRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    public Book findById(UUID id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.orElse(null);
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

    public void update(UUID id, BookRequestDto dto) {
        Book bookExists = findById(id);

        bookExists.setTitle(dto.getTitle());
        bookExists.setAuthor(dto.getAuthor());
        bookExists.setIsbn(dto.getIsbn());
        bookExists.setDescription(dto.getDescription());
        bookExists.setPages(dto.getPages());
        bookExists.setPrice(dto.getPrice());
        bookExists.setStock(dto.getStock());

        bookRepository.save(bookExists);
    }

    public void partialUpdate(UUID id,BookRequestDto dto) {
        Book bookExists = findById(id);

        if (dto.getTitle() != null) {
            bookExists.setTitle(dto.getTitle());
        }
        if (dto.getAuthor() != null) {
            bookExists.setAuthor(dto.getAuthor());
        }
        if (dto.getIsbn() != null) {
            bookExists.setIsbn(dto.getIsbn());
        }
        if (dto.getDescription() != null) {
            bookExists.setDescription(dto.getDescription());
        }
        if (dto.getPages() != null) {
            bookExists.setPages(dto.getPages());
        }
        if (dto.getPrice() != null) {
            bookExists.setPrice(dto.getPrice());
        }
        if (dto.getStock() != null) {
            bookExists.setStock(dto.getStock());
        }
        bookRepository.save(bookExists);
    }

    public void delete(UUID id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            bookRepository.delete(book.get());
        }
    }
}
