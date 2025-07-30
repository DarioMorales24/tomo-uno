package cl.tomouno.bookservice.controller;

import cl.tomouno.bookservice.controller.response.MessageResponse;
import cl.tomouno.bookservice.dto.BookRequestDto;
import cl.tomouno.bookservice.dto.BookResponseDto;
import cl.tomouno.bookservice.model.Book;
import cl.tomouno.bookservice.model.BookCategory;
import cl.tomouno.bookservice.service.BookCategoryService;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookCategoryService bookCategoryService;

    @GetMapping("/")
    public ResponseEntity<List<BookResponseDto>> getAllBooks() {
        List<Book> books = bookCategoryService.findAll();
        List<BookResponseDto> dtoBooks = new ArrayList<>();
        for (Book book : books) {
            dtoBooks.add(mapToDto(book));
        }
        return ResponseEntity.ok(dtoBooks);
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<BookResponseDto> getBook(@PathVariable String isbn) {
        Book book = bookCategoryService.findByIsbn(isbn);
        BookResponseDto dtoBook = mapToDto(book);
        return ResponseEntity.ok(dtoBook);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> getBookById(@PathVariable UUID id) {
        Book book = bookCategoryService.findById(id);
        BookResponseDto dtoBook = mapToDto(book);
        return ResponseEntity.ok(dtoBook);
    }

    @PostMapping("/")
    public ResponseEntity<MessageResponse> saveBook(@RequestBody BookRequestDto bookRequestDto) {
        if (bookCategoryService.findByIsbn(bookRequestDto.getIsbn()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Book already exists"));
        }
        bookCategoryService.save(bookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponse("Book created"));
    }

    @PutMapping("/{isbn}")
    public ResponseEntity<MessageResponse> updateBook(@PathVariable String isbn, @RequestBody BookRequestDto bookRequestDto) {
        if (bookCategoryService.findByIsbn(isbn) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Book not found"));
        }
        bookCategoryService.PartialUpdateByIsbn(isbn, bookRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Book updated"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateBookById(@PathVariable UUID id, @RequestBody BookRequestDto bookRequestDto) {
        if (bookCategoryService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Book not found"));
        }
        bookCategoryService.partialUpdateById(id, bookRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Book updated"));
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<MessageResponse> deleteBook(@PathVariable String isbn) {
        if (bookCategoryService.findByIsbn(isbn) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Book not found"));
        }
        bookCategoryService.deleteByIsbn(isbn);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Book deleted"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteBookbyId(@PathVariable UUID id) {
        if (bookCategoryService.findById(id) == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new MessageResponse("Book not found"));
        }
        bookCategoryService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).body(new MessageResponse("Book deleted"));
    }

    // Metodo auxiliar para transformar de Book -> BookResponseDto

    private BookResponseDto mapToDto(Book book) {
        List<String> categoryNames = book.getCategories().stream().map(BookCategory::getName).toList();

        return new BookResponseDto(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getDescription(),
                book.getPages(),
                book.getYear(),
                book.getPrice(),
                book.getStock(),
                categoryNames
        );
    }
}
