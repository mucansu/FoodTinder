package com.example.demo.Service;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.Profile;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.RecordNotFoundException;
import com.example.demo.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileService extends BaseService{
	public static final String YES = "yes";
	public static boolean isChoiceDone = false;

	@Autowired
    private ProfileRepository profileRepository;
	@Autowired
	private MealService mealService;
	public void findProfileAndMealLists(Model model, Long id, Integer index, String choice, HttpSession session){
		//Kollar vilken profil som är inloggad
		Profile profile = getProfileFromSession(id, session);

		//Hämtar alla måltider som är kopplade till användaren som är inloggad.
		List<Meal> mealList = mealService.getMealsByUserId(profile.getUser().getId());

		//När mealList.size är nådd ska resultat visas och profiler som har valt sina YES meals sparas i profileList.
		if (index >= mealList.size()) {

			if (choice.equals(YES)) { //Om sista måltiden är choice "yes".
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
			isChoiceDone = true;
		}

		Meal meal = mealList.get(index);//Hämtar index på måltid

		model.addAttribute("mealList", mealList);
		model.addAttribute("meal", meal);
		model.addAttribute("mealIndex", index + 1);//Tar nästa måltid

		if (choice.equals(YES)) {
			Meal addmeal = mealList.get(index - 1);//Hämtar indexet för måltiden som visades
			profile.getSessionMealList().add(addmeal);//Om choice yes läggs måltiden i listan i profiles matlistan
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

