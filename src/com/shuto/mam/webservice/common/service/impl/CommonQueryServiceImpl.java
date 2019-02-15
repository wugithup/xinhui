package com.shuto.mam.webservice.common.service.impl;

import java.io.IOException;
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

import com.shuto.mam.webservice.common.bean.Attribute;
import com.shuto.mam.webservice.common.bean.Node;
import com.shuto.mam.webservice.common.bean.Parameter;
import com.shuto.mam.webservice.common.bean.Service;
import com.shuto.mam.webservice.common.service.BaseService;
import com.shuto.mam.webservice.common.util.Constant;
import com.shuto.mam.webservice.common.util.JsonUtil;
import com.shuto.mam.webservice.common.util.StrUtil;
import com.shuto.mam.webservice.common.util.XmlUtil;

/**
 * @Title: CommonQueryServiceImpl.java 
 * @Description: 通用查询接口服务   
 * @author itrobot 
 * @date 2017-6-14 下午03:00:35 
 * @version V1.0 
 */
public class CommonQueryServiceImpl implements BaseService {

	@Override
	public String execute(Service service) {
		String returnstr = null;
		Connection conn = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection(service.getDburl(), service.getDbuser(), service.getDbpwd());
			returnstr = createReturn(service,conn);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return returnstr;
	}
	
