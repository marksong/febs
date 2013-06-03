package com.febs.common.orm;

/**
 * 分页信息类
 * @author Mark
 *
 */
public class PaginationProperty {
	public static final String PAGE = "page";
	public static final String PAGE_SIZE = "pageSize";
	public static final String PAGE_NO = "pageNo";
	public static final String ORDER = "order";
	public static final String ORDER_BY = "orderBy";
	public static final String TOTALCOUNT = "totalCount";
	public static final String ORDER_SPLIT = ",";
	public static final int[] PAGE_SIZE_LIST = { 10, 15, 30, 50, 100 };
	protected String pageName = PAGE;
	protected String pageSizeName = PAGE_SIZE;
	protected int[] pageSizeList = PAGE_SIZE_LIST;
	protected String pageNoName = PAGE_NO;
	protected String orderName = ORDER;
	protected String orderByName = ORDER_BY;
	protected String totalCountName = TOTALCOUNT;
	protected String orderSplitChar = ORDER_SPLIT;

	/**
	 * 页面属性名
	 */
	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	/**
	 * 每页显示条数名
	 * 
	 * @return
	 */
	public String getPageSizeName() {
		return pageSizeName;
	}

	/**
	 * 每页显示条数完全名page.pageSize的表现形式
	 * 
	 * @return
	 */
	public String getPageSizeFullName() {
		return getPageName() + '.' + getPageSizeName();
	}

	public void setPageSizeName(String pageSizeName) {
		this.pageSizeName = pageSizeName;
	}

	/**
	 * 页数名
	 * 
	 * @return
	 */
	public String getPageNoName() {
		return pageNoName;
	}

	/**
	 * 页数名完全名page.pageNo的形式
	 * 
	 * @return
	 */
	public String getPageNoFullName() {
		return getPageName() + '.' + getPageNoName();
	}

	public void setPageNoName(String pageNoName) {
		this.pageNoName = pageNoName;
	}

	/**
	 * 排序字段名
	 * 
	 * @return
	 */
	public String getOrderName() {
		return orderName;
	}

	/**
	 * 排序字段完全名page.order
	 * 
	 * @return
	 */
	public String getOrderFullName() {
		return getPageName() + '.' + getOrderName();
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	/**
	 * 排序方式名
	 * 
	 * @return
	 */
	public String getOrderByName() {
		return orderByName;
	}

	/**
	 * 排序方式完全名page.orderby
	 * 
	 * @return
	 */
	public String getOrderByFullName() {
		return getPageName() + '.' + getOrderByName();
	}

	public void setOrderByName(String orderByName) {
		this.orderByName = orderByName;
	}

	/**
	 * 总数名
	 * 
	 * @return
	 */
	public String getTotalCountName() {
		return totalCountName;
	}

	/**
	 * 总数全名page.totalcount的形式
	 * 
	 * @return
	 */
	public String getTotalCountFullName() {
		return getPageName() + '.' + getTotalCountName();
	}

	public void setTotalCountName(String totalCountName) {
		this.totalCountName = totalCountName;
	}

	/**
	 * 切分order的字段
	 * 
	 * @return
	 */
	public String getOrderSplitChar() {
		return orderSplitChar;
	}

	public void setOrderSplitChar(String orderSplitChar) {
		this.orderSplitChar = orderSplitChar;
	}

	/**
	 * 每页显示大小供选择的数字
	 * 
	 * @return
	 */
	public int[] getPageSizeList() {
		return pageSizeList;
	}

	public void setPageSizeList(int[] pageSizeList) {
		this.pageSizeList = pageSizeList;
	}

}
