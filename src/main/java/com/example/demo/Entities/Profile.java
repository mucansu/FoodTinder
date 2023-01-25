package com.example.demo.Entities;

import javax.persistence.*;

@Entity
public class Profile {
  @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

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
}