	/**
	 * 生成接口查询结果
	 * @param service
	 * @param conn
	 * @return
	 */
	private String createReturn(Service service,Connection conn) {
		String returnStr = null;
		PreparedStatement spstatement = null;
		ResultSet rs = null;
		Map<String, Object> returnmap = null;
		try {
			Node node = service.getRootNode();
			if (node != null) {
				String sql = node.getSql();
				Map<String,Parameter> paramets = node.getParameters();
				Map<String,String> serviceparamets = service.getParameters();
				returnmap = new LinkedHashMap<String, Object>();
				Map<String, Object> resultmap = new LinkedHashMap<String, Object>();
				Map<String, Object> resultdatamap = new LinkedHashMap<String, Object>();
				Map<String, Object> datamap = new LinkedHashMap<String, Object>();
				String resultcode = Constant.SUCCESS;
				String resultmsg = "查询成功";
				//总记录数
				int totalcount = 0;
				//每页记录数
				int pagesize = 0;
				//总页数
				int totalpage = 0;
				//当前页记录数
				int currentcount = 0;
				//页码
				int pagenum = 0;
				//当前页码
				int currentpage = pagenum;

				//参数验证
				if (paramets != null && !paramets.isEmpty()) {
					//缺失的参数
					StringBuffer noparamets = new StringBuffer();
					//缺值的参数
					StringBuffer novalparamets = new StringBuffer();
					//格式错误的参数
					StringBuffer erroformatparamets = new StringBuffer();
					if (service.isYnpage()) {
						if (!serviceparamets.containsKey("pagesize")) {
							noparamets.append("pagesize,");
						} else {
							//判断参数是否有值
							if (serviceparamets.get("pagesize")==null || "".equals(serviceparamets.get("pagesize"))) {
								novalparamets.append("pagesize,");
							} else {
								//判断是否数字
								if (StrUtil.isNumeric(serviceparamets.get("pagesize"))) {
									pagesize = Integer.valueOf(serviceparamets.get("pagesize"));
								} else {
									erroformatparamets.append("pagesize,");
								}
							}
						}
						if (!serviceparamets.containsKey("pagenum")) {
							noparamets.append("pagenum,");
						} else {
							//判断参数是否有值
							if (serviceparamets.get("pagenum")==null || "".equals(serviceparamets.get("pagenum"))) {
								novalparamets.append("pagenum,");
							} else {
								//判断是否数字
								if (StrUtil.isNumeric(serviceparamets.get("pagenum"))) {
									pagenum = Integer.valueOf(serviceparamets.get("pagenum"));
								} else {
									erroformatparamets.append("pagenum,");
								}
							}
						}
					}
					for (Parameter p:paramets.values()) {
						if ("参数".equals(p.getSourceType()) && p.isRequired() && !serviceparamets.containsKey(p.getFaceName())) {
							noparamets.append(p.getFaceName()+",");
						} else {
							//判断参数是否有值
							if ("参数".equals(p.getSourceType()) && p.isRequired() && (serviceparamets.get(p.getFaceName())==null || "".equals(serviceparamets.get(p.getFaceName())))) {
								novalparamets.append(p.getFaceName()+",");
							} else {
								if (p.isRequired() && (("日期".equals(p.getType()) && !StrUtil.isDate(serviceparamets.get(p.getFaceName()))) 
										|| ("日期时间".equals(p.getType()) && !StrUtil.isDateTime(serviceparamets.get(p.getFaceName()))))) {
									erroformatparamets.append(p.getFaceName()+",");
								} else {
									if ("参数".equals(p.getSourceType())) {
										p.setValue(serviceparamets.get(p.getFaceName()));
									}
								}
							}
						}
					}
					if (noparamets != null && noparamets.length()>0) {
						resultcode = Constant.FAIL;
						resultmsg = "缺少参数:"+noparamets.deleteCharAt(noparamets.length()-1).toString();
					} else if(novalparamets != null && novalparamets.length()>0) {
						resultcode = Constant.FAIL;
						resultmsg = "参数: "+novalparamets.deleteCharAt(novalparamets.length()-1).toString()+" 缺少参数值";
					} else if(erroformatparamets != null && erroformatparamets.length()>0) {
						resultcode = Constant.FAIL;
						resultmsg = "参数: "+erroformatparamets.deleteCharAt(erroformatparamets.length()-1).toString()+" 格式不正确";
					} else {
						if (pagesize>service.getMaxPageCount().intValue()) {
							resultcode = Constant.FAIL;
							resultmsg = "每页记录数据不能大于:"+service.getMaxPageCount().intValue();
						}
					}
				}
				
				if (Constant.SUCCESS.equals(resultcode)) {
					//分页;获取分页信息
					if (service.isYnpage() && node.getLayer()==1) {
						if (sql != null && !"".equals(sql)) {
							String countSql = "select count(*) from ("+sql+")";
							try {
								StringBuffer wherestr = new StringBuffer(" where ");
								Map<Integer,String> whereparamets = new LinkedHashMap<Integer,String>();
								int parametnum = 1;
								int num = 0;
								//拼接查询条件
								for (Parameter p:paramets.values()) {
									if (p.getValue() != null && !"".equals(p.getValue())) {
										if (num == 0) {
											if ("日期".equals(p.getType())) {
												wherestr.append(p.getName()+""+p.getComparemark()+"to_date(?, 'yyyy-mm-dd')");
											} else if ("日期时间".equals(p.getType())) {
												wherestr.append(p.getName()+""+p.getComparemark()+"to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
											} else {
												wherestr.append(p.getName()+""+p.getComparemark()+"?");
											}
										} else {
											if ("日期".equals(p.getType())) {
												wherestr.append(" and "+p.getName()+""+p.getComparemark()+"to_date(?, 'yyyy-mm-dd')");
											} else if ("日期时间".equals(p.getType())) {
												wherestr.append(" and "+p.getName()+""+p.getComparemark()+"to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
											} else {
												wherestr.append(" and "+p.getName()+""+p.getComparemark()+"?");
											}
										}
										whereparamets.put(parametnum, p.getValue());
										parametnum++;
										num ++;
									}
								}
								spstatement = conn.prepareStatement(countSql+wherestr.toString());
								for (int i:whereparamets.keySet()) {
									spstatement.setString(i, whereparamets.get(i));
								}
								rs = spstatement.executeQuery();
								if (rs.next()) {
									totalcount = rs.getInt(1);
								}
							} catch (SQLException e) {
								resultcode = Constant.FAIL;
								resultmsg = "查询异常";
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//计算总页数、当前页记录数
							if (pagesize != -1) {
								totalpage = totalcount % pagesize == 0 ? totalcount / pagesize : totalcount / pagesize + 1;
								if (pagenum > totalpage) {
									currentcount = 0;
								} else {
									currentcount = pagenum < totalpage ? pagesize : totalcount - (pagenum - 1) * pagesize;
								}
								currentpage = pagenum;
							} else {
								currentcount = totalcount;
							}
						}
					}
					//生成查询结果数据
					initNodeData(node,conn,null,serviceparamets,datamap,service.isYnpage());
				}
				
				returnmap.put("result", resultmap);
				resultmap.put("resultcode", resultcode);
				resultmap.put("resultmsg", resultmsg);
				if (Constant.SUCCESS.equals(resultcode) && datamap != null && !datamap.isEmpty()) {
					resultmap.put("resultdata", resultdatamap);
					if (service.isYnpage()) {
						resultdatamap.put("totalcount", totalcount);
						resultdatamap.put("pagesize", pagesize);
						resultdatamap.put("totalpage", totalpage);
						resultdatamap.put("currentpage", currentpage);
						resultdatamap.put("currentcount", currentcount);
					}
					resultdatamap.put("data", datamap);
				}
			}
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (spstatement != null) {
					spstatement.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//生成查询结果
			if (returnmap != null) {
				try {
					if ("xml".equalsIgnoreCase(service.getIoFormat())) {
						returnStr = XmlUtil.formatXml(XmlUtil.map2xml(returnmap));
					} else if ("json".equalsIgnoreCase(service.getIoFormat())) {
						returnStr = JsonUtil.obj2Json(returnmap);
					} else {
						returnStr = XmlUtil.formatXml(XmlUtil.map2xml(returnmap));
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
		return returnStr;
	}
	
	/**
	 * 生成节点数据
	 * @param node 根结点
	 * @param conn 数据库链接
	 * @param parentrs 数据集
	 * @param serviceparamets 接口查询参数
	 * @param nodedatas 节点数据
	 * @param ynpage 是否分页
	 */
	private void initNodeData(Node node,Connection conn,ResultSet parentrs,Map<String,String> serviceparamets,Map<String,Object> nodedatas,boolean ynpage) {
		if (node != null) {
			PreparedStatement spstatement = null;
			ResultSet rs = null;
			String sql = node.getSql();
			Map<String,Parameter> paramets = node.getParameters();
			List<Attribute> attributes = node.getAttributes();
			List<Node> subnodes = node.getSubNodes();
			if (sql != null && !"".equals(sql)) {
				Map<String,Object> datamap = new LinkedHashMap<String,Object>();
				List<Map<String,Object>> datalist = null;
				Map<String,Object> attrdatas = null;
				nodedatas.put(node.getNodeName()+"s", datamap);
				try {
					String querysql = sql;
					spstatement = conn.prepareStatement(querysql);
					if (node.getLayer()==1) {
						querysql ="select * from (" + sql +")";
						StringBuffer wherestr = new StringBuffer(" where ");
						Map<Integer,String> whereparamets = new LinkedHashMap<Integer,String>();
						int parametnum = 1;
						int num = 0;
						//拼接查询条件
						for (Parameter p:paramets.values()) {
							if (p.getValue() != null && !"".equals(p.getValue())) {
								if (num == 0) {
									if ("日期".equals(p.getType())) {
										wherestr.append(p.getName()+""+p.getComparemark()+"to_date(?, 'yyyy-mm-dd')");
									} else if ("日期时间".equals(p.getType())) {
										wherestr.append(p.getName()+""+p.getComparemark()+"to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
									} else {
										wherestr.append(p.getName()+""+p.getComparemark()+"?");
									}
								} else {
									if ("日期".equals(p.getType())) {
										wherestr.append(" and "+p.getName()+""+p.getComparemark()+"to_date(?, 'yyyy-mm-dd')");
									} else if ("日期时间".equals(p.getType())) {
										wherestr.append(" and "+p.getName()+""+p.getComparemark()+"to_date(?, 'yyyy-mm-dd hh24:mi:ss')");
									} else {
										wherestr.append(" and "+p.getName()+""+p.getComparemark()+"?");
									}
								}
								whereparamets.put(parametnum, p.getValue());
								parametnum++;
								num ++;
							}
						}
						querysql = querysql + wherestr.toString();
						spstatement = conn.prepareStatement(querysql);
						
						//分页
						if (ynpage) {
							//每页记录数
							int pagesize = Integer.valueOf(serviceparamets.get("pagesize"));
							//当前页码
							int pagenum = Integer.valueOf(serviceparamets.get("pagenum"));
							querysql = "select * from (select a.*,rownum rn from ("
									+ querysql + ") a) where rn <= " + pagesize*pagenum + " "
											+ "and rn>="+ ((pagesize) * (pagenum - 1) + 1);
							spstatement = conn.prepareStatement(querysql);
						}
						
						for (int i:whereparamets.keySet()) {
							spstatement.setString(i, whereparamets.get(i));
						}
					} else {
						for (Parameter p:paramets.values()) {
							if (p.isFromParent() && p.getParentParamet() != null) {
								spstatement.setString(p.getSnum(), parentrs.getString(p.getParentParamet()));
							} else {
								spstatement.setString(p.getSnum(), p.getValue());
							}
						}
					}
					rs = spstatement.executeQuery();
					while (rs.next()) {
						if (datamap.containsKey(node.getNodeName())) {
							datalist = (List<Map<String, Object>>) datamap.get(node.getNodeName());
						} else {
							datalist = new LinkedList<Map<String,Object>>();
							datamap.put(node.getNodeName(), datalist);
						}
						attrdatas = new LinkedHashMap<String,Object>();
						datalist.add(attrdatas);
						for (Attribute attr:attributes) {
							if ("属性".equals(attr.getSourceType())) {
								attrdatas.put(attr.getFaceName().toLowerCase(), rs.getString(attr.getColumAlias()));
							} else if ("固定值".equals(attr.getSourceType())) {
								attrdatas.put(attr.getFaceName().toLowerCase(), attr.getValue());
							}
						}
						//递归调用,循环子节点
						if (subnodes != null && !subnodes.isEmpty()) {
							for (Node n:subnodes) {
								initNodeData(n,conn,rs,null,attrdatas,false);
							}
						}
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						if (rs != null) {
							rs.close();
						}
						if (spstatement != null) {
							spstatement.close();
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}
}
