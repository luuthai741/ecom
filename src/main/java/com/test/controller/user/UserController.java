package com.test.controller.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.entities.UserInfo;
import com.test.entities.security.User;
import com.test.repo.InfoRepository;
import com.test.repo.UserRepository;
import com.test.security.UserPrinciple;
import com.test.service.UserService;

import lombok.AllArgsConstructor;

@Controller("userController")
@PreAuthorize("hasRole('USER')")
@RequestMapping("/info")
public class UserController extends BaseController {
	
	private final static Logger log = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private InfoRepository infoRepo;
	@Autowired
	private UserRepository userRepo;

	@GetMapping
	public String getInfo(Model model) {
		UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userPrinciple.getUser();
		UserInfo info = infoRepo.findByUser(user).orElse(null);
		if (info != null) {
			return "redirect:/info/update?id=" + info.getId();
		} else {
			return "redirect:/info/add";
		}
	}
	@GetMapping("/add")
	public String addInfo(Model model) {
		UserInfo info = new UserInfo();
		model.addAttribute("info", info);
		return "user-pages/user-info/form";
	}

	@PostMapping("/add")
	public String add(@ModelAttribute(name = "info") UserInfo info) {
		UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		int id = userPrinciple.getId();
		User user = userRepo.findById(id).orElse(null);
		info.setUser(user);
		infoRepo.save(info);
		return "redirect:/";
	}

	@GetMapping("/update")
	public String update(Model model, @RequestParam int id) {
		UserInfo info = infoRepo.findById(id).orElse(null);
		if (info != null) {
			model.addAttribute("info", info);
			return "user-pages/user-info/form";
		}
		return "redirect:/info/add";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute(name = "info") UserInfo info) {
		UserPrinciple userPrinciple = (UserPrinciple) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		User user = userPrinciple.getUser();
		info.setUser(user);
		infoRepo.save(info);

		Authentication authen = new UsernamePasswordAuthenticationToken(userPrinciple, user.getUsername(),
				userPrinciple.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authen);

		return "redirect:/";
	}
}
