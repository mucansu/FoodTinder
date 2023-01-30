package com.example.demo.security;

/*import com.example.demo.Entities.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;*/


import com.example.demo.Entities.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public class CustomUserDetailService implements UserDetailsService {

   // @Autowired
   // UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String em) throws UsernameNotFoundException {
        try{
            User user= userRepository.findByEmail(em);
            if(user==null){
                throw new UsernameNotFoundException("user not found");
            } else{
                return new CustomUserDetail(user);
            }
        }catch(Exception e){

            e.printStackTrace();
        }
        return null;
    }
}










