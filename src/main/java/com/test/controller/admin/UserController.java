package com.test.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.service.UserService;

import lombok.AllArgsConstructor;

@Controller("adminUserController")
@RequestMapping("/admin/users")
@AllArgsConstructor
public class UserController {
	private UserService userService;
	
	@GetMapping
	public String home(Model model) {
		model.addAttribute("users", userService.findAll());
		return "admin-pages/users/index";
	}
	@GetMapping("/{id}")
	public String home(Model model,@PathVariable int id) {
		model.addAttribute("user", userService.findById(id));
		return "admin-pages/users/detail";
	}
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		userService.deleteByID(id);
		return "redirect:/admin/users";
	}
}
