package com.example.demo.Entities;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;


	@NotBlank(message = "User name should not be empty")
	@Size(max = 15, message = "Username should not be more than 15 characters")
	private String userName;
	@NotBlank(message = "First name should not be empty")
	@Size (max = 15, message = "Name should not be more than 15 characters")
	private String firstName;
	@NotBlank(message = "Last name should not be empty")
	@Size (max = 15, message = "Name should not be more than 15 characters")
	private String lastName;
	@NotBlank(message = "Email field should not be empty")
	@Column(unique = true)
	private String email;
	@NotBlank(message = "Password field should not be empty")
	private String password;
	private String role;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Profile> profileList = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Meal> userMealList = new ArrayList<>();
	public User() {

	}

	public User(Long id, String userName) {
		this.id = id;
		this.userName = userName;
	}

	public User(String userName, String firstName, String lastName, String email) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public User(Long id, String userName, String firstName, String lastName, String email,
				String password, String role, List<Profile> profileList, List<Meal> userMealList) {
		this.id = id;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.password = password;
		this.role = role;
		this.profileList = profileList;
		this.userMealList = userMealList;
	}


	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Profile> getProfileList() {
		return profileList;
	}

	public void setProfileList(List<Profile> profileList) {
		this.profileList = profileList;
	}

	public List<Meal> getUserMealList() {
		return userMealList;
	}

	public void setUserMealList(List<Meal> userMealList) {
		this.userMealList = userMealList;
	}
}
