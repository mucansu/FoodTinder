package com.example.demo.Service;

import com.example.demo.Entities.Meal;
import com.example.demo.Repository.MealRepository;
import com.example.demo.Service.Interfaces.IMealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MealService extends BaseService <Meal,Long> implements IMealService {
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

	@Override
	public void add(Object o) {
		Meal meal = (Meal) o;
		mealRepository.save(meal);
	}
}
