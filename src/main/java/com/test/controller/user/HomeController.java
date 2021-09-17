package com.test.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.entities.Book;
import com.test.service.BookService;

@Controller("userHomeController")
@RequestMapping("/")
public class HomeController extends BaseController {
	@Autowired
	private BookService bookService;

	@GetMapping
	public String home(Model model) {
		Page<Book> newBooks = bookService.newBooks();	
		Page<Book> hotBooks = bookService.hotSales();
		model.addAttribute("newBooks",newBooks.getContent());
		model.addAttribute("hotBooks",hotBooks.getContent());
		return "user-pages/home";
	}

	@RequestMapping("/search")
	public String search(HttpServletRequest request, Model model) {
		String name = request.getParameter("name");
		String message;
		boolean check;
		if (name != "") {
			message = "Danh sách tìm được";
			check = true;
			model.addAttribute("books", bookService.searchBookEnabled(name));
			model.addAttribute("message", message);
			model.addAttribute("check", check);
			return "user-pages/search-result";
		} else {
			message = "Hãy nhập tên cuốn sách cần tìm";
			check = false;
			model.addAttribute("message", message);
			model.addAttribute("check", check);
			return "user-pages/search-result";
		}
	}
}
