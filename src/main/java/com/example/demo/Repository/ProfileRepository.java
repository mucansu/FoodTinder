package com.example.demo.Repository;

import com.example.demo.Entities.Meal;
import com.example.demo.Entities.Profile;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProfileRepository extends CrudRepository<Profile, Long> {
	List<Profile> findByUserId (Long userId);

}
