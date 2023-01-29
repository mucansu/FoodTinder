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
import java.util.*;
import java.util.stream.Collectors;

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

		//Kollar vilken profil som är inloggad
		Profile profile = (Profile) session.getAttribute("profile");
		if (profile == null || profile.getId() != id) {//kolla om gamla profilen är kvar
			profile = profileService.findById(id);
			session.setAttribute("profile", profile);
		}


		List<Meal> mealList = mealService.findAll(); //Tar alla måltider från databas.

		//När mealList.size är nådd ska resultat visas och profiler som har valt sina YES meals sparas i profileList.
		if (index >= mealList.size()) {

			if (choice.equals("yes")) { //Om sista måltiden är choice "yes".
				Meal lastmeal = mealList.get(index - 1); //Skapa en lastmeal och sätt den till föregående index.
				profile.getSessionMealList().add(lastmeal); //Addera den till SessionMealList.
			}

			List<Profile> profileList = (List<Profile>) session.getAttribute("profileList");
			if (profileList == null) {
				profileList = new ArrayList<>();
				session.setAttribute("profileList", profileList);
			}
			profileList.add(profile);
			model.addAttribute("yesMealList", profile.getSessionMealList());
			return "result";
		}

		Meal meal = mealList.get(index);//Hämtar index på måltid

		model.addAttribute("mealList", mealList);
		model.addAttribute("meal", meal);
		model.addAttribute("mealIndex", index + 1);//Tar nästa måltid

		if (choice.equals("yes")) {
			Meal addmeal = mealList.get(index - 1);//Hämtar indexet för måltiden som visades
			profile.getSessionMealList().add(addmeal);//Om choice yes läggs måltiden i listan i profiles matlistan
		}

		return "main";
	}


	@GetMapping("/matchingMeals")
	public String matchingMeals(Model model, HttpSession session) {
		//Profile currentProfile = (Profile) session.getAttribute("profile"); //Gets current profile from session
		List<Profile> profileList = (List<Profile>) session.getAttribute("profileList");// Gets List of Profiles in the session
		List<Meal> matchingMeals = new ArrayList<>(); //Empty List to add the matchingMeals to

		Map<Long, Integer> matchedMeals = new HashMap<>();
		for (Profile profile : profileList) {
			for (Meal m : profile.getSessionMealList()) {
				Long id = m.getId();
				if (!matchedMeals.containsKey(id)) {
					matchedMeals.put(id, 1);
				} else {
					int numberOfPreviousOccurrences = matchedMeals.get(id);
					matchedMeals.put(id, numberOfPreviousOccurrences + 1);
				}
			}
		}

		List<Long> matchedMealsIds = new ArrayList<>();
		for (Map.Entry<Long, Integer> entry : matchedMeals.entrySet()) {
			if (entry.getValue() == profileList.size()) {
				matchedMealsIds.add(entry.getKey());
			} else if (entry.getValue() == profileList.size() - 1) {
				matchedMealsIds.add(entry.getKey());
			}
		}

		List<Meal> mealList = mealService.findAll(); //Tar alla måltider från databas.
		List<Meal> mealListById = mealList.stream()
				                          .filter(meal -> matchedMealsIds.contains(meal.getId()))
				                          .collect(Collectors.toList());

		model.addAttribute("mealListById", mealListById);
		return "matchingMeals"; //Shows the matchingMeals view.
	}

}
