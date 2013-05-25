package com.febs.common.bean.utils;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

/**
 * 
 * 反射工具
 * 
 * @author Mark Song
 * 
 */
public class ReflectionUtil extends ReflectionUtils {

	private static Logger log = LoggerFactory.getLogger(ReflectionUtil.class);

	/**
	 * 通过反射,获得Class定义中声明的父类的泛型参数第一个参数的类型. 如无法找到, 返回Object.class. eg. public
	 * UserDao extends BaseDao<User>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be
	 *         determined
	 */
	public static Class<?> getSuperClassGenricType(final Class<?> clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射,获得定义Class时声明的父类的第index个泛型参数的类型. 如无法找到, 返回Object.class.
	 * 
	 * 如public UserDao extends HibernateDao&lt;User,Long&gt;
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be
	 *         determined
	 */
	public static Class<?> getSuperClassGenricType(final Class<?> clazz,
			final int index) {

		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			log.warn(clazz.getSimpleName()
					+ "'s superclass not ParameterizedType");
			return (Class<?>) Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			log.warn("Index: " + index + ", Size of " + clazz.getSimpleName()
					+ "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			log.warn(clazz.getSimpleName()
					+ " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class<?>) params[index];
	}

	@SuppressWarnings("unchecked")
	public static <X, T> X getProperty(final T bean, final String name) {
		Field field = ReflectionUtils.findField(bean.getClass(), name);
		ReflectionUtils.makeAccessible(field);
		X value = null;
		try {
			value = (X) field.get(bean);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error("get " + name + " from " + bean.getClass().getName() + " faild.", e);
		}
		return value;
	}
	
	public static <T> void setProperty(final T bean, final String name, final Object value) {
		Field field = ReflectionUtils.findField(bean.getClass(), name);
		ReflectionUtils.makeAccessible(field);
		try {
			field.set(bean, value);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			log.error("set " + name + " to " + bean.getClass().getName() + " faild.", e);
		}
	}
}
