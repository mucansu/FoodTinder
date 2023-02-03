package com.example.demo;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.Profile;
import com.example.demo.Entities.User;
import com.example.demo.Repository.MealRepository;
import com.example.demo.Repository.ProfileRepository;
import com.example.demo.Service.MealService;
import com.example.demo.Service.ProfileService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {

	@Autowired
	private ProfileRepository profileRepository;
	@Autowired
	private MealRepository mealRepository;

	@Test
	void contextLoads() {
	}


	@Test
	public void testUserCreation() {
		User user = new User("hannahöstebo","maryam","cansu","rk@gmail.com");
		Assertions.assertEquals("hannahöstebo", user.getUserName());
		Assertions.assertEquals("maryam", user.getFirstName());
		Assertions.assertEquals("cansu", user.getLastName());
		Assertions.assertEquals("rk@gmail.com", user.getEmail());
	}
	@Test
	public void testCreation(){
		Profile profile = new Profile(1,"Mustafa");
		List<Profile> profiles = new ArrayList<>();
		User user = new User(1L,"Mustafa");
		Meal meal = new Meal(1L, "Mannagryta", profiles,user);

		Assertions.assertEquals("Mannagryta",meal.getMealName());
		Assertions.assertEquals(1L,user.getId());
		Assertions.assertEquals("Mustafa",user.getUserName());
		Assertions.assertEquals("Mustafa",profile.getName());
		}
	}


