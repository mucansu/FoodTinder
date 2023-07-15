package com.example.demo.Service.Interfaces;

import com.example.demo.Entities.Profile;
import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;

public interface IProfileService extends IBaseService{
    void findProfileAndMealLists(Model model, Long id, Integer index, String choice, HttpSession session);
    Profile addProfile(Profile profile);
    Profile findById(Long id);
    Profile deleteById(Long id);
    Profile getProfileFromSession(Long id, HttpSession session);
}
