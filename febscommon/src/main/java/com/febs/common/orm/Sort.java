package com.febs.common.orm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

public class Sort {
	private Logger log = LoggerFactory.getLogger(getClass());

	public enum OrderBy {
		desc, asc
	}

	private String order;
	private OrderBy orderBy = OrderBy.asc;

	public Sort(String order) {
		super();
		Assert.hasText(order, "order 字段不能为空");
		Assert.isTrue(!(OrderBy.desc.name().equals(order) || OrderBy.asc.name().equals(order)), "order不能为desc或者asc");
		this.order = order;
	}

	/**
	 * 获取排序方式
	 * 
	 * @return
	 */
	public OrderBy getOrderBy() {
		return orderBy;
	}

	/**
	 * 设置排序方向，如果不是asc或者desc则为默认
	 * 
	 * @param orderBy
	 */
	public void setOrderBy(String orderBy) {
		try {
			this.setOrderBy(OrderBy.valueOf(orderBy.toLowerCase()));
			// 错误的orderBy字段，不做任何事
		} catch (IllegalArgumentException e) {
			log.error("get order {}  error", orderBy);
		}

	}

	public Sort orderBy(String order) {
		this.setOrderBy(order);
		return this;
	}

	/**
	 * 设置排序方向
	 * 
	 * @param order
	 */
	protected void setOrderBy(OrderBy order) {
		if (order != null) {
			this.orderBy = order;
		}
	}

	/**
	 * 获取排序字段
	 * 
	 * @return
	 */
	public String getOrder() {
		return order;
	}

}
