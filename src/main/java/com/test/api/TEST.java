package com.test.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.dao.BookDao;
import com.test.entities.security.User;
import com.test.repo.BookRepository;
import com.test.repo.InfoRepository;
import com.test.service.BookService;
import com.test.service.UserService;

@RestController
@RequestMapping("/api")
public class TEST {
	@Autowired
	private InfoRepository refo;
	@Autowired
	private BookService bookService;

	@GetMapping("/test")
	public ResponseEntity<?> response(@RequestParam int id) {
		return ResponseEntity.ok(bookService.newBooks().getContent());
	}
}
