package com.test.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.test.entities.Book;

public interface BookService extends GenericService<Book>, PageService<Book> {
	List<Book> findAllByEnabled();

	Book findByName(String name);

	List<Book> searchBookEnabled(String name);

	List<Book> searchBook(String name);
	Page<Book> newBooks();
	Page<Book> hotSales();
}
