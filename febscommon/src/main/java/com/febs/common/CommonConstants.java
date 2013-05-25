package com.febs.common;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class CommonConstants {
	
	private static final Logger log = LoggerFactory.getLogger(CommonConstants.class);
	public static PropertiesConfiguration CONFIG;
	public static final String PROPERTIES_FILE = "application.properties";

	static {
		try {
			CONFIG = new PropertiesConfiguration(PROPERTIES_FILE);
		} catch (ConfigurationException e) {
			log.error("load {} fail", PROPERTIES_FILE, e);
		}
	}

	public static final String DEFAULT_CHARSET = "UTF-8";

	public static final String PAGE_PAGESIZE = "pagination.pageSize";
	public static final String PAGE_STARTINDEX = "pagination.startIndex";
	public static final String PAGE_TOTALCOUNT = "pagination.totalCount";
	public static final int PAGE_SIZE = CONFIG.getInt("page.size.default");

}
