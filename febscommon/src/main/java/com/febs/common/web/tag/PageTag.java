package com.febs.common.web.tag;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.febs.common.orm.PaginationProperty;
import com.febs.common.orm.PaginationSupport;

public class PageTag extends SimpleTagSupport {

	
	private Logger logger = LoggerFactory.getLogger(PageTag.class);
	
	private String formId;
	private String pageName;
	
	@Override
	public void doTag() throws JspException, IOException {
		PaginationSupport<?> page = null;
		if (StringUtils.isBlank(pageName)) {
			pageName = PaginationProperty.PAGE;
		}
		page = (PaginationSupport<?>) getJspContext().findAttribute(pageName);
		Assert.isTrue(page != null, "page can't be null");
		Assert.isInstanceOf(PaginationSupport.class, page, "page must be an instance of PaginationSupport");
		JspWriter writer = getJspContext().getOut();
		write(writer, renderJavaScript());
		write(writer, renderPageFormField(page));
		write(writer, renderPager(page));
		super.doTag();
	}
	
	protected void write(JspWriter writer, String text) throws JspException {
		try {
			writer.print(text);
		} catch (IOException e) {
			logger.error("write text to jsp error", e);
			throw new JspException(e.toString(), e);
		}
	}
	
	protected String renderPager(PaginationSupport<?> page) {
		StringBuilder result = new StringBuilder();
		result.append("<div class=\"row-fluid\"><div class=\"span12\"><div class=\"dataTables_info\" id=\"DataTables_Table_0_info\">");
		int pageBegin = page.getStartIndex() + 1;
		int pageEnd = pageBegin + page.getPageSize() > page.getTotalCount() ? page.getTotalCount() : pageBegin + page.getPageSize();
		result.append("Showing " + pageBegin + " to " + pageEnd + " of " + page.getTotalCount() + " entries");
		result.append("</div></div>");
		result.append("<div class=\"span12 center\"><div class=\"dataTables_paginate paging_bootstrap pagination\"><ul>");
		if (page.isHasPre()) {
			result.append("<li class=\"prev\"><a href=\"javascript:goPage(1)\">←← First</a></li>");
			result.append("<li class=\"prev\"><a href=\"javascript:goPage(" + page.getPrePage() + ")\">← Previous</a></li>");
		} else {
			result.append("<li class=\"prev disabled\"><a href=\"#\">←← First</a></li>");
			result.append("<li class=\"prev disabled\"><a href=\"#\">← Previous</a></li>");
		}
		int begin = page.getPageNo() - 7;
		if (begin < 1) begin = 1;
		int end = begin + 14;
		if (end > page.getPageCount()) end = page.getPageCount();
		for (int i = begin; i < end + 1; i++) {
			if (page.getPageNo() == i) {
				result.append("<li class=\"active\"><a href=\"#\">" + i + "</a></li>");
			} else {
				result.append("<li><a href=\"javascript:goPage(" + i + ")\">" + i + "</a></li>");
			}
		}
		if (page.isHasNext()) {
			result.append("<li class=\"next\"><a href=\"javascript:goPage(" + page.getNextPage() + ")\">Next → </a></li>");
			result.append("<li class=\"next\"><a href=\"javascript:goPage(" + page.getPageCount() + ")\">Last →→ </a></li>");
		} else {
			result.append("<li class=\"next disabled\"><a href=\"#\">Next → </a></li>");
			result.append("<li class=\"next disabled\"><a href=\"#\">Last →→ </a></li>");
		}
		result.append("</ul></div></div></div>");
		return result.toString();
	}

	
	protected String renderPageFormField(PaginationSupport<?> page) {
		StringBuilder result = new StringBuilder();
		Map<String, Object> config = getConfig(page);
		for (String key : config.keySet()) {
			result.append("<input type=\"hidden\" id=\"" + key + "\" name=\"" + key + "\" value=\"" + config.get(key) + "\" />");
		}
		return result.toString();
	}

	protected String renderJavaScript() {
		StringBuilder result = new StringBuilder();
		result.append("<script type=\"text/javascript\">");
		result.append("function goPage(pageNum) {");
		result.append("document.getElementById('" + PaginationProperty.PAGE_NO + "').value=pageNum;");
		result.append("document.getElementById('" + formId + "').submit();}");
		result.append("</script>");
		return result.toString();
	}

	/**
	 * 初始化页面js所需的数据
	 * 
	 * @param page
	 * @return
	 */
	private Map<String, Object> getConfig(PaginationSupport<?> page) {
		//PaginationProperty property = page.getProperty();
		Map<String, Object> configs = new HashMap<>();
		// 每页大小
		configs.put(PaginationProperty.PAGE_SIZE, page.getPageSize());
		//configs.put("pageSizeList", property.getPageSizeList());
		// 页数
		//configs.put("pageNoName", property.getPageNoFullName());
		configs.put(PaginationProperty.PAGE_NO, page.getPageNo());
		// 总记录数
//		configs.put("totalRecord", page.getTotalCount());
		// 页面排序方式字段
//		configs.put("orderName", property.getOrderFullName());
//		configs.put("orderByName", property.getOrderByFullName());
		// String[] order = page.getOrderStr();
//		configs.put("orderValue", page.getOrderStr());
//		configs.put("orderByValue", page.getOrderbyStr());
		return configs;
	}

	public String getFormId() {
		return formId;
	}

	public void setFormId(String formId) {
		this.formId = formId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}
	
}
