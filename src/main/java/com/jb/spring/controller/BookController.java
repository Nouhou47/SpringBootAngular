package com.jb.spring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
}








