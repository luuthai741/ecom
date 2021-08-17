package com.test.controller.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.entities.Genre;
import com.test.service.GenreService;

import lombok.AllArgsConstructor;

@Controller("adminGenreController")
@RequestMapping("/admin/genres")
@PreAuthorize("hasRole('ADMIN')")
public class GenreController {
	@Autowired
	private GenreService genreService;

	@GetMapping({ "/", "" })
	public String home(Model model) {
		List<Genre> genresDTO = genreService.findAll();
		model.addAttribute("genres", genresDTO);
		return "admin-pages/genres/index";
	}


	@GetMapping("/add")
	public String add(Model model) {
		Genre genre = new Genre();
		model.addAttribute("genre", genre);
		return "admin-pages/genres/form";
	}

	@PostMapping("/add-genre")
	public String add(@ModelAttribute(name = "genre") Genre genre) {
		genreService.save(genre);
		return "redirect:/admin/genres";
	}

	@GetMapping("/update/{id}")
	public String update(Model model, @PathVariable int id) {
		Genre genre = genreService.findById(id);
		model.addAttribute("genre", genre);
		return "admin-pages/genres/form";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute(name = "genre") Genre genre) {
		genreService.save(genre);
		return "redirect:/admin/genres";
	}

	@GetMapping("/delete/{id}")
	public String delele(@PathVariable int id) {
		genreService.deleteByID(id);
		return "redirect:/admin/genres";
	}
}
