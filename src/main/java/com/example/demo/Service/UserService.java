package com.example.demo.Service;

import com.example.demo.Entities.User;
import com.example.demo.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
@Service
public class UserService {
    //public UserDetailsService getUsernamePassword;
    @Autowired
   private UserRepository userRepository;
    public User addUser(User user){
      return userRepository.save(user);
    }

   public User findById(Long id){
        return userRepository.findById(id).get();
   }

   public User findUserName(String userName){
        return userRepository.findByUserName(userName);
   }

 // public User findByUsername(String userName){
   //    return userRepository.findByUserName(userName);
 // }

 // public User findByPassword(String password){
      //  return userRepository.findByPassword(password);

   }
   /* public User getUsernamePassword(String userName,String password){
        return userRepository.findByUserNameAndPassword(userName,password);
    }*/



