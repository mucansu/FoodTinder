package com.example.demo.Service;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.Profile;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.RecordNotFoundException;
import com.example.demo.Repository.ProfileRepository;
import com.example.demo.Service.Interfaces.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService extends BaseService implements IProfileService {
	public static final String YES = "yes";
	public static boolean isChoiceDone = false;

	@Autowired
    private ProfileRepository profileRepository;
	@Autowired
	private MealService mealService;
	public void findProfileAndMealLists(Model model, Long id, Integer index, String choice, HttpSession session){
		// Fetch profile from session and meals related to the user
		Profile profile = getProfileFromSession(id, session);
		List<Meal> mealList = mealService.getMealsByUserId(profile.getUser().getId());

		// Handle profile choice
		handleProfileChoice(index, choice, profile, mealList, session);

		// Update model attributes
		updateModelAttributes(index, choice, profile, mealList, model, session);
	}

	private void handleProfileChoice(Integer index, String choice, Profile profile, List<Meal> mealList, HttpSession session) {
		if (index >= mealList.size()) {
			if (choice.equals(YES)) {
				Meal lastmeal = mealList.get(index - 1);
				profile.getSessionMealList().add(lastmeal);
			}

			saveProfileToSession(profile, session);
			isChoiceDone = true;
		}

		if (choice.equals(YES)) {
			Meal addmeal = mealList.get(index - 1);
			profile.getSessionMealList().add(addmeal);
		}
	}

	private void saveProfileToSession(Profile profile, HttpSession session) {
		List<Profile> profileList = (List<Profile>) session.getAttribute("profileList");
		if (profileList == null) {
			profileList = new ArrayList<>();
			session.setAttribute("profileList", profileList);
		}
		profileList.add(profile);
	}

	private void updateModelAttributes(Integer index, String choice, Profile profile, List<Meal> mealList, Model model, HttpSession session) {
		Meal meal = mealList.get(index);
		model.addAttribute("mealList", mealList);
		model.addAttribute("meal", meal);
		model.addAttribute("mealIndex", index + 1);

		if (isChoiceDone) {
			model.addAttribute("yesMealList", profile.getSessionMealList());
			model.addAttribute("profileList", session.getAttribute("profileList"));
		}
	}
	public Profile getProfileFromSession(Long id, HttpSession session) {
		Profile profile = (Profile) session.getAttribute("profile");
		if (profile == null || profile.getId() != id) { //Kollar om gamla profilen är kvar
			profile = (Profile) findById(id);
			session.setAttribute("profile", profile);
		}
		return profile;
	}

	@Override
	protected CrudRepository<Profile,Long> getRepository() {
		return profileRepository;
	}
}

