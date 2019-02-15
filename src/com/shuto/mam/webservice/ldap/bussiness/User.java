/**   
* @Title: MaxUser.java 
* @Package com.shuto.mam.webservice.SynUser.bussiness 
* @Description: TODO(用一句话描述该文件做什么) 
* @author lull lull@shuto.cn
* @date 2017年5月18日 下午10:43:04 
* @version V1.0.0
*/
package com.shuto.mam.webservice.ldap.bussiness;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.shuto.mam.webservice.bean.Element;
import com.shuto.mam.webservice.bean.InfMap;
import com.shuto.mam.webservice.util.Config;
import com.shuto.mam.webservice.util.MboTools;

import psdi.app.signature.MaxUserRemote;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

/**
 * @ClassName: MaxUser
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author lull lull@shuto.cn
 * @date 2017年5月18日 下午10:43:04
 * @changeby zhenglb@shuto.cn 
 * @changedate 2017年11月06日  15:05:04
 * 
 */
public class User {

	public static String S000A000 = result("S000A000", "操作成功");// 操作成功
	public static String E0003001 = result("E0003001", "操作失败");// 操作失败
	public static String E0003002 = result("E0003002", "用户不存在");// 用户不存在
	public static String E0003003 = result("E0003003", "用户已存在");// 用户已存在
	public static String E0003004 = result("E0003004", "报文无法识别");// 报文无法识别
	public static String E0003005 = result("E0003005", "令牌错误");// 令牌错误
	public static String E0003006 = result("E0003006", "用户名为空");// 用户名为空
	public static String E0003007 = result("E0003007", "邮箱已被使用");// 邮箱已被使用
	public static String E0003099 = result("E0003099", "无法识别的操作");// 无法识别的操作
	
	public static Map<String,InfMap> ORGMAP = new HashMap<String,InfMap>();
	public static Map<String,String> DEPMAP = new HashMap<String,String>();

