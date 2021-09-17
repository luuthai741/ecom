package com.test.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.test.service.CartService;

@Controller
@RequestMapping("/admin/cart")
@PreAuthorize("hasRole('ADMIN')")
public class OrderController {
	@Autowired
	private CartService cartService;
	@GetMapping
	public String getAll(Model model) {
		model.addAttribute("listCart", cartService.findAll());
		return "admin-pages/cart/index";
	} 
	
	@GetMapping("/{id}")
	public String detail(Model model,@PathVariable int id) {
		model.addAttribute("items", cartService.findItemsByCartId(id));
		return "admin-pages/cart/detail";
	}
	
	@GetMapping("/delete/{id}")
	public String detail(@PathVariable int id) {
		cartService.deleteByID(id);
		return "admin-pages/cart/index";
	}
}
