package com.example.demo.controller;

import com.example.demo.entity.Book;
import com.example.demo.service.BookService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;
    
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // Feature: Add book (Returns 201 CREATED)
    @PostMapping
    public ResponseEntity<Book> createBook(@Valid @RequestBody Book book) {
        Book savedBook = bookService.saveBook(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // Feature: View books (Handles All, Author, and Category searches)
    @GetMapping
    public ResponseEntity<List<Book>> getBooks(@RequestParam(required = false) String author,
                                               @RequestParam(required = false) String category) {
        List<Book> books;
        
        if (author != null) {
            books = bookService.getBooksByAuthor(author);
        } else if (category != null) {
             books = bookService.getBooksByCategory(category);
        } else {
            books = bookService.getAllBooks();
        }
        
        // Always returns 200 OK, even if the list is empty
        return ResponseEntity.ok(books); 
    }
    
    // Feature: Update stock (Returns 200 OK if successful)
    @PutMapping("/{id}/stock")
    public ResponseEntity<Book> updateBookStock(@PathVariable Long id, 
                                                @RequestParam int quantity) {
        // BookNotFoundException is thrown by service and caught by @ControllerAdvice
        Book updatedBook = bookService.updateStock(id, quantity);
        return ResponseEntity.ok(updatedBook);
    }
    
    // Feature: Delete book (Returns 204 NO CONTENT)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        // Best practice for successful delete: 204 No Content
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); 
    }
}