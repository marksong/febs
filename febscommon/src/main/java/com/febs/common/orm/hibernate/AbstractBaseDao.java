package com.febs.common.orm.hibernate;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import com.febs.common.bean.utils.ReflectionUtil;
import com.febs.common.orm.PaginationSupport;


/**
 * 
 * @author Mark
 *
 * @param <T>
 */
public abstract class AbstractBaseDao<T> {

	protected Logger log = LoggerFactory.getLogger(getClass());

	@Autowired  
    private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * <p/>
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    public boolean isPropertyUnique(final String entity,
                                    final String propertyName, final Object newValue,
                                    final Object orgValue) {
        if (newValue == null || newValue.equals(orgValue))
            return true;
        Object object = findUniqueByProperty(entity, propertyName, newValue);
        return (object == null);
    }

    /**
     * 判断对象的属性值在数据库内是否唯一.
     * <p/>
     * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
     */
    public boolean isPropertyUnique(final Class<?> entity,
                                    final String propertyName, final Object newValue,
                                    final Object orgValue) {
        if (newValue == null || newValue.equals(orgValue))
            return true;
        Object object = findUniqueByProperty(entity, propertyName, newValue);
        return (object == null);
    }

    /**
     * 按属性查找唯一对象,匹配方式为相等.
     */
    @SuppressWarnings("unchecked")
	public T findUniqueByProperty(final String entity,
                                  final String propertyName, final Object value) {
        return (T) findUniqueByProperty14(entity, propertyName, value);
    }

    /**
     * 按属性查找唯一对象,匹配方式为相等.
     */
    @SuppressWarnings("unchecked")
	public T findUniqueByProperty(final Class<?> entity,
                                  final String propertyName, final Object value) {
        return (T) findUniqueByProperty14(entity, propertyName, value);
    }

    /**
     * 按属性查找唯一对象,匹配方式为相等.
     */
    public Object findUniqueByProperty14(final String entity,
                                         final String propertyName, final Object value) {
        final String hql = "from " + entity + " where " + propertyName + "=?";
        return findUnique14(hql, value);
    }

    /**
     * 按属性查找唯一对象,匹配方式为相等.
     */
    public Object findUniqueByProperty14(final Class<?> entity,
                                         final String propertyName, final Object value) {
        DetachedCriteria criteria = DetachedCriteria.forClass(entity);
        criteria.add(Restrictions.eq(propertyName, value));
        return findUnique14(criteria);
    }

    /**
     * 按HQL查询唯一对象（支持泛型）.
     *
     * @param hql
     * @param values
     * @return
     */
    @SuppressWarnings("unchecked")
	public T findUnique(final String hql, final Object... values) {
        return (T) findUnique14(hql, values);
    }

    /**
     * 按DetachedCriteria查询唯一对象（支持泛型）.
     *
     * @param detachedCriteria
     * @return
     */
    @SuppressWarnings("unchecked")
	public T findUnique(final DetachedCriteria detachedCriteria) {
        return (T) findUnique14(detachedCriteria);
    }

    /**
     * 按HQL查询唯一对象.
     *
     * @param hql
     * @param values
     * @return
     */
    public Object findUnique14(final String hql, final Object... values) {
        Query query = getSession().createQuery(hql);
        if (values != null) {
            for (int i = 0; i < values.length; i++) {
                query.setParameter(i, values[i]);
            }
        }
        return query.uniqueResult();
    }

    /**
     * 按DetachedCriteria查询唯一对象.
     *
     * @param detachedCriteria
     * @return
     */
    public Object findUnique14(final DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        return criteria.uniqueResult();
    }

    /**
     * count查询
     *
     * @param entityName
     * @param name
     * @param value
     * @return
     */
    public Number findRowCount(final String entityName, final String name,
                               final Object value) {
        DetachedCriteria criteria = DetachedCriteria.forEntityName(entityName);
        criteria.add(Restrictions.eq(name, value));
        return findRowCount(criteria);
    }

    /**
     * count查询
     *
     * @param persistentClass
     * @param name
     * @param value
     * @return
     */
    public Number findRowCount(final Class<T> persistentClass,
                               final String name, final Object value) {
        DetachedCriteria criteria = DetachedCriteria.forClass(persistentClass);
        criteria.add(Restrictions.eq(name, value));
        return findRowCount(criteria);
    }

    /**
     * count查询
     *
     * @param detachedCriteria
     * @return
     */
    public Number findRowCount(final DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        criteria.setProjection(Projections.rowCount());
        return (Number) criteria.uniqueResult();
    }
    
    @SuppressWarnings("unchecked")
	public List<T> findTop(final DetachedCriteria detachedCriteria, final int count){
        Criteria criteria = detachedCriteria
                .getExecutableCriteria(getSession());
        criteria.setMaxResults(count);
        return criteria.list();
    }


    /**
     * 获得hql的查询数量语句
     *
     * @param hql
     * @return
     */
    public static String getCountHql(String hql) {
        StringBuilder sb = new StringBuilder();
        sb.append("select count(*) ");
        int fromIndex = hql.indexOf("from");
        int orderIndex = hql.indexOf("order");
        if (fromIndex > 0 && orderIndex > 0) {
            sb.append(hql.substring(fromIndex, orderIndex));
        } else if (fromIndex <= 0 && orderIndex > 0) {
            sb.append(hql.substring(0, orderIndex));
        } else if (fromIndex > 0 && orderIndex < 0) {
            sb.append(hql.substring(fromIndex));
        } else {
            sb.append(hql);
        }
        return sb.toString();
    }

