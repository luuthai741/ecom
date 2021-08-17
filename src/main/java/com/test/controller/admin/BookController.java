package com.test.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.test.entities.Book;
import com.test.service.BookService;
import com.test.service.GenreService;
import com.test.ultil.UploadFileUtil;

import lombok.AllArgsConstructor;

@Controller("adminBookController")
@RequestMapping("/admin/books")
@PreAuthorize("hasRole('ADMIN')")

public class BookController {

	private BookService bookService;
	private GenreService genreService;
	private UploadFileUtil uploadFile;

	@Autowired
	public BookController(BookService bookService, GenreService genreService, UploadFileUtil uploadFile) {
		super();
		this.bookService = bookService;
		this.genreService = genreService;
		this.uploadFile = uploadFile;
	}

	@GetMapping("/page={currentPage}")
	public String page(Model model, @PathVariable("currentPage") int currentPage) {
		Page<Book> page = bookService.paging(currentPage);
		List<Book> books = page.getContent();
		model.addAttribute("books", books);
		model.addAttribute("page", currentPage);
		model.addAttribute("totalItem", page.getTotalElements());
		model.addAttribute("totalPage", page.getTotalPages());
		return "admin-pages/books/index";
	}

	@GetMapping("/add")
	public String add(Model model) {
		Book book = new Book();

		model.addAttribute("book", book);
		model.addAttribute("genres", genreService.findAll());
		return "admin-pages/books/form";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute(name = "book") Book book,
			@RequestParam("fileImage") MultipartFile multipartFile) {
		String originFileName = multipartFile.getOriginalFilename();
		String fileName = StringUtils.cleanPath(originFileName);
		book.setImage(fileName);
		bookService.save(book);
		String uploadDir = "./book-images/";
		uploadFile.saveFile(uploadDir, multipartFile, fileName);

		return "redirect:/admin/books/page=1";
	}

	@GetMapping("/update/{id}")
	public String update(Model model, @PathVariable int id) {
		Book book = bookService.findById(id);
		model.addAttribute("book", book);
		model.addAttribute("genres", genreService.findAll());
		return "admin-pages/books/form";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute(name = "book") Book book,
			@RequestParam("fileImage") MultipartFile multipartFile) {
		String originFileName = multipartFile.getOriginalFilename();
		String fileName = StringUtils.cleanPath(originFileName);
		book.setImage(fileName);
		bookService.save(book);
		String uploadDir = "./book-images/";
		uploadFile.saveFile(uploadDir, multipartFile, fileName);
		return "redirect:/admin/books/page=1";
	}

	@GetMapping("/delete/{id}")
	public String delete(Model model, @PathVariable int id) {
		bookService.deleteByID(id);
		return "redirect:/admin/books/page=1";
	}
}
