package com.febs.common.utils;

import java.util.UUID;

/**
 * UUID生成
 * @author Mark Song
 *
 */
public final class UUIDSupport {

	private UUIDSupport() {}
	
    public static String getUUID() {
        return UUID.randomUUID().toString();
    }

    /**
     * 得到消除"-"号后的UUID
     * @return
     */
    public static String getUUIDWithoutMinus() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
