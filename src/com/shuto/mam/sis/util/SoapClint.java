package com.shuto.mam.sis.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
/**
 * 
com.shuto.mam.sis.util.SoapClint 江苏
* @author       zhaowei  zhaowei@shuoto.cn
* @date         2017年5月16日 下午5:45:44
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class SoapClint {
	public static final int CXF_CLIENT_CONNECT_TIMEOUT = 30 * 1000;  
    public static final int CXF_CLIENT_RECEIVE_TIMEOUT = 300 * 1000;
    public URL serverUrl = null;
    
    public SoapClint(String endpointAddress) {
		try {
			serverUrl = new URL(endpointAddress);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    /**
     * 生成SOAP请求xml
     * @param arg0 要调用方法(getByFormulaStr)的参数
     * @return
     */
    private static String createSoapXml(String arg0) {
    	//替换特殊字符,防止XML在解析时出错
    	if (arg0!=null) {
    		arg0 = arg0.replaceAll("<", "/XY/").replaceAll(">", "/DY/");
    	}
        StringBuilder sb = new StringBuilder();
        sb.append("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.sis.mam.shuto.com/\">");
        sb.append("<soapenv:Header/>");
        sb.append("<soapenv:Body>");
        sb.append("<ser:getByFormulaStr>");
        sb.append("<arg0>"+arg0+"</arg0>");
        sb.append("</ser:getByFormulaStr>");
        sb.append("</soapenv:Body>");
        sb.append("</soapenv:Envelope>");
        return sb.toString();
    }
    
    /**
     * 将返回的XML转换成MAP
     * @param resultxml
     * @return
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    private Map<String,Object> getResutMap(String resultxml) throws ParserConfigurationException, SAXException, IOException {
    	resultxml = resultxml.replace("&lt;", "<");
    	resultxml = resultxml.replace("&gt;", ">");
		StringReader sr = new StringReader(resultxml); 
 		InputSource is = new InputSource(sr); 
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); 
		DocumentBuilder builder=factory.newDocumentBuilder();  
		Document doc = builder.parse(is); 
		NodeList nl= doc.getFirstChild().getChildNodes();
		Node n = null ;
		String resultstr = null;
		Map<String,Object> retmap = null;
		for(int i=0;i<nl.getLength();i++) {
			n=nl.item(i);
			 if("soap:Body".equals(n.getNodeName())){
				 nl=n.getChildNodes();
				 for(int j=0;j<nl.getLength();j++){
					n= nl.item(j);
					if("ns2:getByFormulaStrResponse".equals(n.getNodeName())){
						 nl=n.getChildNodes();
						 for(int k = 0 ; k < nl.getLength() ; k++){
							 n = nl.item(k);
							 if("return".equals(n.getNodeName())){
								 nl=n.getChildNodes();
								 resultstr = n.getTextContent();
							 }
						 }
					}
				 }
			 }
		}
		if (resultstr != null) {
			String [] stringArr= resultstr.split("\\|"); 
			if (stringArr.length == 3) {
				retmap = new HashMap<String,Object>();
				retmap.put("RET", stringArr[0]);
				retmap.put("MSG", stringArr[1]);
				retmap.put("TIMES", stringArr[2]);
			}
		}
		return retmap;
	}
    
    /**
     * 请求SOAP WebService服务
     * @param funcStr 请求方法的参数
     * @return
     * @throws Exception
     */
    public Map<String,Object> callWebService(String funcStr) throws Exception {
		String soapxml = createSoapXml(funcStr);
		HttpURLConnection conn = null;
		// 请求返回内容
		InputStreamReader isr = null;
		BufferedReader br = null;
		try {
			conn = (HttpURLConnection)serverUrl.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Content-Length", String.valueOf(soapxml.getBytes().length));
			conn.setRequestProperty("Content-Type","text/xml; charset=GBK");
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setConnectTimeout(CXF_CLIENT_CONNECT_TIMEOUT);
			conn.setReadTimeout(CXF_CLIENT_RECEIVE_TIMEOUT);
			// 请求输入内容
			OutputStream output = conn.getOutputStream();
			output.write(soapxml.getBytes());
			output.flush();
			output.close();
			int code = conn.getResponseCode(); 
			StringBuilder sb = new StringBuilder();
			if (code == HttpURLConnection.HTTP_OK) {
				// 请求返回内容
				isr = new InputStreamReader(conn.getInputStream());
				br = new BufferedReader(isr);
				String str = null;
				while((str = br.readLine())!=null){
				    sb.append(str + "\n");
				}
			}
			return getResutMap(sb.toString());
		} finally {
			if (isr != null) {
				 isr.close();
			}
			if (br != null) {
				br.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
	}
}
