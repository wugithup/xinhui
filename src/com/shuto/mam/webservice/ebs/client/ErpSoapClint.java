package com.shuto.mam.webservice.ebs.client;

import java.io.InputStream;

public class ErpSoapClint {
	
	public String getXML(String pifaccode , String pbatchnumber , String xml) {
		//获取xml
		InputStream inxml = this.getClass().getResourceAsStream("erpservice.xml");
		String argsxml = InputstreamUtils.inputStream2String(inxml);
		//替换参数
		argsxml = argsxml.replaceAll("PIFACECODE",pifaccode);
		argsxml = argsxml.replaceAll("PBATCHNUMBER",pbatchnumber);
		argsxml = argsxml.replaceAll("PREQUESTDATA",xml);
		//UAT测试地址
//		InputStream res = InputstreamUtils.sendSoap(
//				"http://fmsdluat.crc.com.cn:8006/webservices/SOAProvider/plsql/cux_0_ws_server_prg/",	
//				"INVOKEFMSWS", argsxml);
		//SIT
		InputStream res = InputstreamUtils.sendSoap(
				"http://crpsit.crc.com.cn:8004/webservices/SOAProvider/plsql/cux_0_ws_server_prg/",	
				"INVOKEFMSWS", argsxml);
		
		//正式地址
//		InputStream res = InputstreamUtils.sendSoap(
//				"http://fmsdlap.crc.com.cn:8008/webservices/SOAProvider/plsql/cux_0_ws_server_prg/",
//				"INVOKEFMSWS", argsxml);
		String retxml = InputstreamUtils.inputStream2String(res);
		return retxml;
	}
	
	public static void main(String[] args) throws Exception {
		ErpSoapClint t = new ErpSoapClint();
		System.out.println(" 返回结果：" + t.getXML("","",""));

	}

}