	/**
	 * 分页查询,支持是否自动count，自动添加order字段，支持泛型
	 * 
	 * @param detachedCriteria
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	public void findPageByCriteria(PaginationSupport<T> page, final DetachedCriteria detachedCriteria) {
		this.findPageByCriteria14(page, detachedCriteria);
	}

	/**
	 * 分页查询,支持是否自动count，自动添加order字段,如果page参数为null，则返回一个包含所有记录的page
	 * 
	 * @param page
	 * @param detachedCriteria
	 * @param pageSize
	 * @param startIndex
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public void findPageByCriteria14(PaginationSupport<T> page, final DetachedCriteria detachedCriteria) {
		try {
			Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
			page.setTotalCount(findCriteriaRowCount(criteria));
//			if (page.isHasOrder()) {
//				for (Sort order : (List<Sort>) page.getOrder()) {
//					if (order.getOrderBy() == OrderBy.asc)
//						criteria.addOrder(Order.asc(order.getOrder()));
//					else
//						criteria.addOrder(Order.desc(order.getOrder()));
//				}
//			}
			page.setItems(criteria.setFirstResult(page.getStartIndex()).setMaxResults(page.getPageSize()).list());
		} catch (RuntimeException ex) {
			log.error("find page by criteria catch exception {}", ex);
			throw ex;
		}
	}

	/**
	 * 按criteria查询count
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	protected int findCriteriaRowCount(final Criteria criteria) {
		CriteriaImpl impl = (CriteriaImpl) criteria;
		// 先把Projection、ResultTransformer、OrderBy取出来,清空三者后再执行Count操作
		Projection projection = impl.getProjection();
		ResultTransformer transformer = impl.getResultTransformer();
		// 将orderBy对象中的排序字段存入数组中
		List<?> orderEntries = ReflectionUtil.getProperty(impl, "orderEntries");
		// 将排序字段设置为空
		ReflectionUtil.setProperty(impl, "orderEntries", Collections.emptyList());
		// 执行Count查询
		int count = ((Number) criteria.setProjection(Projections.rowCount()).uniqueResult()).intValue();
		// 将取出的参数设回bean
		impl.setProjection(projection);
		if (projection == null && transformer == null) {
			impl.setResultTransformer(CriteriaSpecification.ROOT_ENTITY);
		} else if (transformer != null) {
			impl.setResultTransformer(transformer);
		}
		ReflectionUtil.setProperty(impl, "orderEntries", orderEntries);
		return count;

	}

	@SuppressWarnings("unchecked")
	public List<T> findByCriteria(final DetachedCriteria detachedCriteria) {
		Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
		return criteria.list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Object... values) {
		return createQuery(hql, values).list();
	}

	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> List<X> find(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).list();
	}


	/**
	 * 按HQL查询对象列表.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	@SuppressWarnings("unchecked")
	public <X> X findUnique(final String hql, final Map<String, Object> values) {
		return (X) createQuery(hql, values).uniqueResult();
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * 
	 * @param values
	 *            数量可变的参数,按顺序绑定.
	 */
	public Query createQuery(final String queryString, final Object... values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return query;
	}

	/**
	 * 根据查询HQL与参数列表创建Query对象.
	 * 
	 * @param values
	 *            命名参数,按名称绑定.
	 */
	public Query createQuery(final String queryString, final Map<String, Object> values) {
		Assert.hasText(queryString, "queryString不能为空");
		Query query = getSession().createQuery(queryString);
		if (values != null) {
			query.setProperties(values);
		}
		return query;
	}

	/**
	 * 从hql中查询分页，支持泛型（order by 必须自己在hql里写，程序不自动处理）
	 * 
	 * @param page
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> PaginationSupport<X> findPageByHql(PaginationSupport<X> page, String hql, Object... params) {
		page.setTotalCount(findHqlRowCount(hql, params));
		Query query = this.createQuery(hql, params);
		query.setFirstResult(page.getStartIndex()).setMaxResults(page.getPageSize());
		page.setItems(query.list());
		return page;
	}

	/**
	 * 从hql中查询分页，支持泛型（order by 必须自己在hql里写，程序不自动处理）
	 * 
	 * @param page
	 * @param hql
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <X> PaginationSupport<X> findPageByHql(PaginationSupport<X> page, String hql, Map<String, Object> params) {
		page.setTotalCount(findHqlRowCount(hql, params));
		Query query = this.createQuery(hql, params);
		query.setFirstResult(page.getStartIndex()).setMaxResults(page.getPageSize());
		page.setItems(query.list());
		return page;
	}

	/**
	 * 按hql获取rowcount，仅支持简单的hql,distinct等复杂的语句请自行查询
	 * 
	 * @param hql
	 * @param params
	 * @return
	 */
	protected int findHqlRowCount(String hql, Object... params) {
		// 去除查询内容
		String countHql = "select count(*) from " + StringUtils.substringAfter(hql, "from");
		// 去除order by
		countHql = StringUtils.substringBefore(countHql, "order by");
		return ((Number) findUnique(countHql.toString(), params)).intValue();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 */
	public int batchExecute(final String hql, final Object... values) {
		return createQuery(hql, values).executeUpdate();
	}

	/**
	 * 执行HQL进行批量修改/删除操作.
	 * 
	 * @return 更新记录数.
	 */
	public int batchExecute(final String hql, final Map<String, Object> values) {
		return createQuery(hql, values).executeUpdate();
	}
	
}