	/**
	 * 获取MAXIMO组织、地点
	 * @param inforg
	 * @return
	 */
	private InfMap findOrg(String inforg) {
		InfMap maxorg = null;
		if (ORGMAP.get(inforg) == null) {
			MboSetRemote infmapSet;
			try {
				infmapSet = MXServer.getMXServer().getMboSet("INFMAP",
						MXServer.getMXServer().getSystemUserInfo());
				infmapSet.setWhere("business='LDAP-ORG' and infval='"+inforg+"'");
				infmapSet.reset();
				if (infmapSet != null && !infmapSet.isEmpty()) {
					maxorg = new InfMap();
					maxorg.setMaxorg(infmapSet.getMbo(0).getString("maxorg"));
					maxorg.setMaxsite(infmapSet.getMbo(0).getString("maxsite"));
					ORGMAP.put(inforg, maxorg);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
			maxorg = ORGMAP.get(inforg);
		}
		return maxorg;
	}
	
	/**
	 * 获取MAXIMO部门信息
	 * @param infdep
	 * @return
	 */
	private String findDep(String infdep) {
		String maxdep = null;
		if (DEPMAP.get(infdep) == null) {
			MboSetRemote infmapSet;
			try {
				infmapSet = MXServer.getMXServer().getMboSet("INFMAP",
						MXServer.getMXServer().getSystemUserInfo());
				infmapSet.setWhere("business='LDAP-DEP' and infval='"+infdep+"'");
				infmapSet.reset();
				if (infmapSet != null && !infmapSet.isEmpty()) {
					maxdep = infmapSet.getMbo(0).getString("maxval");
					DEPMAP.put(infdep, maxdep);
				}
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MXException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			maxdep = DEPMAP.get(infdep);
		}
		return maxdep;
	}
	
	public String synUser(String guid, String password, String requestData) {
		System.out.println("=================User.START====================================");
		String username = Config.dao.getProperty("user.username");
		String pass = Config.dao.getProperty("user.password");
		String result = E0003005;
		String type = "";
		String data = "";
		// 判断令牌是否正确
		if (username.equals(guid) && pass.equalsIgnoreCase(password)) {
			try {
				Element operate = Element.parseElement(requestData);
				List<Element> list = Element.parseElements(operate.getValue());
				for (Element e : list) {
					if (e.getKey().equals("operate-type")) {
						type = e.getValue();
					} else if (e.getKey().equals("user")) {
						data = e.getValue();
					}
				}
				System.out.println("========================== type : " + type);
				// 校验登录用户账号是否为空并解析报文判断报文是否正确
				Map<String, String> user = null;
				user = Element.parseElementsMap(data);
				String ldaploginid = user.get("uid").toUpperCase();
				if (ldaploginid != null && ldaploginid.length() > 0) {
					if (type.equalsIgnoreCase("CREATE")) {// 新增用户 A
						result = CREATE(user);
					} else if (type.equalsIgnoreCase("EDITER")) {// 修改用户 U
						result = EDITER(user);
					} else if (type.equalsIgnoreCase("DISABLE")) {// 禁用用户 D
						result = DISABLE(ldaploginid);
					} else if (type.equalsIgnoreCase("ENABLE")) {// 启用用户 U
						result = ENABLE(ldaploginid);
					} else if (type.equalsIgnoreCase("DELETE")) {// 删除用户 D
						result = DELETE(ldaploginid);
					} else {
						result = (E0003099);
					}
				} else {
					result = (E0003006);
				}

			} catch (Exception e) {
				e.printStackTrace();
				result = (E0003004);
			}
		}
		loginfo(requestData, type, result);
		System.out.println("=================User.END====================================");
		return result;
	}

	/**
	 * 新增用户操作
	 * 
	 * @param data
	 *            用户信息数据
	 * @return 操作结果
	 */
	public String CREATE(Map<String, String> user) {// 新增用户
		System.out.println(" -----------------------新增用户 .start---------------------------------- ");
		MboSetRemote maxuserSet;
		try {
			String ldaploginid = user.get("uid").toUpperCase();
			String ldappassword = user.get("passWord");
			String orginfo = user.get("meno");
			String inforg = null;
			String infdep = null;
			String maxorg = null;
			String maxsite = null;
			String maxdep = null;
			//解析LDAP组织、部门信息(如：CR003_铜山华润电力有限公司-技术支持部_1000128)
			if (orginfo != null && !"".equals(orginfo)) {
				if (orginfo.split("_") != null && orginfo.split("_").length > 1) {
					if (orginfo.split("_")[1] != null && orginfo.split("_")[1].split("-") != null) {
						inforg = orginfo.split("_")[1].split("-")[0];
						if (orginfo.split("_")[1].split("-").length > 1) {
							infdep = orginfo.split("_")[1].split("-")[1];
						}
					}
				}
				//获取映射信息
				if (findOrg(inforg) != null) {
					maxorg = findOrg(inforg).getMaxorg();
					maxsite = findOrg(inforg).getMaxsite();
				}
				if (findDep(infdep) != null) {
					maxdep = findDep(infdep);
				}
			}
			System.out.println("ldaploginid = " + ldaploginid + " ldappassword = " + ldappassword);
			maxuserSet = MXServer.getMXServer().getMboSet("MAXUSER", MXServer.getMXServer().getSystemUserInfo());
			maxuserSet.setWhere("loginid='" + ldaploginid + "'");
			maxuserSet.reset();
			if (maxuserSet.getMbo(0) != null && maxuserSet.getMbo(1) == null) {// 如果用户已存在
				return ENABLE(ldaploginid);
			}
			MaxUserRemote maxuser = (MaxUserRemote) maxuserSet.add(11l);// 新增一个用户
			maxuser.setValue("userid", ldaploginid, 11l);
			maxuser.setValue("personid", ldaploginid, 11L);
			maxuser.setValue("loginid", ldaploginid, 11L);
			maxuser.setValue("datastatus", "A", 11l);
			maxuser.setValue("statusdate", new Date(), 11l);
			maxuser.setValue("ldappassword", ldappassword, 11L);
			if (maxsite != null) {
				maxuser.setValue("defsite", maxsite, 11L);
			}
			MboRemote person = null;
			MboSetRemote personSet = MXServer.getMXServer().getMboSet("PERSON",
					MXServer.getMXServer().getSystemUserInfo());
			personSet.setWhere("personid = '" + ldaploginid + "'");
			personSet.reset();
			if (personSet.isEmpty()) {
				person = personSet.add(11l);
				person.setValue("personid", ldaploginid, 11l);
				person.setValue("datastatus", "A", 11l);
				person.setValue("statusdate", new Date(), 11l);
				person.setValue("DISPLAYNAME", user.get("displayName"), 11l);
				if (user.get("mobile") != null && user.get("mobile").length() > 0) {
					person.setValue("MOBILEPHONE", user.get("mobile"), 11L);
				}
				if (maxorg != null) {
					person.setValue("locationorg", maxorg, 11L);
				}
				if (maxsite != null) {
					person.setValue("locationsite", maxsite, 11L);
				}
				if (maxdep != null) {
					person.setValue("department", maxdep, 11L);
				}
				personSet.save();
			}

			// maxuser.addGroupUser();// 添加新用户到默认组
			maxuser.setValue("EMAILPSWD", false, 11L);
			maxuser.setValue("FORCEEXPIRATION", false, 11L);
			maxuser.generatePassword();// 为新用户生成密码
			maxuserSet.save();

			if (user.get("email") != null && user.get("email").length() > 0) {
				MboSetRemote emailSet = person.getMboSet("EMAIL");
				try {
					MboRemote email = emailSet.add();
					email.setValue("EMAILADDRESS", user.get("email"));
					email.setValue("TYPE", "工作");
					emailSet.save();
				} catch (MXApplicationException e) {
					System.out.println(e.getMessage());
				}
			}
			personSet.close();
			maxuserSet.close();

			/** 添加到everyone安全组**/
			MboSetRemote groupUserSet = MboTools.getMboSet("groupuser");
			groupUserSet.setWhere("GROUPNAME ='MAXEVERYONE'  and USERID = '" + ldaploginid + "'");
			groupUserSet.reset();
			if (groupUserSet.isEmpty()) {
				MboRemote groupUser = groupUserSet.add(11L);
				groupUser.setValue("USERID", ldaploginid, 11l);
				groupUser.setValue("GROUPNAME", "MAXEVERYONE", 11L);
				groupUserSet.save();
			}

			groupUserSet.close();

			System.out.println(" -----------------------新增用户 .end---------------------------------- ");
			return S000A000;
		} catch (RemoteException e1) {
			e1.printStackTrace();
		} catch (MXException e1) {
			e1.printStackTrace();
		}
		return (E0003001);
	}

	/**
	 * 修改用户
	 * 
	 * @param data
	 *            待修改的用户数据
	 * @return 修改结果
	 */
	public String EDITER(Map<String, String> user) {// 修改用户
		MboSetRemote maxuserSet;
		try {
			String ldaploginid = user.get("uid").toUpperCase();
			String ldappassword = user.get("passWord");
			String orginfo = user.get("meno");
			String inforg = null;
			String infdep = null;
			String maxorg = null;
			String maxsite = null;
			String maxdep = null;
			//解析LDAP组织、部门信息(如：CR003_铜山华润电力有限公司-技术支持部_1000128)
			if (orginfo != null && !"".equals(orginfo)) {
				if (orginfo.split("_") != null && orginfo.split("_").length > 1) {
					if (orginfo.split("_")[1] != null && orginfo.split("_")[1].split("-") != null) {
						inforg = orginfo.split("_")[1].split("-")[0];
						if (orginfo.split("_")[1].split("-").length > 1) {
							infdep = orginfo.split("_")[1].split("-")[1];
						}
					}
				}
				//获取映射信息
				if (findOrg(inforg) != null) {
					maxorg = findOrg(inforg).getMaxorg();
					maxsite = findOrg(inforg).getMaxsite();
				}
				if (findDep(infdep) != null) {
					maxdep = findDep(infdep);
				}
			}
			
			maxuserSet = MXServer.getMXServer().getMboSet("MAXUSER", MXServer.getMXServer().getSystemUserInfo());
			maxuserSet.setWhere("personid='" + ldaploginid + "'");
			maxuserSet.reset();
			if (maxuserSet.isEmpty()) {// 判断用户是否存在
				return E0003002;
			}

			MboRemote maxuser = maxuserSet.getMbo(0);
			maxuser.setValue("ldappassword", ldappassword, 11L);
			maxuser.setValue("datastatus", "U", 11l);
			maxuser.setValue("statusdate", new Date(), 11l);
			if (maxsite != null) {
				maxuser.setValue("defsite", maxsite, 11L);
			}
			maxuserSet.save();

			MboSetRemote personSet = maxuser.getMboSet("PERSON");
			MboRemote person = personSet.getMbo(0);
			person.setValue("datastatus", "U", 11l);
			person.setValue("statusdate", new Date(), 11l);
			if (user.get("displayName") != null && user.get("displayName").length() > 0) {// 更新显示名称
				person.setValue("DISPLAYNAME", user.get("displayName"));
			}
			if (user.get("mobile") != null && user.get("mobile").length() > 0) {// 更新电话信息
				person.setValue("MOBILEPHONE", user.get("mobile"), 11L);
			} 
			if (maxorg != null) {
				person.setValue("locationorg", maxorg, 11L);
			}
			if (maxsite != null) {
				person.setValue("locationsite", maxsite, 11L);
			}
			if (maxdep != null) {
				person.setValue("department", maxdep, 11L);
			}
			
			/*else if (user.get("mobile") == null) {
				person.setValue("MOBILEPHONE", "", 11L);
			}
			*/
			personSet.save();
			personSet.close();

			if (user.get("email") != null && user.get("email").length() > 0) {// 更新邮箱信息
				MboSetRemote emailSet = person.getMboSet("EMAIL");
				MboRemote email;
				if (emailSet.isEmpty()) {
					email = emailSet.add();
				} else {
					email = emailSet.getMbo(0);
				}
				try {
					email.setValue("EMAILADDRESS", user.get("email"), 2L);
					emailSet.save();
					emailSet.close();
				} catch (MXApplicationException e) {
					return E0003007;// 邮箱已存在时返回执行失败
				}
			} 
			/*else if (user.get("email") == null) {// 删除邮箱信息
				MboSetRemote emailSet = person.getMboSet("EMAIL");
				MboRemote email;
				if (emailSet.count() > 0) {
					email = emailSet.getMbo(0);
					email.delete(11L);
					emailSet.save();
					emailSet.close();
				}
			}*/
			maxuserSet.close();
			return (S000A000);
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		return E0003001;
	}

	private int Sql(String sql) {
		System.out.println("sql = " + sql);
		Connection con = null;
		Statement stmt = null;
		try {
			con = MXServer.getMXServer().getDBManager().getSequenceConnection();
			stmt = con.createStatement();
			return stmt.executeUpdate(sql);
		} catch (Exception e1) {
			e1.printStackTrace();
			return 0;
		} finally {
			if (stmt != null) {
				try {
					stmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/**
	 * 禁用用户
	 * 
	 * @param data
	 *            待禁用的用户信息
	 * @return 操作结果
	 */
	public String DISABLE(String ldaploginid) {// 禁用用户
		String maxuserSql = "update maxuser set status = '不活动' ,statusdate = sysdate, datastatus = 'D' where personid = '"
				+ ldaploginid + "' ";
		Sql(maxuserSql);
		String personSql = "update person set status = '不活动' ,statusdate = sysdate, datastatus = 'D' where personid = '"
				+ ldaploginid + "'";
		Sql(personSql);
		return S000A000;

	}

	/**
	 * 启用用户
	 * 
	 * @param data 待启用的用户信息
	 * @return 操作结果
	 */
	public String ENABLE(String ldaploginid) {
		String maxuserSql = "update maxuser set status = '活动' ,statusdate = sysdate, datastatus = 'U' where personid = '"
				+ ldaploginid + "' ";
		Sql(maxuserSql);
		String personSql = "update person set status = '活动' ,statusdate = sysdate, datastatus = 'U' where personid = '"
				+ ldaploginid + "'";
		Sql(personSql);
		return S000A000;
	}

	/**
	 * 删除用户(由于系统需求,不提供删除操作,改为禁用用户)
	 * 
	 * @param data
	 *            待删除的用户信息
	 * @return 操作结果
	 */
	public String DELETE(String data) {// 删除用户
		// 系统不支持删除操作,改为禁用
		return DISABLE(data);
	}

	private static String result(String code, String msg) {
		return "<?xml version=\"1.0\"?>" + new Element("result",
				new Element[] { new Element("result_code", code), new Element("result_msg", msg) }).toString();
	}

	/**
	 * 记录日志
	 * 
	 * @param requestData
	 * @param result
	 * @throws MXException
	 * @throws RemoteException
	 */
	private void loginfo(String requestData, String type, String result) {
		try {
			MboSetRemote logSet = MXServer.getMXServer().getMboSet("interfacelog",
					MXServer.getMXServer().getSystemUserInfo());
			MboRemote logMbo = logSet.add();
			logMbo.setValue("SYNCHTYPE", type, 11l);
			logMbo.setValue("synchdesc", result, 11l);
			logMbo.setValue("SYNCHDATE", new Date(), 11l);
			logMbo.setValue("SYNCHSTATUS", "", 11l);
			logMbo.setValue("DESCRIPTION", "LDAP用户推送", 11l);
			logMbo.setValue("DATALOG", requestData);
			logSet.save();
			logSet.close();
		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}

	}
	
	public static void main(String[] args) {
		String orginfo = "CR003_华润电力江苏检修有限公司徐州分公司-徐州/铜山项目部_1001001";
		String inforg = null;
		String infdep = null;
		//解析LDAP组织、部门信息(如：CR003_铜山华润电力有限公司-技术支持部_1000128)
		if (orginfo != null && !"".equals(orginfo)) {
			if (orginfo.split("_") != null && orginfo.split("_").length > 1) {
				if (orginfo.split("_")[1] != null && orginfo.split("_")[1].split("-") != null) {
					inforg = orginfo.split("_")[1].split("-")[0];
					if (orginfo.split("_")[1].split("-").length > 1) {
						infdep = orginfo.split("_")[1].split("-")[1];
					}
				}
			}
		}
		System.out.println(inforg);
		System.out.println(infdep);
	}
}
