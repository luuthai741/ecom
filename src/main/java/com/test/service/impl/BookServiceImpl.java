package com.test.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.test.dao.BookDao;
import com.test.entities.Book;
import com.test.repo.BookRepository;
import com.test.service.BookService;

import lombok.AllArgsConstructor;

@Service
@Transactional
public class BookServiceImpl implements BookService {
	private static final Logger log = LoggerFactory.getLogger(BookServiceImpl.class);
	@Autowired
	private BookRepository bookRepo;

	@Cacheable("books")
	@Override
	public List<Book> findAll() {
		log.debug("Fetch book data");
		return bookRepo.findAll();
	}

	@Cacheable("bookByID")
	@Override
	public Book findById(int id) {
		log.debug("Fetch book data by id {}", id);
		return bookRepo.findById(id).orElse(null);
	}

	@Override
	public void save(Book t) {
		log.debug("Save book {}", t);
		bookRepo.save(t);
	}

	@CacheEvict("bookByID")
	@Override
	public void deleteByID(int id) {
		bookRepo.deleteById(id);
	}

	@Cacheable("booksEnabled")
	@Override
	public List<Book> findAllByEnabled() {
		return bookRepo.findAllByEnabled();
	}

	@Override
	public Book findByName(String name) {
		return bookRepo.findByName(name);
	}

	@Override
	public List<Book> searchBookEnabled(String name) {
		return bookRepo.searchBookEnabled(name);
	}

	@Override
	public List<Book> searchBook(String name) {
		return bookRepo.searchBook(name);
	}

	@Cacheable("booksPage")
	@Override
	public Page<Book> paging(int pageNumber) {
		Sort sort = Sort.by("name").ascending();
		Pageable pageable = PageRequest.of(pageNumber - 1, 5, sort);
		return bookRepo.findAll(pageable);
	}

	@Cacheable("newBooks")
	@Override
	public Page<Book> newBooks() {
		Sort sort = Sort.by("createAt").descending();
		Pageable pageable = PageRequest.of(0, 4, sort);
		return bookRepo.findAll(pageable);
	}

	@Override
	public Page<Book> hotSales() {
		Sort sort = Sort.by("quantity").ascending();
		Pageable pageable = PageRequest.of(0, 4, sort);
		return bookRepo.findAll(pageable);
	}

}
