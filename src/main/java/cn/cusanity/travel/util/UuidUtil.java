package cn.cusanity.travel.util;

import java.util.UUID;

/**
 * Generates UUID random strings
 */
public final class UuidUtil {
	private UuidUtil(){}
	public static String getUuid(){
		return UUID.randomUUID().toString().replace("-","");
	}
}
