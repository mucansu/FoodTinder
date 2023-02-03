package com.example.demo;

import com.example.demo.Entities.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}


	@Test
	public void testUserCreation() {
		User user = new User("hannahöstebo","maryam","cansu","rk@gmail.com");
		Assertions.assertEquals("hannahöstebo", user.getUserName());
		Assertions.assertEquals("maryam", user.getFirstName());
		Assertions.assertEquals("cansu", user.getLastName());
		Assertions.assertEquals("rk@gmail.com", user.getEmail());
	}

}
