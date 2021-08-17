package com.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.data.repository.query.Param;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.test.controller.user.BaseController;
import com.test.entities.security.User;
import com.test.mail.SendMail;
import com.test.service.UserService;
import com.test.ultil.URLUltil;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class AuthenController extends BaseController {
	private UserService userService;

	@GetMapping("/login")
	public String singIn() {
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if (authen == null || authen instanceof AnonymousAuthenticationToken) {
			return "/login";
		}
		return "redirect:/";
	}

	@GetMapping("/register")
	public String signUp(Model model) {
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if (authen == null || authen instanceof AnonymousAuthenticationToken) {
			User user = new User();
			model.addAttribute("user", user);
			return "/user-pages/authen/register";
		}
		return "redirect:/";
	}

	@PostMapping("/register")
	public String signUp(@ModelAttribute("user") User user, HttpServletRequest request) {
		String link = URLUltil.getSiteURL(request);
		userService.save(user);

		User savedUser = userService.findByUsername(user.getUsername());
		userService.sigin(savedUser.getEmail(), link);

		return "/user-pages/authen/register-success";
	}

	@GetMapping("/verify")
	public String verifyCustomer(@Param("verifyToken") String verifyToken, Model model) {
		if (userService.verify(verifyToken)) {
			return "/user-pages/authen/verify-success";
		}
		return "/user-pages/authen/verify-fail";
	}

	// FORGOT PASSWORD
	@GetMapping("/forgot-password")
	public String forgotPassword(Model model) {
		Authentication authen = SecurityContextHolder.getContext().getAuthentication();
		if (authen == null || authen instanceof AnonymousAuthenticationToken) {
			return "/user-pages/authen/forgot";
		}
		return "redirect:/";
	}

	@PostMapping("/forgot")
	public String forgot(HttpServletRequest request) {
		String email = request.getParameter("email");
		String link = URLUltil.getSiteURL(request);

		userService.forgot(email, link);
		return "/user-pages/authen/register-success";
	}

	@GetMapping("/reset")
	public String reset(@Param("resetToken") String resetToken, Model model) {
		boolean check = userService.checkResetToken(resetToken);
		if (check) {
			model.addAttribute("resetToken", resetToken);
			return "user-pages/authen/reset-form";
		}
		return "redirect:/";
	}

	@PostMapping("/reset")
	public String resetForm(HttpServletRequest request) {
		String resetToken = request.getParameter("resetToken");
		String password = request.getParameter("password");

		userService.reset(resetToken, password);
		return "redirect:/login";
	}
}
