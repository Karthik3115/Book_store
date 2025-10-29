package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.repository.BookRepository;
import com.example.demo.exception.BookNotFoundException;

import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookService {
    
    private final BookRepository bookRepository;
    
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
    
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
    
    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
    
    public List<Book> getBooksByCategory(String category) {
        return bookRepository.findByCategoryContainingIgnoreCase(category);
    }
    
    public Book updateStock(Long id, int quantity) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + id));
        book.setStockQuantity(quantity);
        return bookRepository.save(book);
    }
    
    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
}