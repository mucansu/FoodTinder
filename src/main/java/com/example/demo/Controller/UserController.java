package com.example.demo.Controller;

import com.example.demo.Entities.Profile;
import com.example.demo.Entities.User;
import com.example.demo.Repository.MealRepository;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
	@Autowired
	private MealRepository mealRepository;
	@Autowired
	private UserService userService;


	@GetMapping("/")
	public String loginPage(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "login";
	}

	@PostMapping("/login")
	public String register(@ModelAttribute User user, Model model) {
		userService.addUser(user);
		model.addAttribute("user", user);
		return "home";
	}

}
