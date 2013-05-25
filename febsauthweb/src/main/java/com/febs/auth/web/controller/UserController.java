package com.febs.auth.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.febs.auth.core.po.User;
import com.febs.auth.core.service.UserService;
import com.febs.common.orm.PaginationSupport;
import com.febs.common.service.AbstractCrudService;
import com.febs.common.web.controller.AbstractController;

@Controller
@RequestMapping("/auth/user")
public class UserController extends AbstractController<User> {
	
	@Autowired
	private UserService userService;

	@Override
	public String modelName() {
		return "user";
	}

	@Override
	public AbstractCrudService<User, String> modelService() {
		return userService;
	}





	
	
	
	
	
}
