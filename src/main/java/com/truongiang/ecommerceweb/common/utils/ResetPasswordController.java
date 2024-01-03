package com.truongiang.ecommerceweb.common.utils;

import com.truongiang.ecommerceweb.model.User;
import com.truongiang.ecommerceweb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/forgot-password")
public class ResetPasswordController {

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@GetMapping("{token}")
	public String resetPassword(Model map, @PathVariable("token") String token) {

		User user = this.userService.findUserByToken(token);
		if (user == null) {

			return "redirect:/forgot-password/error";
		}

		map.addAttribute("name", user.getName());
		map.addAttribute("email", user.getEmail());
		map.addAttribute("token", token);
		map.addAttribute("oldPassword", "");
		map.addAttribute("newPassword", "");
		map.addAttribute("confirm", "");

		return "/reset-password";

	}

	@PostMapping
	public String reset(@RequestParam("newPassword") String newPassword, @RequestParam("oldPassword") String oldPassword,  @RequestParam("confirm") String confirm,
			@RequestParam("email") String email, @RequestParam("name") String name, @RequestParam("token") String token,
			Model map) {

		User user = this.userService.findUserByToken(token);
		if (!passwordEncoder.matches(oldPassword, user.getPassword())) {

			map.addAttribute("oldPassword", "");
			map.addAttribute("newPassword", newPassword);
			map.addAttribute("confirm", confirm);
			map.addAttribute("incorrectPassword", "error");
			map.addAttribute("name", name);
			map.addAttribute("email", email);
			map.addAttribute("token", token);

			return "/reset-password";
		} else {

			if (newPassword.length() < 6) {

				map.addAttribute("oldPassword", oldPassword);
				map.addAttribute("newPassword", "");
				map.addAttribute("confirm", "");
				map.addAttribute("errorPassword", "error");
				map.addAttribute("name", name);
				map.addAttribute("email", email);
				map.addAttribute("token", token);

				return "/reset-password";
			}

			if (newPassword.equals(oldPassword)) {

				map.addAttribute("oldPassword", oldPassword);
				map.addAttribute("newPassword", "");
				map.addAttribute("confirm", "");
				map.addAttribute("existedPassword", "error");
				map.addAttribute("name", name);
				map.addAttribute("email", email);
				map.addAttribute("token", token);

				return "/reset-password";
			}

			if (!newPassword.equals(confirm)) {

				map.addAttribute("oldPassword", oldPassword);
				map.addAttribute("errorConfirm", "error");
				map.addAttribute("name", name);
				map.addAttribute("email", email);
				map.addAttribute("newPassword", newPassword);
				map.addAttribute("confirm", "");
				map.addAttribute("token", token);

				return "/reset-password";
			}

			user = this.userService.findUserByToken(token);
			user.setPassword(this.passwordEncoder.encode(newPassword));
			user.setStatus(true);
			this.userService.saveUser(user);

			return "redirect:/forgot-password/done";
		}

	}

	@GetMapping("/done")
	public String done() {
		return "/done";
	}

	@GetMapping("/error")
	public String error() {
		return "/error";
	}

}