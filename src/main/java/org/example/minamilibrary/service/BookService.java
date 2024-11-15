package org.example.minamilibrary.service;

import org.example.minamilibrary.model.Book;
import org.example.minamilibrary.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
	private final BookRepository bookRepository;
	
	public BookService(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}
	
	public List<Book> getAllBooks() {
		return bookRepository.findAll();
	}
	
	public Book findById(Long id) {
		return bookRepository.findById(id).orElse(null);
	}
	
	public Book createBook(Book book) {
		return bookRepository.save(book);
	}
	
	public int countAvailableBooks() {
		return bookRepository.countByAvailableTrue();
	}
}
