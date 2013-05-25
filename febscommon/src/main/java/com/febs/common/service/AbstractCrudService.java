package com.febs.common.service;

import java.io.Serializable;

import org.apache.commons.lang.NotImplementedException;




/**
 * 抽象Service层，目的是定一套方法的规范
 * @author Mark Song
 *
 * @param <T>
 */
public abstract class AbstractCrudService<T, PK extends Serializable> {

	/**
	 * 保存或更新对象
	 * @param instance
	 */
	public void saveOrUpdate(T instance) {
		throw new NotImplementedException();
	}

	/**
	 * 逻辑删除，常用
	 * @param instance
	 */
	public void delete(T instance) {
		throw new NotImplementedException();
	}

	public T findById(PK id) {
		throw new NotImplementedException();
	}

	/**
	 * 物理删除，一般情况下不建议物理删除
	 * @param instance
	 */
	public void physicalDelete(T instance) {
		throw new NotImplementedException();
	}

}
