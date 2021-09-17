package com.test.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.dto.CartManager;
import com.test.dto.CartRequest;
import com.test.dto.CustomerRequest;
import com.test.entities.Book;
import com.test.entities.UserInfo;
import com.test.entities.security.User;
import com.test.security.UserPrinciple;
import com.test.service.BookService;
import com.test.service.CartService;
import com.test.ultil.URLUltil;

@Controller("userShoppingCart")
@RequestMapping("/cart")
public class ShoppingCartController extends BaseController {
	private Logger log = LoggerFactory.getLogger(ShoppingCartController.class);
	@Autowired
	private BookService bookService;
	@Autowired
	private CartService cartService;
	@Autowired
	private HttpSession session;

	@GetMapping
	public String cart(Model model) {
		@SuppressWarnings("unchecked")
		List<CartRequest> listItems = (List<CartRequest>) session.getAttribute(CartManager.KEY);
		model.addAttribute("listItems", listItems);
		return "user-pages/cart/index";
	}

	@GetMapping("/add")
	public String addToCart(@RequestParam int bookId, @RequestParam int quantity) {
		Book book = bookService.findById(bookId);
		if (book != null) {
			var cart = new CartManager(session);
			cart.add(book, quantity);
			log.info("Cart{}", cart);
		}
		return "redirect:/cart";
	}

	@GetMapping("/delete")
	public String removeFromCart(@RequestParam int bookId) {
		Book book = bookService.findById(bookId);
		log.info("Book data {}", book);
		if (book != null) {
			var cart = new CartManager(session);
			cart.remove(bookId);
			log.info("Cart {}", cart);
		}
		return "redirect:/cart";
	}

	@GetMapping("/clear")
	public String clear() {
		var cart = new CartManager(session);
		cart.clearItems();
		log.info("Cart {}", cart);
		return "redirect:/cart";
	}

	@PostMapping("/check-out")
	public String checkOut(Model model, HttpServletRequest request) {
		String link = URLUltil.getSiteURL(request);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
			return "redirect:/cart/customer";
		} else {
			UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
			User user = userPrinciple.getUser();
			UserInfo info = user.getInfo();
			if (info == null) {
				return "/info/add";
			} else {
				@SuppressWarnings("unchecked")
				List<CartRequest> listItems = (List<CartRequest>) session.getAttribute(CartManager.KEY);
				cartService.createOrderForUser(user, listItems, link);
				var cart = new CartManager(session);
				cart.clearItems();
			}
			return "redirect:/";
		}
	}

	@GetMapping("/customer")
	public String createCustomer(Model model) {
		CustomerRequest customer = new CustomerRequest();
		model.addAttribute("customer", customer);
		return "user-pages/cart/customer-form";
	}

	@PostMapping("/customer")
	public String createCustomer(@ModelAttribute(value = "customer") CustomerRequest customer,
			HttpServletRequest request) {
		String link = URLUltil.getSiteURL(request);
		var cart = new CartManager(session);
		@SuppressWarnings("unchecked")
		List<CartRequest> listItems = (List<CartRequest>) session.getAttribute(CartManager.KEY);
		cartService.createOrderForCustomer(customer, listItems, link);
		cart.clearItems();
		return "redirect:/";
	}
	
	@GetMapping("/check-out")
	public String verifyCustomer(@Param("cofirmToken") String cofirmToken) {
		cartService.enabled(cofirmToken);
		return "user-pages/cart/success";	
	}
}
