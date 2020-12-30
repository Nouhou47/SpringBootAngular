package com.jb.spring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jb.spring.model.Book;

public interface BookRepository extends JpaRepository<Book,Long>{
	List<Book> findBySold(boolean sold);
	List<Book> findByNameContaining(String name);
}
