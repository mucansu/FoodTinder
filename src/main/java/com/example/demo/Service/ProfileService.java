package com.example.demo.Service;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.Profile;
import com.example.demo.Entities.User;
import com.example.demo.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Profile addProfile(Profile profile){
        return profileRepository.save(profile);
    }

    public Profile findById(Long id){
        return profileRepository.findById(id).get();
    }

	public Profile deleteById(Long id){
			Profile profile = profileRepository.findById(id).orElse(null);
			if (profile != null) {
				profileRepository.delete(profile);
			}
			return profile;
		}

	public Profile getProfileFromSession(Long id, HttpSession session) {
		Profile profile = (Profile) session.getAttribute("profile");
		if (profile == null || profile.getId() != id) { //Kollar om gamla profilen är kvar
			profile = findById(id);
			session.setAttribute("profile", profile);
		}
		return profile;
	}
}

