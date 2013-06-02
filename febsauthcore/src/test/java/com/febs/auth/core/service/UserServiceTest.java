package com.febs.auth.core.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.febs.auth.core.po.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void test() {
		for (int i = 0; i < 1000; i++) {
			testAttachDirty();
		}
	}
	
	@Test
	public void testAttachDirty() {
		User user = new User();
		user.setUsername("name1");
		user.setPassword("pass1");
		userService.saveOrUpdate(user);
	}

}
