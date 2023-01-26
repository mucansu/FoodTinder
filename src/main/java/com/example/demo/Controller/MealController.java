package com.example.demo.Controller;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.Profile;
import com.example.demo.Service.MealService;
import com.example.demo.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;

@Controller
public class MealController {
    @Autowired
    private MealService mealService;
    @Autowired
    private ProfileService profileService;

    @GetMapping("/main/")
    public String mainPage(@ModelAttribute Meal meal, Model model){
        Profile profile = new Profile();
       // Profile profileId = profileService.findById(id);
     //   Meal mealId = mealService.findById(id);
        model.addAttribute("profile", profile);
        model.addAttribute("meal",meal);
      //  model.addAttribute("profile",profileId);
        //model.addAttribute("meal",mealId);
        return "main";
    }


 /*   @GetMapping("/main/{id}")
    public String mainPage1(Model model, @PathVariable Long id){
        Profile profile = new Profile();
        Meal mealId = mealService.findById(id);
        model.addAttribute("meal",mealId);
        model.addAttribute("profile", profile);
        return "main";
    }

  */
}
