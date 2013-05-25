package com.febs.common.orm;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;

import com.febs.common.CommonConstants;

/**
 * 分页类
 * @author Mark
 *
 * @param <T>
 */
public class PaginationSupport<T> implements Serializable {

	private static final long serialVersionUID = -735944042549779183L;
	public final static int PAGE_SIZE = CommonConstants.PAGE_SIZE;
	/**
	 * page相关属性
	 */
	protected PaginationProperty property;
	// 每页多少条记录
	private int pageSize = PAGE_SIZE;
	private List<T> items = new ArrayList<T>();
	// -1表示未设置
	private int totalCount;
	// 开始页数
	private int pageNo;
	// 排序
	private List<Sort> order = Collections.emptyList();

	public PaginationSupport() {
		this(PAGE_SIZE);
	}

	public PaginationSupport(int pageSize) {
		this(pageSize, 1);
	}

	public PaginationSupport(int pageSize, int pageNo) {
		super();
		setPageNo(pageNo);
		setPageSize(pageSize);
		setTotalCount(-1);
		setProperty(new PaginationProperty());

	}

	public List<T> getItems() {
		return items;
	}

	public void setItems(List<? extends T> items) {
		this.items = new ArrayList<T>(items);

	}

	public PaginationSupport<T> items(List<? extends T> items) {
		this.setItems(items);
		return this;

	}

	/**
	 * 计算总页数,如果{@link #isHasTotalCount}为false 则返回-1
	 */
	public int getPageCount() {
		if (!isHasTotalCount())
			return -1;
		return (int) Math.ceil((double) totalCount / (double) pageSize);
	}

	/**
	 * 获取当前页数(从1开始)
	 * 
	 * @return
	 */
	public int getPageNo() {
		return pageNo;
	}

	/**
	 * 设置当前页,小于1就保持不变，超过{@link #getPageCount()}
	 * 
	 * @param pageNo
	 */
	public void setPageNo(int pageNo) {
		if (pageNo > 0) {
			this.pageNo = pageNo;
			this.valiPageNo();
		}
	}

	public PaginationSupport<T> pageNo(int pageNo) {
		setPageNo(pageNo);
		return this;
	}

	/**
	 * 设置每页显示记录数，如果小于0则保持不变
	 * 
	 * @param pageSize
	 */
	public void setPageSize(int pageSize) {
		if (pageSize > 0) {
			this.pageSize = pageSize;
			// 设置每页显示数后，验证页数
			this.valiPageNo();
		}
	}

	public PaginationSupport<T> pageSize(int pageSize) {
		setPageSize(pageSize);
		return this;
	}

	/**
	 * 获取每页记录数
	 * 
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * 获取总记录数，如果没有初始化则返回-1
	 * 
	 * @return
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * 设置记录总数，如果小于等于0，则设置为未初始化状态，如果比startIndex小，则将startIndex设为0，防止空记录
	 * 
	 * @param totalCount
	 */
	public void setTotalCount(int totalCount) {
		if (totalCount > 0) {
			this.totalCount = totalCount;
		}
		// 如果开始记录大于最大记录，startIndex设为0
		if (totalCount > 0 && totalCount < (getStartIndex() + 1)) {
			this.setPageNo(1);
		}
	}

	/**
	 * 总记录数是否有效
	 * 
	 * @return
	 */
	public boolean isHasTotalCount() {
		return this.totalCount > 0;
	}

	/**
	 * 获取开始序号，第一条从0开始
	 * 
	 * @return
	 */
	public int getStartIndex() {
		return (getPageNo() - 1) * getPageSize();
	}

	/**
	 * 验证函数，保证在有totalCount的情况下当前页不超过总页数，否则当前页设为1
	 */
	private void valiPageNo() {
		if (isHasTotalCount() && pageNo > getPageCount()) {
			this.pageNo = 1;
		}
	}

	/**
	 * 是否还有下一页.
	 */
	public boolean isHasNext() {
		return pageNo < getPageCount();
	}

