package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
@Autowired
private MealRepository mealRepository;
@Autowired
private UserRepository userRepository;

@GetMapping("/")
public String loginPage(){
    return "login";
}
}
