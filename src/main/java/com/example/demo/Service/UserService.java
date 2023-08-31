package com.example.demo.Service;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.RecordNotFoundException;
import com.example.demo.Repository.UserRepository;
import com.example.demo.Service.Interfaces.IProfileService;
import com.example.demo.Service.Interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
@Service
public class UserService extends BaseService <User,Long> implements IUserService {
    //public UserDetailsService getUsernamePassword;
    @Autowired
   private UserRepository userRepository;

    @Override
    protected CrudRepository<User,Long> getRepository() {
        return userRepository;
    }

    @Override
    public void add(Object o) {
        User user = (User) o;
        userRepository.save(user);
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



