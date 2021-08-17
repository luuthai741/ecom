package com.test.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.test.entities.security.User;
import com.test.repo.InfoRepository;
import com.test.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class TEST {
	@Autowired
	private InfoRepository refo;
	@Autowired
	private UserService userService;

	@GetMapping("/test")
	public ResponseEntity<?> response(@RequestParam int id) {
		User user = userService.findById(id);
		return ResponseEntity.ok(refo.findByUser(user));
	}
}
