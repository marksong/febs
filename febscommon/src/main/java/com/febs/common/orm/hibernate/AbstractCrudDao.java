package com.febs.common.orm.hibernate;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.springframework.util.Assert;

import com.febs.common.bean.utils.ReflectionUtil;
import com.febs.common.orm.PaginationSupport;


/**
 * 封装了基本的增删改查的DAO
 * @author Mark Song
 *
 * @param <T>
 * @param <PK>
 */
public abstract class AbstractCrudDao<T, PK extends Serializable> extends AbstractBaseDao<T> {
	
	public static final String ID = "id";
	public static final String CREATER = "creater";
	public static final String CREATE_TIME = "createTime";
	public static final String MODIFIER = "modifier";
	public static final String MODIFY_TIME = "modifyTime";
	public static final String DELETER = "deleter";
	public static final String DELETE_FLAG = "deleteFlag";
	public static final String DELETE_TIME = "deleteTime";
	
	protected Class<?> entityClass;
	
	/**
	 * 用于Dao层子类使用的构造函数. 通过子类的泛型定义取得对象类型Class. eg. public class UserDao extends
	 * SimpleHibernateDao<User, Long>
	 */
	public AbstractCrudDao() {
		super();
		this.entityClass = ReflectionUtil.getSuperClassGenricType(getClass());
	}

	/**
	 * 根据Criterion条件创建Criteria.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public Criteria createCriteria(final Criterion... criterions) {
		Criteria criteria = getSession().createCriteria(entityClass);
		for (Criterion c : criterions) {
			if (c != null)
				criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 根据Criterion条件创建detachedCriteria.
	 * 
	 * 本类封装的find()函数全部默认返回对象类型为T,当不为T时使用本函数.
	 * 
	 * @param criterions
	 *            数量可变的Criterion.
	 */
	public DetachedCriteria createDetachedCriteria(final Criterion... criterions) {
		DetachedCriteria criteria = DetachedCriteria.forClass(this.entityClass);
		for (Criterion c : criterions) {
			if (c != null)
				criteria.add(c);
		}
		return criteria;
	}

