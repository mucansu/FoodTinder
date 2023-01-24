package com.example.demo.Repository;

import com.example.demo.Entities.Profile;
import org.springframework.data.repository.CrudRepository;

public interface ProfileRepository extends CrudRepository<Profile, Long> {

}