	/**
	 * 取得下页的页号, 序号从1开始. 当前页为尾页时仍返回尾页序号.
	 */
	public int getNextPage() {
		if (isHasNext())
			return pageNo + 1;
		else
			return pageNo;
	}

	/**
	 * 是否还有上一页.
	 */
	public boolean isHasPre() {
		return pageNo > 1;
	}

	/**
	 * 取得上页的页号, 序号从1开始. 当前页为首页时返回首页序号.
	 */
	public int getPrePage() {
		if (isHasPre())
			return pageNo - 1;
		else
			return pageNo;
	}

//	/**
//	 * 获得排序.获取的list不能进行add或者remove等修改操作
//	 */
//	public List<Sort> getOrder() {
//		// 初始化排序方式
//		if (isHasOrder()) {
//			String[] orderBy = this.orderBy.split(getProperty().getOrderSplitChar());
//			// 如果orderBy数超过order.size(),则只改order.size()数量排序方式
//			int length = Math.min(orderBy.length, order.size());
//			for (int i = 0; i < length; i++) {
//				order.get(i).setOrderBy(orderBy[i]);
//			}
//			this.orderByInited = true;
//		}
//		return order;
//	}

//	/**
//	 * 获取排序字段在页面上的显示方式
//	 * 
//	 * @return
//	 */
//	public String getOrderStr() {
//		List<Sort> orderList = getOrder();
//		StringBuilder orderStr = new StringBuilder();
//		for (Sort order : orderList) {
//			if (orderStr.length() > 0)
//				orderStr.append(getProperty().getOrderSplitChar());
//			orderStr.append(order.getOrder());
//		}
//		return orderStr.toString();
//	}

//	/**
//	 * 获取排序方式在页面上的显示方式
//	 * 
//	 * @return
//	 */
//	public String getOrderbyStr() {
//		List<Sort> orderList = getOrder();
//		StringBuilder orderStr = new StringBuilder();
//		for (Sort order : orderList) {
//			if (orderStr.length() > 0)
//				orderStr.append(getProperty().getOrderSplitChar());
//			orderStr.append(order.getOrderBy());
//		}
//		return orderStr.toString();
//	}

	/**
	 * 设置对哪些字段排序，可以用{@link PaginationProperty #orderSplitChar}分隔.
	 * 
	 * @param order
	 */
	public void setOrder(final String order) {
		Assert.hasText(order, "order字段不能为空");
		String[] orders = order.split(getProperty().getOrderSplitChar());
		List<Sort> result = new ArrayList<Sort>(orders.length);
		for (String orderName : orders) {
			result.add(new Sort(orderName));
		}
		this.order = Collections.unmodifiableList(result);

	}

	/**
	 * 设置排序的字段，调用setOrder
	 * 
	 * @param order
	 * @return
	 * @see {@link #setOrder(String)}
	 */
	public PaginationSupport<T> order(final String order) {
		setOrder(order);
		return this;
	}

	public PaginationSupport<T> order(final Sort oneOrder, final Sort... orders) {
		this.order = new ArrayList<Sort>();
		this.order.add(oneOrder);
		if (ArrayUtils.isNotEmpty(orders)) {
			for (Sort order : orders) {
				this.order.add(order);
			}
		}
		return this;
	}

	/**
	 * 是否有排序字段
	 * 
	 * @return
	 */
	public boolean isHasOrder() {
		return !this.order.isEmpty();
	}

	/**
	 * 是否为空
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		if (getTotalCount() == 0) {
			return true;
		} else if (!isHasTotalCount()) {
			return (this.getItems().isEmpty() && this.getPageNo() == 1);
		}
		return true;
	}

	/**
	 * 页面属性
	 * 
	 * @return
	 */
	public PaginationProperty getProperty() {
		return property;
	}

	public void setProperty(PaginationProperty property) {
		this.property = property;
	}

}
