package com.example.demo.Repository;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MealRepository extends CrudRepository<Meal,Long> {
	List<Meal> findByUser(User user);
	List<Meal> findByUserId (Long userId);
}
