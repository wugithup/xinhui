package com.shuto.mam.security;

import java.rmi.RemoteException;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;

import psdi.mbo.MboRemote;
import psdi.security.SecurityService;
import psdi.server.MXServer;
import psdi.util.MXAccessException;
/**
 * 
    *  文件名： com.shuto.mam.security.MySecurity.java
    *  说明：逃生系统  用户登录密码验证为ldap密码
    *  创建日期： 2017年8月25日
    *  修改历史 :   
    *     1. [2017年8月25日上午9:29:16] 创建文件 by lull lull@shuto.cn
 */
public class MySecurity extends SecurityService {
	protected void verifyUser(String userName, String enteredPassword, MboRemote userMbo) throws Exception {
		if (!userName.equalsIgnoreCase(userMbo.getString("loginid"))) {
			throw new MXAccessException("access", "invaliduser");
		}

		String storedPassword = userMbo.getString("ldappassword");

		String md5pw = MD5(enteredPassword);
		if (!md5pw.equalsIgnoreCase(storedPassword)) {
			System.out.println(enteredPassword+" "+md5pw + " "+storedPassword);
			throw new MXAccessException("access", "invaliduser");
		}
		System.out.println("------------当前登录人 ：" + userMbo.getString("PERSON.displayname") + " 登陆时间 : "
				+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	}

	public MySecurity(MXServer mxServer) throws RemoteException {
		super(mxServer);
	}

	private String MD5(String inStr) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();

		for (int i = 0; i < md5Bytes.length; i++) {
			int val = md5Bytes[i] & 0xFF;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}
}