package com.febs.auth.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.febs.auth.core.po.User;
import com.febs.auth.core.po.UserDao;
import com.febs.common.orm.PaginationSupport;
import com.febs.common.service.AbstractCrudService;

@Service
@Transactional
public class UserService extends AbstractCrudService<User, String> {

	@Autowired
	private UserDao userDao;
	
	@Override
	public void saveOrUpdate(User instance) {
		userDao.attachDirty(instance);
	}

	@Override
	public void findPage(PaginationSupport<User> page) {
		userDao.findPage(page);
	}

	
}
