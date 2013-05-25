package com.febs.common.orm.hibernate;

import java.io.Serializable;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.IdentifierGenerator;

import com.febs.common.utils.UUIDSupport;


/**
 * UUID生成器
 * @author Mark Song
 *
 */
public class UUIDGen implements IdentifierGenerator {

	@Override
	public Serializable generate(SessionImplementor sessionimplementor,
			Object obj) throws HibernateException {
		return UUIDSupport.getUUIDWithoutMinus();
	}

}
