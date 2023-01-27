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

        //Kollar vilken profil som är inloggad
        Profile profile = (Profile) session.getAttribute("profile");
        if (profile==null || profile.getId()!=id){//kolla om gamla profilen är kvar
            profile = profileService.findById(id);
            session.setAttribute("profile",profile);
        }


        List<Meal> mealList = mealService.findAll(); //Tar alla måltider från databas.

        //När mealList är 20 ska resultat visas och profiler som har valt sina YES meals sparas i profileList.
        if (index >= mealList.size()){
            List<Profile> profileList = (List<Profile>) session.getAttribute("profileList");
            if (profileList==null){
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
        model.addAttribute("mealIndex", index+1);//Tar nästa måltid
        if (choice.equals("yes")){
            profile.getSessionMealList().add(meal);//Om choice yes läggs måltiden i listan i profiles matlista.

        }

        return "main";
    }


   /* @GetMapping("/matches")
    public String findMatches(HttpSession session, Model model){
        // Retrieve the list of profiles from the session
        List<Profile> profileList = (List<Profile>) session.getAttribute("profileList");

        // Create a list to store the matches
        List<Profile> matches = new ArrayList<>();

        // Iterate through the profileList
        for (Profile profile1 : profileList) {
            for (Profile profile2 : profileList) {
                // Compare the sessionMealList of the two profiles
                if (profile1 != profile2 && profile1.getSessionMealList().equals(profile2.getSessionMealList())) {
                    // If they match, add the profiles to the matches list
                    matches.add(profile1);
                    matches.add(profile2);
                }
            }
        }
        // Add the matches list to the model
        model.addAttribute("matches", matches);

        // Return the name of the view to display the matches
        return "matches";
    }*/

    @GetMapping("/matchingMeals")
    public String matchingMeals(Model model, HttpSession session) {
        Profile currentProfile = (Profile) session.getAttribute("profile"); //Gets current profile from session
        List<Profile> profileList = (List<Profile>) session.getAttribute("profileList");// Gets List of Profiles in the session
        List<Meal> matchingMeals = new ArrayList<>(); //Empty List to add the matchingMeals to

      /*  List<Meal> commonMeals = new ArrayList<>(profileList.get(0).getSessionMealList()); //Gets the first profiles sessionMealList.
        for(int i = 1; i < profileList.size(); i++) {
            List<Meal> currentProfileMeals = profileList.get(i).getSessionMealList(); //Gets the next profiles sessionMealList in the profileList
            List<Meal> temp = new ArrayList<>(); //Creats a temporary list to store meals that contains in both profiles sessionMealLists
            for(Meal meal : commonMeals) {  //Checks the meals from commonMeals list
                if(currentProfileMeals.contains(meal)) { //If currentProfileMeals contains meal
                    temp.add(meal); //Add it to the temporary list.
                }
            }
            commonMeals = temp;
        }
        matchingMeals.addAll(commonMeals);*/


        /*List<Meal> commonMeals = new ArrayList<>();
        boolean isCommon;
        for (Profile outerProfile : profileList) {
            for (Meal outerMeal : outerProfile.getSessionMealList()) {
                isCommon = true;
                for (Profile innerProfile : profileList) {
                    if (!innerProfile.getSessionMealList().contains(outerMeal)) {
                        isCommon = false;
                        break;
                    }
                }
                if (isCommon) {
                    commonMeals.add(outerMeal);
                }
                matchingMeals.addAll(commonMeals);
            }
        }*/


      /*  List<Meal> commonMeals = new ArrayList<>(profileList.get(0).getSessionMealList());
        for (int i = 1; i < profileList.size(); i++) {
            commonMeals.retainAll(profileList.get(i).getSessionMealList());
        }
        List<Meal> matchingMeals = new ArrayList<>(commonMeals);
        System.out.println(matchingMeals);*/

      /*  List<Meal> commonMeals; //A List to store commonMeals from.
        for (int i = 0; i < profileList.size(); i++) { // outer loop to iterate through all profiles
            Profile profile1 = profileList.get(i); //Gets the first index from the profileList and sets it to profile 1
            for (int j = i+1; j < profileList.size(); j++) { // nested loop to compare with all other profiles
                Profile profile2 = profileList.get(j); //Gets the next index from the profilesList and sets it to profile 2
                commonMeals = new ArrayList<>(profile1.getSessionMealList()); //Gets profile1 SessionMealList
                commonMeals.retainAll(profile2.getSessionMealList());//Gets profile2 SessionMealList and finds which meals are the same.
                matchingMeals.addAll(commonMeals); //Adds all the matching meals to matchingMeals list.
            }
        }*/

      /*  List<Meal> commonMeals; //A List to store commonMeals in the for-loop.
        for (Profile profile : profileList) { //Loops all the profiles that has a MealLists, from the profileList.
            if (!profile.equals(currentProfile)) { //Profile that is not currentProfile from the session.
                commonMeals = new ArrayList<>(currentProfile.getSessionMealList()); //Adds the SessionMealList from currentProfile to commonMeals list.
                commonMeals.retainAll(profile.getSessionMealList());//Checks which meals are common between the list commonMeal and another profile SessionMealList.
                matchingMeals.addAll(commonMeals); //Adds all the commonMeals matched to the matchingMeals List.
            }
        }*/
        model.addAttribute("matchingMeals", matchingMeals); //Adds the matching meals to the model.
        return "matchingMeals"; //Shows the matchingMeals view.
    }

}
