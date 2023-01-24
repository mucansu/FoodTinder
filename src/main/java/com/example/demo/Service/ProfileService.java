package com.example.demo.Service;

import com.example.demo.Entities.Profile;
import com.example.demo.Repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public Profile addProfile(Profile profile){
        return profileRepository.save(profile);
    }
}
