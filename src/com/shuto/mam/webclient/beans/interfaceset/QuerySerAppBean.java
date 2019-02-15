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
import com.shuto.mam.webservice.common.bean.Attribute;
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
 * @Title: QuerySerAppBean.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-10 下午04:15:58 
 * @version V1.0 
 */
public class QuerySerAppBean extends AppBean {
	
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
			Service queryservice = new Service();
			queryservice.setServiceName(servicembo.getString("servicename"));
			queryservice.setServiceType(servicembo.getString("servicetype"));
			queryservice.setIoFormat(servicembo.getString("ioformat"));
			queryservice.setYnpage(servicembo.getBoolean("ynpage"));
			queryservice.setMaxPageCount(servicembo.getInt("maxpagecount"));
			MboSetRemote rootnodes = servicembo.getMboSet("ROOTNODE");
			if (rootnodes != null && !rootnodes.isEmpty()) {
				Node rootnode = new Node();
				initNode(rootnodes.getMbo(0),rootnode);
				queryservice.setRootNode(rootnode);
			}
			String inputtemplet = showInFormat(queryservice);
			String outtemplet = showOutFormat(queryservice);
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
			MboSetRemote nodeparameters = rootmbo.getMboSet("NODEPARAMETER");
			nodeparameters.setOrderBy("snum");
			//节点对应的属性
			MboSetRemote nodeattributes = rootmbo.getMboSet("NODEATTRIBUTE");
			nodeattributes.setOrderBy("ordernum");
			
			//节点对应的参数map
			Map<String,Parameter> parameters = new LinkedHashMap<String,Parameter>();
			//节点对应的属性list
			List<Attribute> attributes = new LinkedList<Attribute>();
			//节点对应的子节点
			List<Node> subnodelist = new LinkedList<Node>(); 
			//封装节点信息
			rootnode.setNodeNum(rootmbo.getString("nodenum"));
			rootnode.setNodeName(rootmbo.getString("nodename"));
			rootnode.setLayer(rootmbo.getInt("layer"));
			rootnode.setRemark(rootmbo.getString("remark"));
			rootnode.setParentNum(rootmbo.getString("parentnum"));
			rootnode.setParameters(parameters);
			rootnode.setAttributes(attributes);
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
				nodeparameter.setRequired(nodeparameters.getMbo(i).getBoolean("isrequired"));
				parameters.put(nodeparameter.getFaceName(), nodeparameter);
			}
			//封装节点属性list
			Attribute attribute = null;
			for (int j=0;j<nodeattributes.count();j++) {
				attribute = new Attribute();
				attribute.setValueType(nodeattributes.getMbo(j).getString("valuetype"));
	            attribute.setTableName(nodeattributes.getMbo(j).getString("tablename"));
	            attribute.setColumnName(nodeattributes.getMbo(j).getString("columnname"));
	            attribute.setColumAlias(nodeattributes.getMbo(j).getString("columalias"));
	            attribute.setFaceName(nodeattributes.getMbo(j).getString("facename"));
	            attribute.setColumnType(nodeattributes.getMbo(j).getString("columntype"));
	        	attribute.setColumnSize(nodeattributes.getMbo(j).getString("columnsize"));
	            attribute.setSourceType(nodeattributes.getMbo(j).getString("sourcetype"));
	            attribute.setRemark(nodeattributes.getMbo(j).getString("remark"));
	            attribute.setValue(nodeattributes.getMbo(j).getString("value"));
	            attribute.setRequired(nodeattributes.getMbo(j).getBoolean("isrequired"));
	            attributes.add(attribute);
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
			Map<String,Parameter> parameters = node.getParameters();
			if (parameters != null && !parameters.isEmpty()) {
				Map<String, Object> map = new LinkedHashMap<String, Object>();
				Map<String, Object> parametemap = new LinkedHashMap<String, Object>();
				map.put(service.getServiceType().toLowerCase(), parametemap);
				parametemap.put("servicename", "接口服务名");
				parametemap.put("partnerid", "接口系统标识");
				parametemap.put("sign", "接口签名");
				//是否分页
				if (service.isYnpage()) {
					parametemap.put("pagesize", "每页记录数");
					parametemap.put("pagenum", "当前页码");
				}
				for (Parameter p:parameters.values()) {
					parametemap.put(p.getFaceName().toLowerCase(), p.getRemark());
				}
				try {
					if ("xml".equalsIgnoreCase(type)) {
						str = XmlUtil.formatXml(XmlUtil.map2xml(map));
					} else if ("json".equalsIgnoreCase(type)) {
						str = JsonUtil.obj2Json(map);
					} else {
						str = XmlUtil.formatXml(XmlUtil.map2xml(map));
					}
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
			Map<String, Object> resultdatamap = new LinkedHashMap<String, Object>();
			Map<String, Object> datamap = new LinkedHashMap<String, Object>();
			returnmap.put("result", resultmap);
			resultmap.put("resultcode", "接口返回代码");
			resultmap.put("resultmsg", "接口返回提示");
			resultmap.put("resultdata", resultdatamap);
			if (service.isYnpage()) {
				resultdatamap.put("totalcount", "总记录数");
				resultdatamap.put("pagesize", "每页记录数");
				resultdatamap.put("totalpage", "总页数");
				resultdatamap.put("currentpage", "当前页码");
				resultdatamap.put("currentcount", "当前页记录数");
			}
			resultdatamap.put("data", datamap);
			createReturnMap(node,datamap);
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
			List<Attribute> attributes = node.getAttributes();
			Map<String,Map<String, Object>> outattrmap = null;
			Map<String, Object> attrmap = null;
			if (attributes != null && !attributes.isEmpty()) {
				outattrmap = new LinkedHashMap<String,Map<String, Object>>();
				attrmap = new LinkedHashMap<String, Object>(); 
				outattrmap.put(nodename,attrmap);
				map.put(nodename+"s", outattrmap);
				for (Attribute attr:attributes) {
					if ("属性".equals(attr.getSourceType())) {
						attrmap.put(attr.getFaceName().toLowerCase(), attr.getRemark());
					} else if ("固定值".equals(attr.getSourceType())) {
						attrmap.put(attr.getFaceName().toLowerCase(), attr.getValue());
					}
				}
			}
			List<Node> subnodes = node.getSubNodes();
			if (subnodes != null && !subnodes.isEmpty()) {
				for (Node n:subnodes) {
					if (attrmap != null) {
						createReturnMap(n,attrmap);
					}
				}
			}
		}
	}
}
