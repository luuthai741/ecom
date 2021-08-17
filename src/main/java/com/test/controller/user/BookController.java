package com.test.controller.user;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.entities.Book;
import com.test.service.BookService;
import lombok.AllArgsConstructor;

@Controller("userBookController")
@RequestMapping("/books")
@AllArgsConstructor
public class BookController extends BaseController {
	private BookService bookService;

//	@GetMapping
//	public String home(Model model) {
//		model.addAttribute("books", bookService.findAllByEnabled());
//		return "user-pages/books/index";
//	}

	@GetMapping("/page={currentPage}")
	public String page(Model model, @PathVariable("currentPage") int currentPage) {
		Page<Book> page = bookService.paging(currentPage);
		List<Book> books = page.getContent().stream().filter(x-> x.isEnabled()).collect(Collectors.toList());
		model.addAttribute("books", books);
		model.addAttribute("page", currentPage);
		model.addAttribute("totalItem", page.getTotalElements());
		model.addAttribute("totalPage", page.getTotalPages());
		return "user-pages/books/index";
	}

	@GetMapping("/{id}")
	public String detail(Model model, @PathVariable int id) {
		model.addAttribute("book", bookService.findById(id));
		return "user-pages/books/detail";
	}
}
