package com.example.demo.Entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Meal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name="mealName")
    private String mealName;

    @ManyToMany(mappedBy = "mealList")
    private List<Profile> profiles= new ArrayList<>();
}
