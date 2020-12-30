package com.jb.spring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jb.spring.model.Book;
import com.jb.spring.repository.BookRepository;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class BookController {

	@Autowired
	BookRepository bookRepository;
	
	@GetMapping("/books")
	public ResponseEntity<List<Book>> getAllBooks(@RequestParam(required = false) String name) {
		try {
			List<Book> books = new ArrayList<Book>();
			
			if (name == null) {
				bookRepository.findAll().forEach(books::add);
			}else {
				bookRepository.findByNameContaining(name).forEach(books::add);
			}
			
			if (books.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			
			return new ResponseEntity<>(books, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/books/{id}")
	public ResponseEntity<Book> getBookById(@PathVariable(name = "id") long id) {
		 Optional<Book> bookData = bookRepository.findById(id);
		 
		 if (bookData.isPresent()) {
			 return new ResponseEntity<>(bookData.get(), HttpStatus.OK);	 
		 }else {
			 return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		 }
	}
	
	@PostMapping("/books")
	public ResponseEntity<Book> createBook(@RequestBody Book b) {
		try {
			Book _book = bookRepository.save(new Book(b.getName(), b.getDescription(), false));
			return new ResponseEntity<>(_book, HttpStatus.CREATED);
		}catch(Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	@PutMapping("/books/{id}")
	public ResponseEntity<Book> updateBook (@PathVariable("id") long id, @RequestBody Book b) {
		Optional<Book> bookData = bookRepository.findById(id);
		
		if (bookData.isPresent()) {
			Book _book = bookData.get();
			_book.setName(b.getName());
			_book.setDescription(b.getDescription());
			_book.setSold(b.isSold());
			
			return new ResponseEntity<>(bookRepository.save(_book), HttpStatus.OK);
		}else {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping("/books/{id}")
	public ResponseEntity<Book> deleteBook (@PathVariable("id") long id) {
		try {
			bookRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/books")
	public ResponseEntity<Book> deleteAllBooks (@PathVariable("id") long id) {
		try {
			bookRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/books/sold")
	public ResponseEntity<List<Book>> findBySold() {
		try {
			List<Book> books = bookRepository.findBySold(true);
			
			if (books.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(books, HttpStatus.OK);
		}catch(Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}








