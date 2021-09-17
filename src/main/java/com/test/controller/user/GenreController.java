package com.test.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.test.service.GenreService;

import lombok.AllArgsConstructor;

@Controller("genreController")
@AllArgsConstructor

public class GenreController extends BaseController{
	private GenreService genreService;
	@GetMapping("/genre-{id}")
	public String getBooksByGenre(Model model, @PathVariable int id) {
		model.addAttribute("books", genreService.findById(id).getBooks());
		return "user-pages/search-result";
	}
}
