package com.shuto.mam.webservice.ebs.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

public class InputstreamUtils {

	/**
	 * 将inputStream对象转换为字符
	 * 
	 * @param is
	 * @return
	 */
	public static String inputStream2String(InputStream is) {
		BufferedReader in = null;
		try {
			if (is != null) {
				in = new BufferedReader(new InputStreamReader(is, "utf-8"));
			}
		} catch (java.io.UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			if (in != null) {
				while ((line = in.readLine()) != null) {
					buffer.append(line);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer.toString();
	}

	/**
	 * 向指定sopa url发送参数
	 * 
	 * @param soapurl
	 * @param functionname
	 * @param args
	 * @return
	 */
	public static InputStream sendSoap(String soapurl, String functionname,
			String args) {
		InputStream is = null;
		try {
			URL url = new URL(soapurl);
			URLConnection conn = url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Content-Length",
					String.valueOf(args.getBytes().length));
			conn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
			conn.setRequestProperty("SOAPAction", "http://tempuri.org/"
					+ functionname);
			OutputStream os = conn.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
			osw.write(args);
			osw.flush();
			osw.close();
			is = conn.getInputStream();
		} catch (Exception e) {
			// log.error("获取数据有错误：" + e.toString());
			e.printStackTrace();
		}
		return is;
	}

}
