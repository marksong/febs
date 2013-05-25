package com.febs.auth.core.po;

import org.springframework.stereotype.Repository;

import com.febs.common.orm.hibernate.AbstractCrudDao;

@Repository
public class UserDao extends AbstractCrudDao<User, String> {

}