	/**
	 * 按eq的方式匹配propertyName 调用{@link #find(Criterion...)}
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 */
	public List<T> findBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		Criterion criterion = Restrictions.eq(propertyName, value);
		return find(criterion);
	}

	/**
	 * 按id列表查询 调用{@link #find(Criterion...)}
	 * 
	 * @param ids
	 * @return
	 */
	public List<T> findByIds(List<PK> ids) {
		return find(Restrictions.in(getIdName(), ids));
	}

	/**
	 * 按条件查询 调用{@link #createCriteria(Criterion...)}
	 * 
	 * @param criterions
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> find(final Criterion... criterions) {
		return createCriteria(criterions).list();
	}

	/**
	 * 支持排序的查询所有 调用{@link #createCriteria(Criterion...)}
	 * 
	 * @param orderBy
	 * @param isAsc
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<T> findAll(String orderBy, boolean isAsc) {
		Criteria c = createCriteria();
		if (isAsc)
			c.addOrder(Order.asc(orderBy));
		else
			c.addOrder(Order.desc(orderBy));
		return c.list();
	}

	/**
	 * 判断对象的属性值在数据库内是否唯一.
	 * <p/>
	 * 在修改对象的情景下,如果属性新修改的值(value)等于属性原来的值(orgValue)则不作比较.
	 */
	public boolean isPropertyUnique(final String propertyName, final Object newValue, final Object orgValue) {
		if (newValue == null || newValue.equals(orgValue))
			return true;
		return findUniqueBy(propertyName, newValue) == null;
	}

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	public T findUniqueBy(final String propertyName, final Object value) {
		Assert.hasText(propertyName, "propertyName不能为空");
		return findUnique(Restrictions.eq(propertyName, value));
	}

	/**
	 * 按条件查询唯一对象（支持泛型）.
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	public T findUnique(final Criterion... criterions) {
		return super.findUnique(createDetachedCriteria(criterions));
	}

	/**
	 * count查询
	 * 
	 * @param detachedCriteria
	 * @return
	 */
	public Number findRowCount(final Criterion... criterions) {
		return super.findRowCount(createDetachedCriteria(criterions));
	}

	/**
	 * 按条件查询前n条记录，调用{@link #createTopCriteria(int, Criterion...)}
	 * 
	 * @param size
	 *            查询记录数
	 * @param criterions
	 * @return
	 * @see {@link #createTopCriteria(int, Criterion...)}
	 */
	@SuppressWarnings("unchecked")
	public List<T> findTop(int size, final Criterion... criterions) {
		return createTopCriteria(size, criterions).list();
	}

	/**
	 * 根据条件，生成查询前n条记录的criteria
	 * 
	 * @param size
	 * @param criterions
	 * @return
	 */
	public Criteria createTopCriteria(int size, Criterion... criterions) {
		Criteria criteria = createCriteria(criterions);
		criteria.setFirstResult(0).setMaxResults(size);
		return criteria;
	}

	/**
	 * 分页查询,支持是否自动count，自动添加order字段，支持泛型 调用
	 * {@link #createDetachedCriteria(Criterion...)}
	 * 
	 * @param page
	 * @param criterions
	 * @return
	 */
	public void findPage(PaginationSupport<T> page, final Criterion... criterions) {
		super.findPageByCriteria(page, createDetachedCriteria(criterions));
	}

	/**
	 * 根据id获取实例
	 * 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public T load(PK id) {
		Assert.notNull(id);
		T instance = (T) getSession().load(entityClass, id);
		Hibernate.initialize(instance);
		return instance;
	}

	/**
	 * 为Query添加distinct transformer.
	 */
	public Query distinct(Query query) {
		query.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return query;
	}

	/**
	 * 为Criteria添加distinct transformer.
	 */
	public Criteria distinct(Criteria criteria) {
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		return criteria;
	}

	/**
	 * 取得对象的主键名.
	 */
	public String getIdName() {
		ClassMetadata meta = getSessionFactory().getClassMetadata(entityClass);
		return meta.getIdentifierPropertyName();
	}

	/**
	 * 初始化属性（一对多的时候用init(T.getCollection)）
	 * 
	 * @param entity
	 */
	public void init(T entity) {
		Hibernate.initialize(entity);
	}
	
	public void save(T transientInstance) {
		log.debug("saving " + entityClass.getName() + " instance");
		try {
			getSession().save(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	public void delete(T persistentInstance) {
		log.debug("deleting " + entityClass.getName() + " instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}
	
	/**
	 * 逻辑删除
	 * @param persistentInstance
	 */
	public void logicDelete(AuditEntity persistentInstance) {
		log.debug("logic deleting " + entityClass.getName() + " instance");
		try {
			persistentInstance.setDeleteFlag(true);
			persistentInstance.setDeleteTime(new Date());
			getSession().update(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public T findById(java.lang.String id) {
		log.debug("getting " + entityClass.getName() + " instance with id: " + id);
		try {
			return (T) getSession().get((Class<T>) entityClass, id);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findByExample(T instance) {
		log.debug("finding " + entityClass.getName() + " instance by example");
		try {
			return getSession().createCriteria(entityClass).add(Example.create(instance)).list();
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findByProperty(String propertyName, Object value) {
		log.debug("finding " + entityClass.getName() + " instance with property: " + propertyName
				+ ", value: " + value);
		try {
			String queryString = "from " + entityClass.getName() + " as model where model." + propertyName + "= ?";
			return getSession().createQuery(queryString).list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		log.debug("finding all " + entityClass.getName() + " instances");
		try {
			String queryString = "from " + entityClass.getName();
			return getSession().createQuery(queryString).list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public T merge(T detachedInstance) {
		log.debug("merging " + entityClass.getName() + " instance");
		try {
			@SuppressWarnings("unchecked")
			T result = (T) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public void attachDirty(T instance) {
		log.debug("attaching dirty " + entityClass.getName() + " instance");
		try {
			AuditEntity entity = (AuditEntity) instance;
			if (StringUtils.isBlank(entity.getId())) {
				entity.setCreater("test");
				entity.setCreateTime(new Date());
			}
			/*if (instance instanceof AuditEntity) {
				AuditEntity entity = (AuditEntity) instance;
				if (StringUtils.isBlank(entity.getId())) {
					entity.setCreater(SpringSecurityUtils.getCurrentUserId());
					entity.setCreateTime(new Date());
				} else {
					entity.setModifier(SpringSecurityUtils.getCurrentUserId());
					entity.setModifyTime(new Date());
				}
			}*/
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}
