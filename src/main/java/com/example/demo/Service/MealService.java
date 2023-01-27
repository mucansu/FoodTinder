package com.example.demo.Service;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.Profile;
import com.example.demo.Repository.MealRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MealService {
    @Autowired
    private MealRepository mealRepository;


    public Meal findById(Long id){
        return mealRepository.findById(id).get();
    }


	public List<Meal> findAll() {
		List<Meal> mealList = (List<Meal>) mealRepository.findAll();
		return mealList;
	}

	public Meal addMeal(Meal meal){
		return mealRepository.save(meal);
	}

}
