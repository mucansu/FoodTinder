package com.example.demo.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="name")
    private String mealName;

    @ManyToMany(mappedBy = "profileMealList")
    private List<Profile> profiles= new ArrayList<>();

    public Meal() {
    }

    //Constructor bara för test
    public Meal(String mealName){
        this.mealName = mealName;
    }

    public Meal(long id, String mealName, List<Profile> profiles) {
        this.id = id;
        this.mealName = mealName;
        this.profiles = profiles;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }

    public void setProfiles(List<Profile> profiles) {
        this.profiles = profiles;
    }
}
