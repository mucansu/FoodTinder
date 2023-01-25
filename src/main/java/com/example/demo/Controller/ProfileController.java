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

    @GetMapping("/createProfile")
    public String createProfile(Model model){
     //   User user = new User();
        Profile profile= new Profile();
        model.addAttribute("profile", profile);
        return "createProfile";
    }
  /*  @PostMapping("/saveItem")
    public String saveItem (@ModelAttribute Item item){
        itemRepository.save(item); //Sparar objektet
        return "redirect:/room/" + item.getRoom().getId(); //Skickar tillbaka till /room/{id}
    }*/

    @PostMapping("/createProfile/{id}")
    public String createprofile(@ModelAttribute Profile profile,@PathVariable Long id, Model model ){
        User user = userService.findById(id);

        profileService.addProfile(profile);
        model.addAttribute("profile",profile);
        model.addAttribute("user",user);

        return "redirect:/createProfile";
    }

    /*
   @GetMapping("/createProfile/{id}")
    public String addUserInProfile(@PathVariable Long id, Model model){

            User user = userService.findById(id);

        Profile profile = new Profile();
        profile.setUser(user);
            model.addAttribute("user", user); //Skicka till thymeleaf
            model.addAttribute("profile", profile);//Skicka till thymeleaf

            return "createProfile";
        }

*/

    }


