package com.example.demo.Service;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.Profile;
import com.example.demo.Entities.User;
import com.example.demo.Exceptions.RecordNotFoundException;
import com.example.demo.Repository.MealRepository;
import com.example.demo.Service.Interfaces.IMealService;
import com.example.demo.Service.Interfaces.IProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MealService extends BaseService implements IMealService {
    @Autowired
    private MealRepository mealRepository;
//    public Meal findById(Long id){
//        return mealRepository.findById(id).get();
//    }
	public List<Meal> getMealsByUserId(Long userId){
		return mealRepository.findByUserId(userId);
	}

	@Override
	protected CrudRepository<Meal,Long> getRepository() {
		return mealRepository;
	}
}
