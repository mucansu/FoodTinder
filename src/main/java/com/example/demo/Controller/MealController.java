package com.example.demo.Controller;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.Profile;
import com.example.demo.Repository.MealRepository;
import com.example.demo.Service.MealService;
import com.example.demo.Service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MealController {
    @Autowired
    private MealService mealService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private MealRepository mealRepository;


    @GetMapping("/main/{id}")
    public String mainPage(Model model, @PathVariable Long id,
                           @RequestParam(required = false, defaultValue = "0") Integer index,
                           @RequestParam(required = false, defaultValue = "default") String choice,
                           HttpSession session) {
        Profile profile = (Profile) session.getAttribute("profile");
        if (profile==null || profile.getId()!=id){//kolla om gamla profilen är kvar
            profile = profileService.findById(id);
            session.setAttribute("profile",profile);
        }
//        model.addAttribute("profile", profile);

        List<Meal> mealList = mealService.findAll();
        if (index >= mealList.size()){
            List<Profile> profiles = (List<Profile>) session.getAttribute("profileList");
            if (profiles==null){
                profiles = new ArrayList<>();
            }
            profiles.add(profile);
            model.addAttribute("yesMealList", profile.getSessionMealList());
            return "result";
        }
        Meal meal = mealList.get(index);//Hämtar index på måltid

        model.addAttribute("mealList", mealList);
        model.addAttribute("meal", meal);
        model.addAttribute("mealIndex", index+1);//Tar nästa måltid
        if (choice.equals("yes")){
//            meal.getProfiles().add(profile);
//            mealService.addMeal(meal);
            profile.getSessionMealList().add(meal);//Om choice yes läggs måltiden i listan i profiles matlista.

        }

        return "main";
    }


}
