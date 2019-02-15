package com.shuto.mam.webservice.common.service.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.shuto.mam.webservice.common.bean.Node;
import com.shuto.mam.webservice.common.bean.Parameter;
import com.shuto.mam.webservice.common.bean.Service;
import com.shuto.mam.webservice.common.service.BaseParameter;
import com.shuto.mam.webservice.common.service.BaseService;
import com.shuto.mam.webservice.common.util.Constant;
import com.shuto.mam.webservice.common.util.JsonUtil;
import com.shuto.mam.webservice.common.util.XmlUtil;

/**
 * @Title: CommonUploadServiceImpl.java 
 * @Description: TODO   
 * @author itrobot 
 * @date 2017-6-14 下午03:01:21 
 * @version V1.0 
 */
public class CommonUploadServiceImpl implements BaseService {

	@Override
	public String execute(Service service) {
		String returnstr = null;
		Connection conn = null;
		Map<String, Object> returnmap = new LinkedHashMap<String, Object>();
		Map<String, Object> resultmap = new LinkedHashMap<String, Object>();
		String resultcode = Constant.SUCCESS;
		String resultmsg = "数据上传成功";
		try {
			Map<String,String> serviceparamets = service.getParameters();
			String data = serviceparamets.get("data");
			if (data != null && !"".equals(data.trim())) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
				conn=DriverManager.getConnection(service.getDburl(), service.getDbuser(), service.getDbpwd());
				conn.setAutoCommit(false);
				
				SAXReader reader = new SAXReader();
				Document document = reader.read(new ByteArrayInputStream(data.getBytes("UTF-8")));
				Element root = document.getRootElement();
				List<Node> allnodes = service.getAllNodes();
				if (allnodes != null && !allnodes.isEmpty()) {
					Map<String,Map<String,Parameter>> nodeparametmap = new LinkedHashMap<String,Map<String,Parameter>>();
					List<String> logs = new LinkedList<String>();
					boolean isUpdate = false;
					doUpload(root,allnodes,nodeparametmap,conn,logs,isUpdate);
					conn.commit();
					for (String log:logs) {
						System.out.println(log);
					}
				} else {
					resultcode = Constant.FAIL;
					resultmsg = "接口服务异常,请联系接口服务提供方";
				}
			} else {
				resultcode = Constant.FAIL;
				resultmsg = "缺少需上传的数据";
			}
			
		} catch (ClassNotFoundException e) {
			resultcode = Constant.FAIL;
			resultmsg = "接口服务异常,请联系接口服务提供方";
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			resultcode = Constant.FAIL;
			resultmsg = "接口服务异常,请联系接口服务提供方";
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				if (conn != null) {
					conn.rollback();
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} catch (SQLException e) {
			resultcode = Constant.FAIL;
			resultmsg = "接口服务异常,请联系接口服务提供方";
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			returnmap.put("result", resultmap);
			resultmap.put("resultcode", resultcode);
			resultmap.put("resultmsg", resultmsg);
			try {
				if ("xml".equalsIgnoreCase(service.getIoFormat())) {
					returnstr = XmlUtil.formatXml(XmlUtil.map2xml(returnmap));
				} else if ("json".equalsIgnoreCase(service.getIoFormat())) {
					returnstr = JsonUtil.obj2Json(returnmap);
				} else {
					returnstr = XmlUtil.formatXml(XmlUtil.map2xml(returnmap));
				}
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnstr;
	}

	/**
	 * 数据上传
	 * @Title: doUpload  
	 * @Description: TODO  
	 * @param elm
	 * @param nodes
	 * @param nodeparametmap
	 * @param conn
	 * @param logs
	 * @param isUpdate
	 * @throws SQLException void
	 * @throws
	 */
	private void doUpload(Element elm,List<Node> nodes,Map<String,Map<String,Parameter>> nodeparametmap,Connection conn,List<String> logs,boolean isUpdate) throws SQLException {
		if (elm != null) {
			List<Element> childs = elm.elements();
			if (childs != null && !childs.isEmpty()) {
				for (Node node:nodes) {
					if (elm.getName().equalsIgnoreCase(node.getNodeName())) {
						Map<String,String> updatemark = node.getUpdateMark();
						if (node.getLayer() == 1) {
							isUpdate = false;
							//判断是否更新
							if (updatemark != null && !updatemark.isEmpty()) {
								int truenum = 0;
								for (String mark:updatemark.keySet()) {
									if (updatemark.get(mark).equals(elm.elementText(mark))) {
										truenum ++ ;
									}
								}
								if (updatemark.size() == truenum) {
									isUpdate = true;
								}
							} else {
								isUpdate = isExist(node,elm,conn);
							}
						}
						
						if (isUpdate) {
							if (node.getLayer() == 1) {
								doUpdate(node,elm,nodeparametmap,conn,logs);
							} else {
								doInsert(node,elm,nodeparametmap,conn,logs);
							}
						} else {
							doInsert(node,elm,nodeparametmap,conn,logs);
						}
					}
				}
				//递归调用,循环子节点
				for (Element e:childs) {
					doUpload(e,nodes,nodeparametmap,conn,logs,isUpdate);
				}
			}
		}
	}
	
	/**
	 * 删除
	 * @param node
	 * @param elm
	 * @param nodeparametmap
	 * @param conn
	 * @param logs
	 * @throws SQLException 
	 */
	private void doDelete(Node node,Connection conn,List<Map<String,String>> rsmaps,List<String> logs) throws SQLException {
		PreparedStatement spstatement = null;
		PreparedStatement upstatement = null;
		ResultSet rs = null;
		try {
			if (node != null) {
				String table = null;
				Map<String,Parameter> parameters = null;
				List<Node> subnodes = null;
				StringBuffer querysql = null;
				StringBuffer deletesql = null;
				List<Map<String,String>> childrsmaps = null;
				for (Map<String,String> rsmap:rsmaps) {
					table = node.getTableName();
					parameters = node.getParameters();
					subnodes = node.getSubNodes();
					querysql = new StringBuffer();
					deletesql = new StringBuffer();
					
					deletesql.append("delete from "+table+" where ");
					querysql.append("select ");
					//拼接select部分
					int num = 0;
					for (Parameter p:parameters.values()) {
						if (num == 0) {
							if ("日期".equals(p.getType())) {
								querysql.append("to_char("+p.getName()+", 'yyyy-mm-dd') "+p.getName());
							} else if ("日期时间".equals(p.getType())) {
								querysql.append("to_char("+p.getName()+", 'yyyy-mm-dd hh24:mi:ss') "+p.getName());
							} else {
								querysql.append(p.getName());
							}
						} else {
							if ("日期".equals(p.getType())) {
								querysql.append(",to_char("+p.getName()+", 'yyyy-mm-dd') "+p.getName());
							} else if ("日期时间".equals(p.getType())) {
								querysql.append(",to_char("+p.getName()+", 'yyyy-mm-dd hh24:mi:ss') "+p.getName());
							} else {
								querysql.append(","+p.getName());
							}
						}
						num++;
					}
					querysql.append(" from "+table+" where ");
					
					//拼接where部分
					num = 0;
					int parametnum = 1;
					Map<Integer,String> whereparamets = new LinkedHashMap<Integer,String>();
					for (Parameter p:parameters.values()) {
						if (p.isFromParent() && p.getParentParamet() != null && !"".equals(p.getParentParamet())) {
							//父节点参数集
							String value = rsmap.get(p.getParentParamet());
							if (value != null) {
								if (num == 0) {
									querysql.append(p.getName()+"=?");
									deletesql.append(p.getName()+"=?");
								} else {
									querysql.append(" and "+p.getName()+"=?");
									deletesql.append(" and "+p.getName()+"=?");
								}
								whereparamets.put(parametnum, value);
								num++;
								parametnum++;
							} 
						}
					}
					spstatement = conn.prepareStatement(querysql.toString());
					upstatement = conn.prepareStatement(deletesql.toString());
					
					logs.add(deletesql.toString());
					StringBuffer pstr = new StringBuffer();
					for (int i:whereparamets.keySet()) {
						spstatement.setString(i, whereparamets.get(i));
						upstatement.setString(i, whereparamets.get(i));
						pstr.append(whereparamets.get(i) + ",");
					}
					logs.add(pstr.toString()+"\n");
					rs = spstatement.executeQuery();
					
					childrsmaps = new LinkedList<Map<String,String>>();
					Map<String,String> childrsmap = null;
					while (rs.next()) {
						childrsmap = new HashMap<String,String>();
						for (Parameter p:parameters.values()) {
							childrsmap.put(p.getName(), rs.getString(p.getName()));
						}
						childrsmaps.add(childrsmap);
					}
					//删除数据
					upstatement.execute();
					conn.commit();
					
					//将要删除的数据集传递给子节点用于删除子表数据
					if (subnodes != null && !subnodes.isEmpty()) {
						for (Node n:subnodes) {
							doDelete(n,conn,childrsmaps,logs);
						}
					}
				}
			}
		} finally {
			if (rs != null) {
				rs.close();
			}
			if (spstatement != null) {
				spstatement.close();
			}
			if (upstatement != null) {
				upstatement.close();
			}
		}
	}
	
	/**
	 * 新增
	 * @param node
	 * @param elm
	 * @param nodeparametmap
	 * @param conn
	 * @param logs
	 * @throws SQLException 
	 */
	private void doInsert(Node node,Element elm,Map<String,Map<String,Parameter>> nodeparametmap,Connection conn,List<String> logs) throws SQLException {
		PreparedStatement upstatement = null;
		try {
			if (node != null) {
				String sql = node.getSql();
				Map<String,Parameter> parameters = node.getParameters();
				//参数map<参数名,参数对象>
				Map<String,Parameter> parametermap = new LinkedHashMap<String,Parameter>();
				//以参数名为key封装参数map,用于后续根据参数名获取参数对象
				for (Parameter p:parameters.values()) {
					parametermap.put(p.getName().toUpperCase(), p);
				}
				upstatement = conn.prepareStatement(sql);
				String paramentvalue = null;
				StringBuffer log = new StringBuffer(sql+"\n");
				for (Parameter p:parametermap.values()) {
					if (!"固定值".equals(p.getSourceType())) {
						//序列
						if (p.isSequence() && p.getSequenceName() != null && !"".equals(p.getSequenceName())) {
							Long seq = generateSeq(conn,p.getSequenceName());
							if (seq != null) {
								paramentvalue = String.valueOf(seq);
							} else {
								//提示：获取序列出错
							}
						//自动编号
						} else if (p.isAutoNumber() && p.getAutoNumberName() != null && !"".equals(p.getAutoNumberName())) {
							String orgidparaet = parametermap.get("ORGID")!=null?parametermap.get("ORGID").getFaceName():"";
							String siteidparaet = parametermap.get("SITEID")!=null?parametermap.get("SITEID").getFaceName():"";
							String autokey = generateAutokey(conn,p.getAutoNumberName(),elm.elementText(orgidparaet),elm.elementText(siteidparaet));
							if (autokey != null) {
								paramentvalue = autokey;
							} else {
								//提示：自动编号出错
							}
						//从父级获取属性值
						} else if (p.isFromParent() && p.getParentParamet() != null && !"".equals(p.getParentParamet())) {
							//父节点参数集
							Map<String,Parameter> parentparameters = nodeparametmap.get(node.getParentNum());
							if (parentparameters != null) {
								Parameter parentparament = parentparameters.get(p.getParentParamet().toUpperCase());
								if (parentparament != null) {
									paramentvalue = parentparament.getValue();
								} else {
									//提示：父参数缺失
								}
							}
						//参数
						} else {
							if (p.getParametClass() != null && !"".equals(p.getParametClass())) {
								try {
									Class<?> parametclass = Class.forName(p.getParametClass());
									BaseParameter des = (BaseParameter) parametclass.newInstance();
									paramentvalue = des.initValue(p, elm, conn);
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
							} else {
								if(elm.element(p.getFaceName()) != null) {
									paramentvalue = elm.element(p.getFaceName()).getText();
								} else {
									//提示：参数缺失
								}
							}
						}
						p.setValue(paramentvalue);
					}
					upstatement.setString(p.getSnum(), p.getValue());
					log.append(p.getValue()+",");
				}
				logs.add(log.toString()+"\n");
				upstatement.execute();
				nodeparametmap.put(node.getNodeNum(), parametermap);
			}
		} finally {
			if (upstatement != null) {
				upstatement.close();
			}
		}
	}
	
	/**
	 * 更新
	 * @param node
	 * @param elm
	 * @param nodeparametmap
	 * @param conn
	 * @param logs
	 * @throws SQLException 
	 */
	private void doUpdate(Node node,Element elm,Map<String,Map<String,Parameter>> nodeparametmap,Connection conn,List<String> logs) throws SQLException {
		if (node != null) {
			PreparedStatement spstatement = null;
			PreparedStatement upstatement = null;
			ResultSet rs = null;
			String table = node.getTableName();
			Map<String,Parameter> parameters = node.getParameters();
			//参数map<参数名,参数对象>
			Map<String,Parameter> parametermap = new LinkedHashMap<String,Parameter>();
			//以参数名为key封装参数map,用于后续根据参数名获取参数对象
			for (Parameter p:parameters.values()) {
				parametermap.put(p.getName().toUpperCase(), p);
			}
			//用于更新的条件字段
			Map<String,Parameter> updateconditions = node.getUpdateConditions();
			List<Node> subnodes = node.getSubNodes();
			//数据集list,传递给子节点,用于删除子表数据
			List<Map<String,String>> rsmaps = null;
			try {
				if (node.getLayer() == 1) {
					//更新的属性值
					Map<Integer,String> updateparamets = new LinkedHashMap<Integer,String>();
					StringBuffer updatesql = new StringBuffer();
					updatesql.append("update "+table+" set ");
					int parametnum = 1;
					int num = 0;
					String value = null;
					//拼接set部分
					for (Parameter p:parametermap.values()) {
						if (!p.isSequence() && !p.isAutoNumber() && !p.isUpdateCondition()) {
							if (num == 0) {
								if ("日期".equals(p.getType())) {
									updatesql.append(p.getName()+"=to_date(?, 'yyyy-mm-dd')");
								} else if ("日期时间".equals(p.getType())) {
									updatesql.append(p.getName()+"=to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
								} else {
									updatesql.append(p.getName()+"=?");
								}
							} else {
								if ("日期".equals(p.getType())) {
									updatesql.append(","+p.getName()+"=to_date(?, 'yyyy-mm-dd')");
								} else if ("日期时间".equals(p.getType())) {
									updatesql.append(","+p.getName()+"=to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
								} else {
									updatesql.append(","+p.getName()+"=?");
								}
							}
							if (!"固定值".equals(p.getSourceType())) {
								value = elm.elementText(p.getFaceName());
								if (p.getParametClass() != null && !"".equals(p.getParametClass())) {
									try {
										Class<?> parametclass = Class.forName(p.getParametClass());
										BaseParameter des = (BaseParameter) parametclass.newInstance();
										value = des.initValue(p, elm, conn);
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
								}
							} else {
								value = p.getValue();
							}
							updateparamets.put(parametnum, value);
							//更新参数值,用于更新子表
							p.setValue(value);
							parametnum++;
							num ++;
						}
					}
					//拼接where部分
					updatesql.append(" where ");
					num = 0;
					for (Parameter p:updateconditions.values()) {
						if (num == 0) {
							updatesql.append(p.getName()+"=?");
						} else {
							updatesql.append(" and "+p.getName()+"=?");
						}
						if (p.getParametClass() != null && !"".equals(p.getParametClass())) {
							try {
								Class<?> parametclass = Class.forName(p.getParametClass());
								BaseParameter des = (BaseParameter) parametclass.newInstance();
								value = des.initValue(p, elm, conn);
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
						} else {
							value = elm.elementText(p.getFaceName());
						}
						updateparamets.put(parametnum, value);
						parametnum++;
						num++;
					}
					upstatement = conn.prepareStatement(updatesql.toString());
					logs.add(updatesql.toString());
					StringBuffer pstr = new StringBuffer();
					for (int i:updateparamets.keySet()) {
						upstatement.setString(i, updateparamets.get(i));
						pstr.append(updateparamets.get(i)+",");
					}
					logs.add(pstr.toString()+"\n");
					upstatement.execute();
					conn.commit();
					
					/** 查询更新后的记录,用于更新子表 */
					StringBuffer querysql = new StringBuffer();
					querysql.append("select ");
					//拼接select部分
					num = 0;
					for (Parameter p:parametermap.values()) {
						if (num == 0) {
							if ("日期".equals(p.getType())) {
								querysql.append("to_char("+p.getName()+", 'yyyy-mm-dd') "+p.getName());
							} else if ("日期时间".equals(p.getType())) {
								querysql.append("to_char("+p.getName()+", 'yyyy-mm-dd hh24:mi:ss') "+p.getName());
							} else {
								querysql.append(p.getName());
							}
						} else {
							if ("日期".equals(p.getType())) {
								querysql.append(",to_char("+p.getName()+", 'yyyy-mm-dd') "+p.getName());
							} else if ("日期时间".equals(p.getType())) {
								querysql.append(",to_char("+p.getName()+", 'yyyy-mm-dd hh24:mi:ss') "+p.getName());
							} else {
								querysql.append(","+p.getName());
							}
						}
						num++;
					}
					querysql.append(" from "+table+" where ");
					//拼接where部分
					num = 0;
					parametnum = 1;
					Map<Integer,String> queryparamets = new LinkedHashMap<Integer,String>();
					for (Parameter p:updateconditions.values()) {
						if (num == 0) {
							querysql.append(p.getName()+"=?");
						} else {
							querysql.append(" and "+p.getName()+"=?");
						}
						queryparamets.put(parametnum, elm.elementText(p.getFaceName()));
						num++;
						parametnum++;
					}
					spstatement = conn.prepareStatement(querysql.toString());
					for (int i:queryparamets.keySet()) {
						spstatement.setString(i, queryparamets.get(i));
					}
					rs = spstatement.executeQuery();
					//封装数据集list,传递给子节点,用于删除子表数据
					rsmaps = new LinkedList<Map<String,String>>();
					Map<String,String> rsmap = null;
					while (rs.next()) {
						rsmap = new HashMap<String,String>();
						for (Parameter p:parametermap.values()) {
							p.setValue(rs.getString(p.getName()));
							rsmap.put(p.getName(), rs.getString(p.getName()));
						}
						rsmaps.add(rsmap);
					}
					nodeparametmap.put(node.getNodeNum(), parametermap);
					
					//删除子表
					if (subnodes != null && !subnodes.isEmpty()) {
						for (Node n:subnodes) {
							doDelete(n,conn,rsmaps,logs);
						}
					}
				}
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (spstatement != null) {
					spstatement.close();
				}
				if (upstatement != null) {
					upstatement.close();
				}
			}
		}
	}
	
	/**
	 * 判断记录是否已存在
	 * @param node
	 * @param elm
	 * @param conn
	 * @return
	 * @throws SQLException 
	 */
	private boolean isExist(Node node,Element elm,Connection conn) throws SQLException {
		boolean exist = false;
		PreparedStatement spstatement = null;
		ResultSet rs = null;
		String table = node.getTableName();
		Map<String,Parameter> updateconditions = node.getUpdateConditions();
		
		StringBuffer querysql = new StringBuffer();
		if (table != null && updateconditions != null && !updateconditions.isEmpty()) {
			querysql.append("select * from "+table+" where ");
			int num = 0;
			int parametnum = 1;
			Map<Integer,String> queryparamets = new LinkedHashMap<Integer,String>();
			//拼接where部分
			String paramentvalue = null;
			for (Parameter condition:updateconditions.values()) {
				if (num == 0) {
					querysql.append(condition.getName()+"=?");
				} else {
					querysql.append(" and "+condition.getName()+"=?");
				}
				if (condition.getParametClass() != null && !"".equals(condition.getParametClass())) {
					try {
						Class<?> parametclass = Class.forName(condition.getParametClass());
						BaseParameter des = (BaseParameter) parametclass.newInstance();
						paramentvalue = des.initValue(condition, elm, conn);
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
				} else {
					if(elm.element(condition.getFaceName()) != null) {
						paramentvalue = elm.elementText(condition.getFaceName());
					} else {
						//提示：参数缺失
					}
				}
				queryparamets.put(parametnum, paramentvalue);
				num ++;
				parametnum ++;
			}
			
			try {
				spstatement = conn.prepareStatement(querysql.toString());
				for (int i:queryparamets.keySet()) {
					spstatement.setString(i, queryparamets.get(i));
				}
				rs = spstatement.executeQuery();
				while (rs.next()) {
					exist = true;
				}
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (spstatement != null) {
					spstatement.close();
				}
			}
		}
		return exist;
	}
	
	/**
	 * 获取序列值
	 * @param conn
	 * @param seqname
	 * @return
	 * @throws SQLException 
	 */
	private Long generateSeq(Connection conn,String seqname) throws SQLException {
		Long seq = null;
		PreparedStatement spstatement = null;
		ResultSet rs = null;
		if (seqname != null) {
			try {
				String sql = "select "+seqname+".nextval from dual";
				spstatement = conn.prepareStatement(sql);
				rs = spstatement.executeQuery();
				while (rs.next()) {
					seq = rs.getLong(1);
				}
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (spstatement != null) {
					spstatement.close();
				}
			}
		}
		return seq;
	}
	
	/**
	 * 获取自动编号(按地点)
	 * @param conn
	 * @param autokeyname
	 * @param orgid
	 * @return
	 * @throws SQLException 
	 */
	private String generateAutokey(Connection conn,String autokeyname,String orgid,String siteid) throws SQLException {
		String autokey = null;
		PreparedStatement spstatement = null;
		PreparedStatement upstatement = null;
		ResultSet rs = null;
		if (autokeyname != null) {
			try {
				String querysql="select prefix,seed from autokey where autokeyname=?";
				String updatesql="update autokey set seed=seed+1 where autokeyname=?";
				if (orgid != null && !"".equals(orgid) && siteid != null && !"".equals(siteid)) {
					querysql="select prefix,seed from autokey where autokeyname=? and orgid=? and siteid=?";
					updatesql="update autokey set seed=seed+1 where autokeyname=? and orgid=? and siteid=?";
				}
				spstatement = conn.prepareStatement(querysql);
				upstatement = conn.prepareStatement(updatesql);
				spstatement.setString(1, autokeyname);
				upstatement.setString(1, autokeyname);
				if (orgid != null && !"".equals(orgid) && siteid != null && !"".equals(siteid)) {
					spstatement.setString(2, orgid);
					upstatement.setString(2, orgid);
					spstatement.setString(3, siteid);
					upstatement.setString(3, siteid);
				}
				upstatement.executeUpdate();
				conn.commit();
				rs = spstatement.executeQuery();
				while (rs.next()) {
					if (rs.getString("prefix") != null && !"".equals(rs.getString("prefix"))) {
						autokey = rs.getString("prefix")+rs.getInt("seed");
					} else {
						autokey = rs.getInt("seed")+"";
					}
				}
				if (autokey == null) {
					autokey = generateAutokey(conn,autokeyname);
				}
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (spstatement != null) {
					spstatement.close();
				}
				if (upstatement != null) {
					upstatement.close();
				}
			}
		}
		return autokey;
	}
	
	/**
	 * 获取自动编号(不分地点)
	 * @param conn
	 * @param autokeyname
	 * @return
	 * @throws SQLException
	 */
	private String generateAutokey(Connection conn,String autokeyname) throws SQLException {
		String autokey = null;
		PreparedStatement spstatement = null;
		PreparedStatement upstatement = null;
		ResultSet rs = null;
		if (autokeyname != null) {
			try {
				String querysql="select prefix,seed from autokey where autokeyname=?";
				String updatesql="update autokey set seed=seed+1 where autokeyname=?";
				spstatement = conn.prepareStatement(querysql);
				upstatement = conn.prepareStatement(updatesql);
				spstatement.setString(1, autokeyname);
				upstatement.setString(1, autokeyname);
				upstatement.executeUpdate();
				conn.commit();
				rs = spstatement.executeQuery();
				while (rs.next()) {
					if (rs.getString("prefix") != null && !"".equals(rs.getString("prefix"))) {
						autokey = rs.getString("prefix")+rs.getInt("seed");
					} else {
						autokey = rs.getInt("seed")+"";
					}
				}
			} finally {
				if (rs != null) {
					rs.close();
				}
				if (spstatement != null) {
					spstatement.close();
				}
				if (upstatement != null) {
					upstatement.close();
				}
			}
		}
		return autokey;
	}
}
