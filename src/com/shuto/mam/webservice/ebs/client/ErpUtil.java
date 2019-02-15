/**
 * 
 */
package com.shuto.mam.webservice.ebs.client;

import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerFactoryConfigurationError;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.shuto.mam.webservice.common.service.impl.CommonServiceImpl;
import com.shuto.mam.webservice.common.util.Md5Util;
import com.shuto.mam.webservice.common.util.XmlUtil;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

/**
 * @author Administrator
 *
 */
public class ErpUtil {

	/**
	 * 上传工单领料数据
	 * @param mainMbo
	 * @return
	 * @throws RemoteException
	 * @throws SQLException
	 * @throws MXException
	 */
	public Map<String,Map<String,String>> uploadWo(MboRemote mainMbo) throws RemoteException, SQLException, MXException {
		StringBuffer logSbf = new StringBuffer("===============计划领料单上传==============\n");
		String interfaceException = "" ;
		String synchstatus = "成功" ;
		ErpSoapClint esc = new ErpSoapClint();
		String planXml = "" ;
		String lldXml = "" ;
		String llRetXml="";
		String jhRetXml = "";
		ErpBase64 b64 = new ErpBase64();
		Map<String,String> jhreturnmap = null;
		Map<String,String> llreturnmap = null;
		try{
			planXml = getUploadXml(mainMbo,"QUERYWOPLAN");
			logSbf.append("\n~~~~~~~~~~上传信息(加密前)：\n").append(planXml);
			planXml = b64.encode(planXml);
			logSbf.append("\n~~~~~~~~~~上传信息(加密后)：\n").append(planXml);
			jhRetXml = esc.getXML("05INV1007", "000320140610000000000004", planXml);
			logSbf.append("\n~~~~~~~~~~返回信息：\n").append(jhRetXml);
			jhreturnmap = getReturnMap(jhRetXml);
			if (jhreturnmap == null) {
				synchstatus = "失败" ;
			} else {
				if (!"1".equals(jhreturnmap.get("resultcode"))) {
					synchstatus = "失败" ;
				} else {
					logSbf.append("\n=====领料单数据上传：=====\n");
					lldXml = getUploadXml(mainMbo,"QUERYWOMATER");
					logSbf.append("\n~~~~~~~~~~上传信息(加密前)：\n").append(lldXml);
					lldXml = b64.encode(lldXml);
					logSbf.append("\n~~~~~~~~~~上传信息(加密后)：\n").append(lldXml);
					llRetXml = esc.getXML("05INV1008", "000320140610000000000004", lldXml);
					logSbf.append("\n~~~~~~~~~~返回信息：\n").append(llRetXml);
					llreturnmap = getReturnMap(llRetXml);
					if (llreturnmap != null) {
						if (!"1".equals(llreturnmap.get("resultcode"))) {
							synchstatus = "失败" ;
						}
					} else {
						synchstatus = "失败" ;
					}
				}
			}
		} catch(Exception e){
			interfaceException = e.getMessage();
			synchstatus = "失败" ;
			e.printStackTrace();
		} finally {
			addLog(mainMbo,"workorderid","ERP计划领料单上传","上传",interfaceException,synchstatus,logSbf.toString());
		}
		Map<String,Map<String,String>> msgMap = new HashMap<String,Map<String,String>>();
		msgMap.put("JHRET", jhreturnmap);
		msgMap.put("LLRET", llreturnmap);
		return msgMap;
	}
	
	/**
	 * 取消工单计划
	 * @param mainMbo
	 * @return
	 * @throws RemoteException
	 * @throws SQLException
	 * @throws MXException
	 */
	public Map<String,Map<String,String>> cancelWo(MboRemote mainMbo) throws RemoteException, SQLException, MXException {
		StringBuffer logSbf = new StringBuffer("===============计划领料单取消==============\n");
		String interfaceException = "" ;
		String synchstatus = "成功" ;
		ErpSoapClint esc = new ErpSoapClint();
		String planXml = "";
		String lldXml = "";
		String llRetXml="";
		String jhRetXml = "";
		ErpBase64 b64 = new ErpBase64();
		Map<String,String> jhreturnmap = null;
		Map<String,String> llreturnmap = null;
		try {
			logSbf.append("\n=====领料单取消：=====\n");
			lldXml = getUploadXml(mainMbo,"QUERYWOMATER");
			logSbf.append("\n~~~~~~~~~~上传信息(加密前)：\n").append(lldXml);
			lldXml = b64.encode(lldXml);
			logSbf.append("\n~~~~~~~~~~上传信息(加密后)：\n").append(lldXml);
			llRetXml = esc.getXML("05INV1008", "000320140610000000000004", lldXml);
			logSbf.append("\n~~~~~~~~~~返回信息：\n").append(llRetXml);
			llreturnmap = getReturnMap(llRetXml);
	
			logSbf.append("\n=====领料计划取消：=====\n");
			planXml = getUploadXml(mainMbo,"QUERYWOPLAN");
			logSbf.append("\n~~~~~~~~~~上传信息(加密前)：\n").append(planXml);
			planXml = b64.encode(planXml);
			logSbf.append("\n~~~~~~~~~~上传信息(加密后)：\n").append(planXml);
			jhRetXml = esc.getXML("05INV1007", "000320140610000000000004", planXml);
			logSbf.append("\n~~~~~~~~~~返回信息：\n").append(jhRetXml);
			jhreturnmap = getReturnMap(jhRetXml);
			if (llreturnmap == null || jhreturnmap == null) {
				synchstatus = "失败" ;
			} else {
				if (!"1".equals(llreturnmap.get("resultcode")) || !"1".equals(jhreturnmap.get("resultcode"))) {
					synchstatus = "失败" ;
				}
			}
		} catch(Exception e){
			interfaceException = e.getMessage();
			synchstatus = "失败" ;
			e.printStackTrace();
		} finally {
			addLog(mainMbo,"workorderid","ERP计划领料单取消","上传",interfaceException,synchstatus,logSbf.toString());
		}
		Map<String,Map<String,String>> msgMap = new HashMap<String,Map<String,String>>();
		msgMap.put("JHRET", jhreturnmap);
		msgMap.put("LLRET", llreturnmap);
		return msgMap;
	}
	
