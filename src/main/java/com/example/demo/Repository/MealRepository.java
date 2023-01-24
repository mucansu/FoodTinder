package com.example.demo.Repository;

import com.example.demo.Entities.Meal;
import org.springframework.data.repository.CrudRepository;

public interface MealRepository extends CrudRepository<Meal,Long> {
}
