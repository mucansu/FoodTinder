package com.example.demo.Controller;

import com.example.demo.Entities.Profile;
import com.example.demo.Entities.User;
import com.example.demo.Service.ProfileService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class ProfileController {
	@Autowired
	private ProfileService profileService;
	@Autowired
	private UserService userService;

	@GetMapping("/profile/{id}")
	String profilePage(Model model, @PathVariable Long id) {
		User user = userService.findById(id);
		model.addAttribute("user", user);
		return "profile";
	}

	@GetMapping("/addProfile")
	public String createProfile(Model model, @PathVariable Long id) {
		User userId = userService.findById(id);
		Profile profile = new Profile();
		profile.setUser(userId);
		model.addAttribute("user", userId);
		model.addAttribute("profile", profile);
		return "profile";
	}

	@PostMapping("/saveProfile")
	public String saveProfile(@ModelAttribute Profile profile) {
		profileService.addProfile(profile);
		return "redirect:/profile/" + profile.getUser().getId();
	}




}

