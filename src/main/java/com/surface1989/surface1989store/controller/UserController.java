package com.surface1989.surface1989store.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.surface1989.surface1989store.entity.User;
import com.surface1989.surface1989store.service.CustomUserDetailsService;
import com.surface1989.surface1989store.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	@GetMapping("/register")
	public String register(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "admin/register";
	}

	@PostMapping("/register")
	public String register(@Valid User user, BindingResult rs, Model model) {
		if (rs.hasErrors()) {
			model.addAttribute("user", user);
			return "admin/register";
		}
		if(!user.getConfirmPassword().trim().equals(user.getPassword().trim())){
			model.addAttribute("user", user);
			rs.rejectValue("confirmPassword", null, "Password not match!");
			return "admin/register";
		}
		User usr = userService.findByEmail(user.getEmail().trim());
		if(usr != null) {
			rs.rejectValue("email", null, "There is already an account registered with that email");
			model.addAttribute("user", user);
			return "admin/register";
		}
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encoderPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encoderPassword);
		userService.UserRegister(user);
		return "redirect:/login";
	}

	@GetMapping("/login")
	public String login(@RequestParam(name="error", required = false) String error, Model model) {
		if(error != null) {
			model.addAttribute("error", error);
		}
		return "admin/login";
	}

	@GetMapping("/admin/users")
	public String users(@RequestParam(name = "page", required = false, defaultValue = "0") int page, Model model) {
		Page<User> users = userService.getAllUsers(page);
		model.addAttribute("users", users);
		return "admin/listUsers";
	}

}
