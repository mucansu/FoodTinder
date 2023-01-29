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
			model.addAttribute("yesMealList", profile.getSessionMealList()); //Adding the yesMealList to the controller.
			model.addAttribute("profileList", profileList);//Add the profileList to the model.
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

		Map<Long, Integer> matchedMeals = new HashMap<>(); //Creates a HashMap
		for (Profile profile : profileList) { //Loops over the profileList
			for (Meal m : profile.getSessionMealList()) { //Loops over SessionMealList
				Long id = m.getId(); //Sets the id to ID of the meal.
				if (!matchedMeals.containsKey(id)) { //If the meal doesn't exist in the matchedMeals List,
					matchedMeals.put(id, 1); //It adds it to the matchedMeals MapList and sets the key to 1.
				} else {
					int numberOfPreviousOccurrences = matchedMeals.get(id); //Else it finds the ID of the meals key value.
					matchedMeals.put(id, numberOfPreviousOccurrences + 1); //Adds 1 to the key.
				}
			}
		}

		List<Long> matchedMealsIds = new ArrayList<>(); //Creates a new list.
		for (Map.Entry<Long, Integer> entry : matchedMeals.entrySet()) { //Iterates over the entries of the map "matchedMeals".
			if (entry.getValue() == profileList.size()) { //For each entry it checks if the value of the entry equals to the size of the list profileList.
				matchedMealsIds.add(entry.getKey()); //If so, it adds the key of the entry to the matcheMealsIds List.
			} else if (entry.getValue() == profileList.size() - 1) { //If the value of the entry equals to the size of "profileList" minus 1.
				matchedMealsIds.add(entry.getKey()); //it also adds the key to the "matchedMealsIds" list.
			}
		}

		List<Meal> mealList = mealService.findAll(); //Tar alla måltider från databas.
		List<Meal> mealListById = mealList.stream()
				                          .filter(meal -> matchedMealsIds.contains(meal.getId())) //Filters meaList and returns mealListById that contain the meals whose id matches the id in matchedMealsIds.
				                          .collect(Collectors.toList()); //Collects all the filtered meals to a list.

		model.addAttribute("mealListById", mealListById); //Sends the new list to the template matchingMeals.
		return "matchingMeals"; //Shows the matchingMeals view.
	}

}
