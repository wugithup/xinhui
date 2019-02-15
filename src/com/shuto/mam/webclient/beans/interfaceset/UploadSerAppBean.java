package com.shuto.mam.webclient.beans.interfaceset;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import com.shuto.mam.webservice.common.bean.Node;
import com.shuto.mam.webservice.common.bean.Parameter;
import com.shuto.mam.webservice.common.bean.Service;
import com.shuto.mam.webservice.common.service.impl.CommonServiceImpl;
import com.shuto.mam.webservice.common.util.JcacheUtil;
import com.shuto.mam.webservice.common.util.JsonUtil;
import com.shuto.mam.webservice.common.util.XmlUtil;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationWarningException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.WebClientEvent;

/**
 * @Title: UploadSerAppBean.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-10 下午04:15:58 
 * @version V1.0 
 */
public class UploadSerAppBean extends AppBean {
	
	/**
	 * 数据库连接测试
	 * @Title: TESTDBCONNT  
	 * @Description: TODO  
	 * @throws RemoteException
	 * @throws MXException void
	 * @throws
	 */
	public void TESTDBCONNT() throws RemoteException, MXException {
		MboRemote service = this.getMbo();
		String dburl = service.getString("dburl");
		String dbuser = service.getString("dbuser");
		String dbpwd = service.getString("dbpwd");
		Connection conn = null;
		PreparedStatement spstatement = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(dburl, dbuser, dbpwd);
			spstatement = conn.prepareStatement("select * from dual");
			spstatement.executeQuery();
			throw new MXApplicationWarningException("SUCCESS", "测试成功!");	
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MXApplicationException("", e.getMessage());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new MXApplicationException("", e.getMessage());
		} finally {
			 try {
				if (rs != null) {
					rs.close();
				}
				if (spstatement != null) {
					spstatement.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 生成接口服务输入输出格式模板
	 * @Title: CREATEIOFORMAT  
	 * @Description: TODO   void
	 * @throws
	 */
	public void CREATEIOFORMAT() {
		try {
			MboRemote servicembo = this.getMbo();
			Service uploadservice = new Service();
			uploadservice.setServiceName(servicembo.getString("servicename"));
			uploadservice.setServiceType(servicembo.getString("servicetype"));
			uploadservice.setIoFormat(servicembo.getString("ioformat"));
			uploadservice.setYnpage(servicembo.getBoolean("ynpage"));
			uploadservice.setMaxPageCount(servicembo.getInt("maxpagecount"));
			MboSetRemote childmostrootnodes = servicembo.getMboSet("CHILDMOSTROOTNODE");
			childmostrootnodes.setOrderBy("nodenum");
			if (childmostrootnodes != null && !childmostrootnodes.isEmpty()) {
				Node rootnode = new Node();
				initNode(childmostrootnodes.getMbo(0),rootnode);
				uploadservice.setRootNode(rootnode);
			}
			String inputtemplet = showInFormat(uploadservice);
			String outtemplet = showOutFormat(uploadservice);
			servicembo.setValue("inputtemplet", inputtemplet);
			servicembo.setValue("outtemplet", outtemplet);
			super.SAVE();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 接口服务测试
	 * @Title: TESTSERVICE  
	 * @Description: TODO   void
	 * @throws MXException 
	 * @throws RemoteException 
	 */
	public void TESTSERVICE() throws RemoteException, MXException {
		long begin,end;
		//开始计时
		begin = System.currentTimeMillis();
		
		MboRemote service = this.getMbo();
		String testinput = service.getString("testinput");
		CommonServiceImpl commonservice = new CommonServiceImpl();
		String outstr = commonservice.serviceTest(testinput);
		
		//结束计时
        end = System.currentTimeMillis();
        
        service.setValue("testtime", (end-begin)+"");
		service.setValue("testout", outstr,2l);
		this.reloadTable();
	}
	
	/**
	 * 发布接口服务
	 * @Title: PUBLISHSERVICE  
	 * @Description: TODO   void
	 * @throws MXException 
	 * @throws RemoteException 
	 */
	public void PUBLISHSER() throws RemoteException, MXException {
		WebClientEvent event = clientSession.getCurrentEvent();
		int msgRet = event.getMessageReturn();
		if (msgRet < 0 ) {	
			throw new MXApplicationYesNoCancelException("BMXAA9312E","service", "publishser");	
		}
		if (msgRet == 8) {
			MboRemote servicembo = this.getMbo();
			CommonServiceImpl commonservice = new CommonServiceImpl();
			Service service = commonservice.createService(servicembo.getString("servicename"));
			if (service != null) {
				//将接口服务加载到缓存
				JcacheUtil.putServiceToCache(service);
			}
			this.clientSession.showMessageBox(event, "system", "publishserrecord", null);
		} else {
			return;
		}
	}
	
	/**
	 * 封装节点数据
	 * @Title: initNode  
	 * @Description: TODO  
	 * @param rootmbo
	 * @param rootnode
	 * @throws RemoteException
	 * @throws MXException void
	 * @throws
	 */
	private void initNode(MboRemote rootmbo,Node rootnode) throws RemoteException, MXException {
		if (rootmbo != null) {
			//子节点
			MboSetRemote subnodes = rootmbo.getMboSet("SUBNODE");
			//节点对应的参数
			MboSetRemote nodeparameters = rootmbo.getMboSet("$serviceparameter","serviceparameter","servicenum=:servicenum and nodenum in (select nodenum from servicenode where servicenum=:servicenum and nodename=:nodename)");
			nodeparameters.setOrderBy("nodenum,snum");
			
			//节点对应的参数map
			Map<String,Parameter> parameters = new LinkedHashMap<String,Parameter>();
			//节点对应的子节点
			List<Node> subnodelist = new LinkedList<Node>(); 
			//封装节点信息
			rootnode.setNodeNum(rootmbo.getString("nodenum"));
			rootnode.setNodeName(rootmbo.getString("nodename"));
			rootnode.setLayer(rootmbo.getInt("layer"));
			rootnode.setRemark(rootmbo.getString("remark"));
			rootnode.setParentNum(rootmbo.getString("parentnum"));
			rootnode.setTableName(rootmbo.getString("tablename"));
			rootnode.setParameters(parameters);
			rootnode.setSubNodes(subnodelist);
			//封装节点参数map
			Parameter nodeparameter = null;
			for (int i=0;i<nodeparameters.count();i++) {
				nodeparameter = new Parameter();
				nodeparameter.setSnum(nodeparameters.getMbo(i).getInt("snum"));
				nodeparameter.setName(nodeparameters.getMbo(i).getString("name"));
				nodeparameter.setFaceName(nodeparameters.getMbo(i).getString("facename"));
				nodeparameter.setType(nodeparameters.getMbo(i).getString("type"));
				nodeparameter.setValue(nodeparameters.getMbo(i).getString("value"));
				nodeparameter.setRemark(nodeparameters.getMbo(i).getString("remark"));
				nodeparameter.setSourceType(nodeparameters.getMbo(i).getString("sourcetype"));
				nodeparameter.setSequence(nodeparameters.getMbo(i).getBoolean("issequence"));
				nodeparameter.setAutoNumber(nodeparameters.getMbo(i).getBoolean("isautonumber"));
				nodeparameter.setFromParent(nodeparameters.getMbo(i).getBoolean("isFromParent"));
				nodeparameter.setRequired(nodeparameters.getMbo(i).getBoolean("isrequired"));
				parameters.put(nodeparameter.getFaceName(), nodeparameter);
			}
			
			//递归调用,封装子节点
			if (subnodes != null && !subnodes.isEmpty()) {
				Node subnode = null; 
				for (int k=0;k<subnodes.count();k++) {
					subnode = new Node();
					subnodelist.add(subnode);
					initNode(subnodes.getMbo(k),subnode);
				}
			}
		}
	}
	
	/**
	 * 显示接口输入格式
	 * @param service
	 * @param type
	 * @return
	 */
	private static String showInFormat(Service service) {
		String str = null;
		Node node = service.getRootNode();
		String type = service.getIoFormat();
		if (node != null) {
			Map<String, Object> inputmap = new LinkedHashMap<String, Object>();
			Map<String, Object> uploadmap = new LinkedHashMap<String, Object>();
			Map<String, Object> uploaddatamap = new LinkedHashMap<String, Object>();
			inputmap.put("upload", uploadmap);
			uploadmap.put("servicename", "接口服务名");
			uploadmap.put("partnerid", "接口系统标识");
			uploadmap.put("sign", "接口签名");
			uploadmap.put("data", uploaddatamap);
			createReturnMap(node,uploaddatamap);
			try {
				if ("xml".equalsIgnoreCase(type)) {
					str = XmlUtil.formatXml(XmlUtil.map2xml(inputmap));
				} else if("json".equalsIgnoreCase(type)) {
					str = JsonUtil.obj2Json(inputmap);
				} else {
					str = XmlUtil.formatXmlNoHead(XmlUtil.map2xml(inputmap));
				}
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return str;
	}
	
	/**
	 * 显示接口输出格式
	 * @param node
	 * @param type
	 * @return
	 */
	private static String showOutFormat(Service service) {
		String str = null;
		Node node = service.getRootNode();
		String type = service.getIoFormat();
		if (node != null) {
			Map<String, Object> returnmap = new LinkedHashMap<String, Object>();
			Map<String, Object> resultmap = new LinkedHashMap<String, Object>();
			returnmap.put("result", resultmap);
			resultmap.put("resultcode", "接口返回代码");
			resultmap.put("resultmsg", "接口返回提示");
			try {
				if ("xml".equalsIgnoreCase(type)) {
					str = XmlUtil.formatXml(XmlUtil.map2xml(returnmap));
				} else if("json".equalsIgnoreCase(type)) {
					str = JsonUtil.obj2Json(returnmap);
				} else {
					str = XmlUtil.formatXmlNoHead(XmlUtil.map2xml(returnmap));
				}
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return str;
	}
	
	/**
	 * 封装返回格式MAP
	 * @param node
	 * @param map
	 */
	private static void createReturnMap(Node node,Map<String, Object> map) {
		if (node != null) {
			String nodename = node.getNodeName();
			Map<String,Parameter> parameters = node.getParameters();
			Map<String,Map<String, Object>> outattrmap = null;
			Map<String, Object> parametrmap = null;
			if (parameters != null && !parameters.isEmpty()) {
				outattrmap = new LinkedHashMap<String,Map<String, Object>>();
				parametrmap = new LinkedHashMap<String, Object>(); 
				outattrmap.put(nodename,parametrmap);
				map.put(nodename+"s", outattrmap);
				for (Parameter p:parameters.values()) {
					if ("参数".equals(p.getSourceType()) && !p.isSequence() && !p.isAutoNumber() && !p.isFromParent()) {
						parametrmap.put(p.getFaceName(), p.getRemark());
					}
				}
			}
			List<Node> subnodes = node.getSubNodes();
			if (subnodes != null && !subnodes.isEmpty()) {
				for (Node n:subnodes) {
					if (parametrmap != null) {
						createReturnMap(n,parametrmap);
					}
				}
			}
		}
	}
}
