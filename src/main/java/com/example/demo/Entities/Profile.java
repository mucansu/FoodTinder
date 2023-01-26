package com.example.demo.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Profile {
  @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne()
    private User user;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "meal_profiles", joinColumns = @JoinColumn(name = "meal_id"), inverseJoinColumns = @JoinColumn(name = "profiles_id"))
    private List<Meal> mealList = new ArrayList<>();

    public Profile() {
    }

    public Profile(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Profile(long id, String name, User user) {
        this.id = id;
        this.name = name;
        this.user = user;
    }

    public Profile(long id, String name, User user, List<Meal> mealList) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.mealList = mealList;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Meal> getMealList() {
        return mealList;
    }

    public void setMealList(List<Meal> mealList) {
        this.mealList = mealList;
    }

}