	/**
	 * 获取上传XML
	 * @param servicename
	 * @param wonum
	 * @return
	 */
	public String getUploadXml(MboRemote mainMbo,String servicename) {
		String xml = null;
		try {
			MboSetRemote servicepartner = mainMbo.getMboSet("$servicepartner", "SERVICEPARTNER", "PARTNERID='CRPEAM'");
			if (servicepartner != null && !servicepartner.isEmpty()) {
				//对接系统(CRPEAM)标识ID
				String partnerid = servicepartner.getMbo(0).getString("partnerid");
				//密钥
				String secretkey = servicepartner.getMbo(0).getString("secretkey");
				//工单号
				String wonum = mainMbo.getString("wonum");
				//接口签名
				String md5str = Md5Util.md5(secretkey+partnerid+wonum);
				CommonServiceImpl commonservice = new CommonServiceImpl();
				String parameter = "<query><servicename>"+servicename+"</servicename><partnerid>"+partnerid+"</partnerid><sign>"+md5str+"</sign><wonum>"+wonum+"</wonum></query>";
				String outstr = commonservice.query(parameter);
				if (outstr != null) {
					Document doc = DocumentHelper.parseText(outstr.toString());
					Element root = doc.getRootElement();
					Element data = (Element) root.selectSingleNode("//data"); 
					if (data != null) {
						@SuppressWarnings("unchecked")
						List<Element> list = data.elements();
						if (list != null && list.size() == 1) {
							xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>"+list.get(0).asXML();
						}
					}
				}
			}
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (MXException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return xml;
	}
	
	/**
	 * 获取接口返回信息
	 * @param xmlstr
	 * @return
	 */
	public Map<String,String> getReturnMap(String xmlstr) {
		Map<String,String> returnmap = null;
		if (xmlstr != null) {
			xmlstr = xmlstr.replace("&lt;", "<");
			xmlstr = xmlstr.replace("&gt;", ">");
			try {
				Document doc = DocumentHelper.parseText(xmlstr.toString());
				Element root = doc.getRootElement();
				Element body = (Element) root.selectSingleNode("env:Body"); 
				if (body != null) {
					Element op = body.element("OutputParameters");
					if (op != null) {
						Element responsedate = op.element("X_RESPONSE_DATA");
						if (responsedate != null) {
							Element result = responsedate.element("result");
							if (result != null) {
								Map<String,Object> resultmap = XmlUtil.xml2mapWithAttr(result.asXML(), true);
								if (resultmap != null) {
									returnmap = (Map<String, String>) resultmap.get("result");
								}
							}
						}
					}
				}
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnmap;
	}
	
	/**
	 * 
	 * @param mainMbo 主对象
	 * @param mainid  主对象ID
	 * @param desc    接口日志描述（值如：“***上传”）
	 * @param synchtype   接口日志类型（如上传、下载、验证）
	 * @param synchdesc   接口日志异常信息记录
	 * @param synchstatus    接口状态
	 * @param log
	 * @throws RemoteException
	 * @throws SQLException
	 * @throws MXException
	 */
	public void addLog(MboRemote mainMbo ,String mainid,String desc , String synchtype,String synchdesc,String synchstatus,String datalog) throws RemoteException, SQLException, MXException{
		MboSetRemote logSet = mainMbo.getMboSet("#interfacelog"+mainMbo.getString(mainid),"interfacelog","1!=1");
		MboRemote logMbo = logSet.add(2L);
		logMbo.setValue("SYNCHTYPE", synchtype,2l);
		logMbo.setValue("SYNCHDATE",new Date(),2l);
		logMbo.setValue("SYNCHDESC", synchdesc,2L);
		logMbo.setValue("SYNCHSTATUS", synchstatus,2l);
		logMbo.setValue("DESCRIPTION", desc,2l);
		logMbo.setValue("datalog", datalog,2l);
		logSet.save(2l);
	}
	
	public static void main(String[] args) throws TransformerFactoryConfigurationError, Exception {
		Document doc = DocumentHelper.parseText("<query><servicename>QUERYWOPLAN</servicename><partnerid>CRPEAM</partnerid><sign>8A8770015D283AE495A967517CC515D4</sign><wonum>207777</wonum></query>".toString());  
        Element root = doc.getRootElement();
        System.out.println(root.elementText("servicename"));
	}
}
