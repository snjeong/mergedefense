package com.fruttidino.api.common.util;

import com.fruttidino.api.common.exception.ApplicationException;
import com.fruttidino.api.common.exception.ErrorCode;
import org.slf4j.Logger;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

public class Utils {
	public static String LPad(String str, Integer length, char car) {
		// 같을 경우 반환
		if(str.length()==length) {
			return str;
		}
		// 클경우  substirn
		if(str.length()>length) {
			return str.substring(0, length);
		}
		return str + String.format("%" + (length - str.length()) + "s" , "").replace(" ", String.valueOf(car));
	}

	public static String RPad(String str, Integer length, char car) {
		// 같을 경우 반환
		if(str.length()==length) {
			return str;
		}
		// 클경우  substirn
		if(str.length()>length) {
			return str.substring(0, length);
		}
		return String.format("%" + (length - str.length()) + "s" , "").replace(" ", String.valueOf(car)) + str;
	}
	
	public static String getTxId() throws UnknownHostException {
		String hostName = InetAddress.getLocalHost().getHostName();
		if(hostName.length() > 18) {
			hostName = hostName.substring(0, 18);
		}
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return LPad(hostName, 18, 'X') + uuid;
	}

	public static void throwApplicationException(Logger logger, String errMsg, ErrorCode errorCode) throws ApplicationException {
		logger.error(errMsg);
		throw new ApplicationException(errorCode);
	}

}

