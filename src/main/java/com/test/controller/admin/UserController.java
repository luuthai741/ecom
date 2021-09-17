package com.test.controller.admin;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.entities.UserInfo;
import com.test.entities.security.User;
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
	public String home(Model model, @PathVariable int id) {
		User user = userService.findById(id);
		UserInfo info = user.getInfo();
		if (info != null) {
			model.addAttribute("user", user);
			model.addAttribute("info", info);
			return "admin-pages/users/detail";
		} else {
			return "admin-pages/users/null";
		}
	}

	@GetMapping("/delete/{id}")
	public String delete(@PathVariable int id) {
		userService.deleteByID(id);
		return "redirect:/admin/users";
	}
}
