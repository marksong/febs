package com.febs.auth.core.service;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.febs.auth.core.po.User;
import com.febs.auth.core.po.UserDao;
import com.febs.common.orm.PaginationSupport;
import com.febs.common.orm.hibernate.AbstractCrudDao;
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
		DetachedCriteria criteria = DetachedCriteria.forClass(User.class);
		criteria.add(Restrictions.eq(AbstractCrudDao.DELETE_FLAG, false));
		criteria.addOrder(Order.desc(AbstractCrudDao.CREATE_TIME));
		userDao.findPageByCriteria(page, criteria);
	}

	@Override
	public User findById(String id) {
		return userDao.findById(id);
	}

	@Override
	public void delete(String id) {
		userDao.logicDelete(findById(id));
	}
	
	

	
}
