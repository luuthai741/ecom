package com.test.controller.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

import com.test.entities.Genre;
import com.test.service.GenreService;

@Controller
public class BaseController {
	@Autowired
	private GenreService genreService;

	@ModelAttribute("genres")
	public List<Genre> menus() {
		return genreService.findAll();
	}
}
