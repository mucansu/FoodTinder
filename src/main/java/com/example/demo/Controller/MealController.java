package com.example.demo.Controller;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.Profile;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.RecordNotFoundException;
import com.example.demo.Repository.MealRepository;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.MealService;
import com.example.demo.Service.ProfileService;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class MealController {
	private final MealService mealService;
	private final ProfileService profileService;
	private final UserService userService;
	@Autowired
	public MealController(MealService mealService, ProfileService profileService, UserService userService){
		this.mealService = mealService;
		this.profileService = profileService;
		this.userService = userService;
	}

	@Autowired
	private MealRepository mealRepository;
	@Autowired
	private UserRepository userRepository;

	public static final String DEFAULT = "default";

	@GetMapping("/main/{id}")
	public String mainPage(Model model, @PathVariable Long id,
	                       @RequestParam(required = false, defaultValue = "0") Integer index,
	                       @RequestParam(required = false, defaultValue = DEFAULT) String choice,
	                       HttpSession session) {
		if (ProfileService.isChoiceDone){
			return "result";
		}
		profileService.findProfileAndMealLists(model, id, index, choice, session);
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


		for (int i = 1; i <= profileList.size(); i++) {
			for (Map.Entry<Long, Integer> entry : matchedMeals.entrySet()) { //Iterates over the entries of the map "matchedMeals".
				if (entry.getValue() == profileList.size()) { //If the value of the entry equals to the size of "profileList" minus 1.
					matchedMealsIds.add(entry.getKey()); //it also adds the key to the "matchedMealsIds" list.
				}
			}

			if (matchedMealsIds.size() == 0)
				for (Map.Entry<Long, Integer> entry : matchedMeals.entrySet()) { //Iterates over the entries of the map "matchedMeals".
					if (entry.getValue() == profileList.size() - i) { //If the value of the entry equals to the size of "profileList" minus i.
						matchedMealsIds.add(entry.getKey()); //it also adds the key to the "matchedMealsIds" list.
					}
				}
		}


		//Hittar namnet på alla de ID:n som föregående funktion har hittat med högst keyvalue.
		//Vi behöver ha namnet på alla meals och i HASHMAPPEN Ovan så kollar vi endast ID.
		List<Meal> mealList = (List<Meal>) mealService.findAll(); //Tar alla måltider från databas (så den kan hitta namnet på de ID som har fått match)
		List<Meal> mealListById = mealList.stream()
				                          .filter(meal -> matchedMealsIds.contains(meal.getId())) //Filters meaList and returns mealListById that contain the meals whose id matches the id in matchedMealsIds.
				                          .collect(Collectors.toList()); //Collects all the filtered meals to a list.

		model.addAttribute("mealListById", mealListById); //Sends the new list to the template matchingMeals.
		return "matchingMeals"; //Shows the matchingMeals view.
	}

	//Resetar alla sessionMealLists. Så man kan börja om från början.
	@GetMapping("/reset")
	public String resetSession(HttpSession session) {
		session.removeAttribute("sessionMealList"); //Resetar sessionMealList från session.
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication(); //SpringSecurity kod
		String currentPrincipalName = authentication.getName();//SpringSecurity kod
		User user = userRepository.findByEmail(currentPrincipalName);//SpringSecurity kod
		return "redirect:/user/profile/" + user.getId(); //Visar profilsidan igen.
	}

	//Visar sidan med mealList
	@GetMapping("/mealList")
	public String addMealList(Model model, @RequestParam(required = false) Long id){
		User user = (User) userService.findById(id);
		model.addAttribute("user", user);
		return "mealList";
	}

	//Sätter ihop user och meal objekt med varandra och skickar med modellen.
	@GetMapping("/addMeal/{id}")
	public String add(Model model, @PathVariable Long id){
		User user = (User) userService.findById(id); //Hittar user med hjälp av id.
		Meal meal = new Meal(); //Skapa ett nytt meal objekt
		meal.setUser(user); // Sätter ihop meal med user.
		model.addAttribute("meal", meal);//Skicka meal till thymeleaf
		return "mealList";
	}

	//Sparar meal från formuläret i databasen och skickar till mealList templaten.
	@PostMapping("/saveMeal")
	public String saveMeal (@ModelAttribute Meal meal){
		mealService.save(meal); //Sparar meal i databasen
		return "redirect:/mealList?id=" + meal.getUser().getId();//Skickar tillbaka till /mealList
	}

	@GetMapping("/editMeal/{id}")
	public String editMeal(Model model, @PathVariable Long id){
		Meal meal = (Meal) mealService.findById(id);

		model.addAttribute("meal", meal);
		return "editMealForm";
	}

	@PostMapping("/saveEditedMeal")
	public String saveEditedMeal(@RequestParam String mealName, @RequestParam Long id){
		Meal meal = (Meal) mealService.findById(id);

		meal.setMealName(mealName);
		mealService.save(meal);
		return "redirect:/mealList?id=" + meal.getUser().getId();
	}


	@GetMapping("/deleteMeal/{id}")
	public String deleteMeal(@PathVariable Long id){
		Meal meal = (Meal) mealService.findById(id);
					mealService.deleteById(id);
		return "redirect:/mealList?id=" + meal.getUser().getId();
	}
}
