package com.shuto.mam.webservice.common.service.impl;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.DocumentException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.util.MXException;

import com.shuto.mam.webservice.common.bean.Attribute;
import com.shuto.mam.webservice.common.bean.Node;
import com.shuto.mam.webservice.common.bean.Parameter;
import com.shuto.mam.webservice.common.bean.Service;
import com.shuto.mam.webservice.common.bean.ServicePartner;
import com.shuto.mam.webservice.common.service.BaseService;
import com.shuto.mam.webservice.common.service.CommonService;
import com.shuto.mam.webservice.common.util.Base64Util;
import com.shuto.mam.webservice.common.util.Constant;
import com.shuto.mam.webservice.common.util.JcacheUtil;
import com.shuto.mam.webservice.common.util.JsonUtil;
import com.shuto.mam.webservice.common.util.Md5Util;
import com.shuto.mam.webservice.common.util.XmlUtil;

/**
 * @Title: CommonServiceImpl.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-12 下午02:13:23 
 * @version V1.0 
 */
public class CommonServiceImpl implements CommonService {

	/* 
	 * <p>Title: query</p>  
	 * <p>Description: </p>  
	 * @param parameter
	 * @return  
	 * @see com.shuto.mam.webservice.common.service.CommonService#qurey(java.lang.String)  
	 */
	@Override
	public String query(String parameter) {
		String returnstr = null;
		Map<String, Object> returnmap = new LinkedHashMap<String, Object>();
		Service service = null;
		try {
			service = validate(parameter,returnmap,false);
			Map<String,String> resultmap = (Map<String, String>) returnmap.get("result");
			if (Constant.SUCCESS.equals(resultmap.get("resultcode"))) {
				returnstr = executeService(service);
			} else {
				if (XmlUtil.isXML(parameter.toString())) {
					returnstr = XmlUtil.formatXml(XmlUtil.map2xml(returnmap));
				} else if (JsonUtil.isJson(parameter.toString())) {
					returnstr = JsonUtil.obj2Json(returnmap);
				} else {
					returnstr = XmlUtil.formatXml(XmlUtil.map2xml(returnmap));
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnstr;
	}

	/* 
	 * <p>Title: upload</p>  
	 * <p>Description: </p>  
	 * @param parameter
	 * @return  
	 * @see com.shuto.mam.webservice.common.service.CommonService#upload(java.lang.String)  
	 */
	@Override
	public String upload(String parameter) {
		String returnstr = null;
		Map<String, Object> returnmap = new LinkedHashMap<String, Object>();
		Service service = null;
		try {
			service = validate(parameter,returnmap,false);
			Map<String,String> resultmap = (Map<String, String>) returnmap.get("result");
			if (Constant.SUCCESS.equals(resultmap.get("resultcode"))) {
				returnstr = executeService(service);
			} else {
				if (XmlUtil.isXML(parameter.toString())) {
					returnstr = XmlUtil.formatXml(XmlUtil.map2xml(returnmap));
				} else if (JsonUtil.isJson(parameter.toString())) {
					returnstr = JsonUtil.obj2Json(returnmap);
				} else {
					returnstr = XmlUtil.formatXml(XmlUtil.map2xml(returnmap));
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			addLog(service,parameter,returnstr);
		}
		return returnstr;
	}

	/**
	 * 接口测试
	 * @Title: serviceTest  
	 * @Description: TODO  
	 * @param parameter
	 * @return String
	 * @throws
	 */
	public String serviceTest(String parameter) {
		String returnstr = null;
		Map<String, Object> returnmap = new LinkedHashMap<String, Object>();
		Service service = null;
		try {
			service = validate(parameter,returnmap,true);
			Map<String,String> resultmap = (Map<String, String>) returnmap.get("result");
			if (Constant.SUCCESS.equals(resultmap.get("resultcode"))) {
				returnstr = executeService(service);
			} else {
				if (XmlUtil.isXML(parameter.toString())) {
					returnstr = XmlUtil.formatXml(XmlUtil.map2xml(returnmap));
				} else if (JsonUtil.isJson(parameter.toString())) {
					returnstr = JsonUtil.obj2Json(returnmap);
				} else {
					returnstr = XmlUtil.formatXml(XmlUtil.map2xml(returnmap));
				}
			}
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnstr;
	}
	
	/**
	 * 接口服务调用验证
	 * @throws Exception 
	 * @throws IOException 
	 * @throws DocumentException 
	 * @Title: validate  
	 * @Description: TODO  
	 * @param parameter
	 * @param returnmap
	 * @param returnmap
	 * @return Service
	 * @throws
	 */
	private Service validate(String parameter,Map<String, Object> returnmap,boolean istest) throws Exception {
		Service service = null;
		String resultcode = Constant.SUCCESS;
		String resultmsg = "接口调用成功"; 
		if (parameter != null && !"".equals(parameter.trim())) {
			Map<String, Object> parametermap = null;
			if (XmlUtil.isXML(parameter.toString())) {
				parametermap = XmlUtil.xml2mapWithAttr(parameter, true);
			} else if (JsonUtil.isJson(parameter.toString())) {
				parametermap = JsonUtil.json2Map(parameter);
			} else {
				resultcode = Constant.FAIL;
				resultmsg = "接口参数格式不正确"; 
			}
			if (parametermap != null) {
				if (parametermap.containsKey("query") || parametermap.containsKey("upload")) {
					Map<String, Object> valuemap = null;
					//接口服务名
					String servicename = null;
					//接口服务类型
					String servicetype = null;
					//接口服务对接方标识ID
					String partnerid = null;
					//接口签名
					String sign = null;
					//接口服务参数map<参数名,参数值>
					Map<String,String> parameters = null;
					//缺失的参数
					StringBuffer noparamets = new StringBuffer();
					if (parametermap.containsKey("query")) {
						valuemap = (Map<String, Object>) parametermap.get("query");
						servicetype = "QUERY";
					} else if (parametermap.containsKey("upload")) {
						valuemap = (Map<String, Object>) parametermap.get("upload");
						if (!valuemap.containsKey("data")) {
							noparamets.append("data");
						}
						servicetype = "UPLOAD";
					} 
					if (valuemap != null) {
						if (!valuemap.containsKey("servicename")) {
							noparamets.append("servicename");
						} else {
							servicename = (String) valuemap.get("servicename");
						}
						if (!valuemap.containsKey("partnerid")) {
							noparamets.append("partnerid");
						} else {
							partnerid = (String) valuemap.get("partnerid");
						}
						if (!valuemap.containsKey("sign")) {
							noparamets.append("sign");
						} else {
							sign = (String) valuemap.get("sign");
						}
					}
					if (servicename != null) {
						//如果是测试则直接从数据库中加载服务
						if (istest) {
							service = createService(servicename);
						} else {
							//非测试则首先从还缓存中加载已经发布的服务
							service = loadService(servicename);
						}
					}
					if (noparamets != null && noparamets.length()>0) {
						resultcode = Constant.FAIL;
						resultmsg = "缺少参数:"+noparamets.toString();
					} else {
						if (service != null) {
							if (service.getServiceType().equals(servicetype)) {
								if (service.getPartners() != null && service.getPartners().containsKey(partnerid)) {
									//密钥
									String secretKey = service.getPartners().get(partnerid).getSecretKey();
									//预签名参数串
									StringBuffer signstr = new StringBuffer();
									if (secretKey != null) {
										signstr.append(secretKey);
										signstr.append(partnerid);
										//封装接口预签名参数
										if (service.getSignparams() != null) {
											String signparam = null;
											for (int i=1;i<=service.getSignparams().size();i++) {
												signparam = service.getSignparams().get(i);
												signstr.append(valuemap.get(signparam));
											}
										}
										//MD5加密串
										String md5str = Md5Util.md5(signstr.toString());
										if (!md5str.equalsIgnoreCase(sign)) {
											resultcode = Constant.FAIL;
											resultmsg = "您没有访问该接口的权限,请确认接口签名是否正确";
										} else {
											//封装接口服务参数
											parameters = new LinkedHashMap<String,String>();
											Object value = null;
											for (String key:valuemap.keySet()) {
												value = valuemap.get(key);
												if (value instanceof Map) {
													parameters.put(key, XmlUtil.formatXml(XmlUtil.map2xml((Map<String, Object>) value)));
												} else {
													//对接口数据内容进行解密
													if ("data".equals(key)) {
														String encrypttype = service.getEncryptType();
														if ("BASE64".equalsIgnoreCase(encrypttype)) {
															String data = Base64Util.decode((String)value);
															//解析特殊字符
															if (XmlUtil.isXML(data)) {
																data = XmlUtil.formatXml(XmlUtil.map2xml(XmlUtil.xml2mapWithAttr(data, true)));
															}
															parameters.put(key, data);
														} else {
															parameters.put(key, (String)value);
														}
													} else {
														parameters.put(key, (String)value);
													}
												}
											}
											service.setParameters(parameters);
										}
									} else {
										resultcode = Constant.FAIL;
										resultmsg = "密钥缺失,请联系接口服务提供方";
									}
								} else {
									resultcode = Constant.FAIL;
									resultmsg = "您没有访问该接口的权限,请确认partnerid是否正确";
								}
							} else {
								resultcode = Constant.FAIL;
								resultmsg = "接口参数格式与服务类型不匹配";
							}
						} else {
							resultcode = Constant.FAIL;
							resultmsg = "接口服务不存在";
						}
					}
				} else {
					resultcode = Constant.FAIL;
					resultmsg = "接口参数格式不正确";
				}
			}
		} else {
			resultcode = Constant.FAIL;
			resultmsg = "接口参数不能为空"; 
		}
		
		Map<String, Object> resultmap = new LinkedHashMap<String, Object>();
		returnmap.put("result", resultmap);
		resultmap.put("resultcode", resultcode);
		resultmap.put("resultmsg", resultmsg);
		return service;
	}
	
	/**
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 * 执行接口服务
	 * @Title: executeService  
	 * @Description: TODO  
	 * @param service
	 * @return String
	 * @throws
	 */
	private String executeService(Service service)  {
		String returnstr = null;
		Class<?> serviceclass;
		try {
			serviceclass = Class.forName(service.getServiceClass());
			BaseService concreteService = (BaseService) serviceclass.newInstance();
			returnstr = concreteService.execute(service);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return returnstr;
	}
	
	/**
	 * 根据接口服务名获取接口服务
	 * @Title: loadService  
	 * @Description: TODO  
	 * @param servicename
	 * @return Service
	 * @throws
	 */
	private Service loadService(String servicename) {
		//首先从缓存中获取接口服务
		Service service = JcacheUtil.getServiceFromCache(servicename);
		if (service == null) {
			//如果缓存中不存在对应的接口服务,则从数据库中加载,并保存到缓存
			service = createService(servicename);
			if (service != null) {
				JcacheUtil.putServiceToCache(service);
			}
		}
		return service;
	}
	
	/**
	 * 从数据库中加载接口服务
	 * @Title: createService  
	 * @Description: TODO  
	 * @param servicename
	 * @return Service
	 * @throws
	 */
	public Service createService(String servicename) {
		Service service = null;
		try {
			MXServer mxserver = MXServer.getMXServer();
			UserInfo userinfo = mxserver.getSystemUserInfo();
			MboSetRemote serviceMboSet = mxserver.getMboSet("SERVICE", userinfo);
			serviceMboSet.setWhere("servicename='"+servicename+"'");
			MboRemote serviceMbo = serviceMboSet.getMbo(0);
			if (serviceMbo != null) {
				Node rootnode = null;
				List<Node> rootnodes = null;
				List<Node> allnodes = new LinkedList<Node>();
				Map<String,ServicePartner> partners = null;
				Map<Integer,String> signparams = null;
				
				//接口服务根节点
				MboSetRemote rootnodeeMboSet = serviceMbo.getMboSet("ROOTNODE");
				//接口服务对接方
				MboSetRemote partnerMboSet = serviceMbo.getMboSet("SERVICEPARTNER");
				//接口服务签名参数
				MboSetRemote signparamMboSet = serviceMbo.getMboSet("SERVICESIGNPARAM");
				
				//初始化接口服务节根点数据(一个查询服务只能对应一个查询服务根节点;一个上传服务可以对应多个数据插入根节点)
				if (rootnodeeMboSet.count() == 1) {
					rootnode = new Node();
					initNode(rootnodeeMboSet.getMbo(0),rootnode,allnodes);
				} else {
					rootnodes = new LinkedList<Node>();
					for (int i=0;i<rootnodeeMboSet.count();i++) {
						rootnode = new Node();
						initNode(rootnodeeMboSet.getMbo(i),rootnode,allnodes);
						rootnodes.add(rootnode);
					}
				}
				//封装接口服务对接方数据
				ServicePartner partner = null;
				if (partnerMboSet != null && !partnerMboSet.isEmpty()) {
					partners = new LinkedHashMap<String,ServicePartner>();
					for (int i=0;i<partnerMboSet.count();i++) {
						partner = new ServicePartner();
						partner.setPartnerNum(partnerMboSet.getMbo(i).getString("partnernum"));
						partner.setPartnerId(partnerMboSet.getMbo(i).getString("partnerid"));
						partner.setSecretKey(partnerMboSet.getMbo(i).getString("secretkey"));
						partners.put(partner.getPartnerId(), partner);
					}
				}
				//封装接口服务签名参数
				if (signparamMboSet != null && !signparamMboSet.isEmpty()) {
					signparams = new LinkedHashMap<Integer,String>();
					for (int i=0;i<signparamMboSet.count();i++) {
						signparams.put(signparamMboSet.getMbo(i).getInt("ordernum"), signparamMboSet.getMbo(i).getString("signparam"));
					}
				}
				
				service = new Service();
				service.setServiceName(serviceMbo.getString("servicename"));
				service.setServiceType(serviceMbo.getString("servicetype"));
				service.setServiceClass(serviceMbo.getString("serviceclass"));
				service.setDburl(serviceMbo.getString("dburl"));
				service.setDbuser(serviceMbo.getString("dbuser"));
				service.setDbpwd(serviceMbo.getString("dbpwd"));
				service.setRemark(serviceMbo.getString("remark"));
				service.setYnpage(serviceMbo.getBoolean("ynpage"));
				service.setMaxPageCount(serviceMbo.getInt("maxpagecount"));
				service.setEncryptType(serviceMbo.getString("encrypttype"));
				service.setIoFormat(serviceMbo.getString("ioformat"));
				service.setRootNode(rootnode);
				service.setRootNodes(rootnodes);
				service.setAllNodes(allnodes);
				service.setPartners(partners);
				service.setSignparams(signparams);
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return service;
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
	private void initNode(MboRemote rootmbo,Node rootnode,List<Node> allnodes) throws RemoteException, MXException {
		if (rootmbo != null) {
			//子节点
			MboSetRemote subnodes = rootmbo.getMboSet("SUBNODE");
			//节点对应的参数
			MboSetRemote nodeparameters = rootmbo.getMboSet("NODEPARAMETER");
			nodeparameters.setOrderBy("snum");
			//节点对应的属性
			MboSetRemote nodeattributes = rootmbo.getMboSet("NODEATTRIBUTE");
			nodeattributes.setOrderBy("ordernum");
			//节点对应的数据修改标识属性
			MboSetRemote updatemarks = rootmbo.getMboSet("NODEUPDATEMARK");
			
			//节点对应的参数map
			Map<String,Parameter> parameters = new LinkedHashMap<String,Parameter>();
			//节点对应的属性list
			List<Attribute> attributes = new ArrayList<Attribute>();
			//节点对应的子节点
			List<Node> subnodelist = new LinkedList<Node>(); 
			//节点对应的数据修改标识属性
			Map<String,String> updatemark = new HashMap<String,String>();
			//节点对应的数据更新条件属性
			Map<String,Parameter> updateconditions = new HashMap<String,Parameter>();
			//封装节点信息
			rootnode.setNodeNum(rootmbo.getString("nodenum"));
			rootnode.setNodeName(rootmbo.getString("nodename"));
			rootnode.setLayer(rootmbo.getInt("layer"));
			rootnode.setRemark(rootmbo.getString("remark"));
			rootnode.setParentNum(rootmbo.getString("parentnum"));
			rootnode.setSql(rootmbo.getString("sql"));
			rootnode.setTableName(rootmbo.getString("tablename"));
			rootnode.setParameters(parameters);
			rootnode.setAttributes(attributes);
			rootnode.setSubNodes(subnodelist);
			rootnode.setUpdateMark(updatemark);
			rootnode.setUpdateConditions(updateconditions);
			allnodes.add(rootnode);
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
				nodeparameter.setFromParent(nodeparameters.getMbo(i).getBoolean("isfromparent"));
				nodeparameter.setParentParamet(nodeparameters.getMbo(i).getString("parentParamet"));
				nodeparameter.setSequence(nodeparameters.getMbo(i).getBoolean("issequence"));
				nodeparameter.setSequenceName(nodeparameters.getMbo(i).getString("sequencename"));
				nodeparameter.setAutoNumber(nodeparameters.getMbo(i).getBoolean("isautonumber"));
				nodeparameter.setAutoNumberName(nodeparameters.getMbo(i).getString("autonumbername"));
				nodeparameter.setUpdateCondition(nodeparameters.getMbo(i).getBoolean("isupdatecondition"));
				nodeparameter.setParametClass(nodeparameters.getMbo(i).getString("parametclass"));
				nodeparameter.setRequired(nodeparameters.getMbo(i).getBoolean("isrequired"));
				nodeparameter.setComparemark(nodeparameters.getMbo(i).getString("comparemark"));
				parameters.put(nodeparameter.getSnum()+"", nodeparameter);
				if (nodeparameter.isUpdateCondition()) {
					updateconditions.put(nodeparameter.getName(), nodeparameter);
				}
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
			//封装节点数据修改标识属性
			for (int k=0;k<updatemarks.count();k++) {
				updatemark.put(updatemarks.getMbo(k).getString("attributename"), updatemarks.getMbo(k).getString("attributevalue"));
			}
			
			//递归调用,封装子节点
			if (subnodes != null && !subnodes.isEmpty()) {
				Node subnode = null; 
				for (int k=0;k<subnodes.count();k++) {
					subnode = new Node();
					subnodelist.add(subnode);
					initNode(subnodes.getMbo(k),subnode,allnodes);
				}
			}
		}
	}
	
	/**
	 * 记录接口日志
	 * @param service
	 * @param parameter
	 * @param returnstr
	 */
	private void addLog(Service service,String parameter,String returnstr) {
		String partnerid = "";
		String servicename = "";
		String servicedesc = "";
		String servicetype = "";
		String synchstatus = "";
		StringBuffer logSbf = new StringBuffer();
		logSbf.append("===============接收==============\n");
		logSbf.append(parameter+"\n\n");
		if (service != null) {
			servicename = service.getServiceName();
			servicedesc = service.getRemark();
			servicetype = service.getServiceType();
			Map<String, String> parameters = service.getParameters();
			if (parameters != null) {
				partnerid = parameters.get("partnerid");
				String encrypttype = service.getEncryptType();
				if ("BASE64".equalsIgnoreCase(encrypttype)) {
					String data = parameters.get("data");
					logSbf.append("===============接收(数据解密后)==============\n");
					logSbf.append(data+"\n\n");
				}
			}
		}
		if (returnstr != null) {
			Map<String, Object> returnmap = null;
			if (XmlUtil.isXML(returnstr.toString())) {
				try {
					returnmap = XmlUtil.xml2mapWithAttr(returnstr, true);
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (JsonUtil.isJson(returnstr.toString())) {
				returnmap = JsonUtil.json2Map(returnstr);
			}
			if (returnmap != null) {
				Map<String,String> resultmap = (Map<String, String>) returnmap.get("result");
				if (resultmap != null) {
					if ("1".equals(resultmap.get("resultcode"))) {
						synchstatus = "成功";
					} else {
						synchstatus = "失败";
					}
				}
			}
		}
		logSbf.append("===============返回==============\n");
		logSbf.append(returnstr);
		
		MboSetRemote logmboset = null;
		try {
			MXServer mxserver = MXServer.getMXServer();
			UserInfo userinfo = mxserver.getSystemUserInfo();
			logmboset = mxserver.getMboSet("INTERFACELOG", userinfo);
			MboRemote logmbo = logmboset.add();
			logmbo.setValue("partnerid", partnerid);
			logmbo.setValue("description", servicename);
			logmbo.setValue("synchdesc", servicedesc);
			logmbo.setValue("synchdate", new Date());
			logmbo.setValue("synchtype", servicetype);
			logmbo.setValue("synchstatus", synchstatus);
			logmbo.setValue("datalog", logSbf.toString());
			logmboset.save(2L);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (logmboset != null) {
				try {
					logmboset.close();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 显示node
	 * @param node
	 */
	 void showNode(Node node) {
		if (node != null) {
			Map<String,Parameter> parameters = node.getParameters();
			System.out.println("======================"+node.getNodeName()+"参数======================");
			for (Parameter p:parameters.values()) {
				System.out.println(p.getSnum()+"|"+p.getName()+"|"+p.getFaceName()+"|"+p.getType()+"|"+p.getSourceType()+"|"+p.getValue());
			}
//			List<Attribute> attributes = node.getAttributes();
//			System.out.println("======================"+node.getNodeName()+"属性======================");
//			for (Attribute attr:attributes) {
//				System.out.println(attr.getValueType()+"|"+attr.getTableName()+"|"+attr.getColumnName()+"|"+attr.getColumAlias()+"|"+attr.getColumnType()+"|"+attr.getColumnSize()+"|"+attr.getFaceName()+"|"+attr.getSourceType());
//			}
			List<Node> subnodes = node.getSubNodes();
			if (subnodes != null && !subnodes.isEmpty()) {
				for (Node n:subnodes) {
					showNode(n);
				}
			}
		}
	}
}
