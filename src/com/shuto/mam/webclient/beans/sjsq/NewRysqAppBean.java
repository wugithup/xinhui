package com.shuto.mam.webclient.beans.sjsq;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**
 * 新人员授权页面类 com.shuto.mam.webclient.beans.sjsq.NewRysqAppBean
 * 
 * @author shanbh shanbh@shuoto.cn
 * @date 2017年3月15日 上午9:18:32
 * @Copyright: 2017 Shuto版权所有
 * @version V1.0
 */
public class NewRysqAppBean extends AppBean {
	private String groupNum = "";// 安全组编号
	private String personGroupNum = "";// 人员组、角色编号
	private String personGroupDesc = "";// 人员组、角色描述

	/**
	 * <p>
	 * Title: SAVE
	 * <p>
	 * 安全组命名：地点+2位流水; 描述：地点+岗位
	 * <p>
	 * 人员组命名：地点+岗位编码或地点+岗位编码+专业; 描述：地点+部门+岗位
	 * <p>
	 * 角色命名：地点+岗位编码或地点+岗位编码+专业; 描述：地点+部门+岗位 Description:
	 * 
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 * @see psdi.webclient.system.beans.AppBean#SAVE()
	 */
	@Override
	public int SAVE() throws MXException, RemoteException {
		super.SAVE();
		try {
			String siteid = getMbo().getString("locationsite");
			String orgid = getMbo().getString("locationorg");
			String depnum = getMbo().getString("department");
			String postnum = getMbo().getString("post");
			String professionnum = getMbo().getString("profession");
			String personid = getMbo().getString("personid");

			// 保持用户表地点与人员地点一致
			MboSetRemote maxUserSet = getMbo().getMboSet("$maxuser", "maxuser", "userid = '" + personid + "'");
			if (!maxUserSet.isEmpty()) {
				maxUserSet.getMbo(0).setValue("defsite", siteid, 2l);
				maxUserSet.save();
			}
			maxUserSet.clear();
			maxUserSet.close();

			/*
			 * 根据权限映射表 插入关联的权限表
			 */
			MboSetRemote AuthSet = this.getMbo().getMboSet("$AUTHORITYGX", "AUTHORITYGX",
					"department = '" + depnum + "' and siteid = '" + siteid + "' and postnum = '" + postnum + "'");
			/*
			 * 映射表中查询不到，则提示先维护部门管理
			 */
			if (AuthSet.isEmpty()) {
				// System.out.println(" 请先维护部门，再进行人员授权！");
				AuthSet.clear();
				AuthSet.close();
				return 1;
			}

			// 清空原安全组中人员
			MboSetRemote groupUserSet = getMbo().getMboSet("$GROUPUSER", "GROUPUSER", " userid = '" + personid + "'");
			if (!groupUserSet.isEmpty()) {
				groupUserSet.deleteAll(11l);
				groupUserSet.save();
			}
			groupUserSet.clear();
			groupUserSet.close();

			MboRemote Auth;
			for (int i = 0; ((Auth = AuthSet.getMbo(i)) != null); i++) {
				groupNum = Auth.getString("groupname");
				MboRemote goupUser = groupUserSet.add(2l);
				goupUser.setValue("groupname", groupNum, 11l);
				goupUser.setValue("userid", personid, 11l);
				groupUserSet.save();
			}
			/**
			 * 判断当前人是否在MAXEVERYONE组中。
			 */
			groupUserSet = getMbo().getMboSet("$GROUPUSER", "GROUPUSER",
					"groupname = 'MAXEVERYONE' and userid = '" + personid + "'");
			if (groupUserSet.isEmpty()) {
				MboRemote goupUser = groupUserSet.add(2l);
				goupUser.setValue("groupname", "MAXEVERYONE", 11l);
				goupUser.setValue("userid", personid, 11l);
				groupUserSet.save();

			}
			groupUserSet.clear();
			groupUserSet.close();
			AuthSet.clear();
			AuthSet.close();

			/*
			 * 根据部门、岗位与人员组映射表关系
			 */
			MboSetRemote usercPergroupSet = getMbo().getMboSet("$USERCPERGROUP", "USERCPERGROUP", "depnum = '" + depnum
					+ "' and siteid = '" + siteid + "' and postnum = '" + postnum + "' and professionnum is null");
			if (usercPergroupSet.isEmpty()) {
				return 1;
			}

			/*
			 * 清空原人员组信息
			 */
			MboSetRemote personGroupTeamSet = getMbo().getMboSet("$PERSONGROUPTEAM", "PERSONGROUPTEAM",
					"resppartygroup = '" + personid + "'");
			if (!personGroupTeamSet.isEmpty()) {
				personGroupTeamSet.deleteAll(11l);
				personGroupTeamSet.save();
			}
			personGroupTeamSet.clear();
			personGroupTeamSet.close();

			for (int i = 0; i < usercPergroupSet.count(); i++) {
				MboRemote usercPergroup = usercPergroupSet.getMbo(i);
				personGroupNum = usercPergroup.getString("persongroup");
				insPerGroup(personid);
			}

			usercPergroupSet.clear();
			usercPergroupSet.close();

			// ---------------------------------------------------------------

			if (!"".equals(professionnum) && professionnum != null) {
				usercPergroupSet = getMbo().getMboSet("$USERCPERGROUP", "USERCPERGROUP",
						"depnum = '" + depnum + "' and siteid = '" + siteid + "' and postnum = '" + postnum
								+ "' and professionnum = '" + professionnum + "'");
				if (usercPergroupSet.isEmpty()) {
					personGroupNum = siteid + "_" + postnum + "_" + professionnum;// 人员组编码
					personGroupDesc = getMbo().getString("locationsite.description") + "_"
							+ getMbo().getString("post.description") + "_" + professionnum;// 人员组/角色描述
					addPersonGroup();// 新建人员组
					insPerGroup(personid);// 人员组增加人员
					addMaxRole();// 新建角色
					addUsercPergroup(orgid, siteid, personGroupDesc, depnum, postnum, professionnum, personGroupNum);// 增加关联表记录

				} else {
					for (int i = 0; i < usercPergroupSet.count(); i++) {
						MboRemote usercPergroup = usercPergroupSet.getMbo(i);
						personGroupNum = usercPergroup.getString("persongroup");
						insPerGroup(personid);
					}
				}

				usercPergroupSet.clear();
				usercPergroupSet.close();

			}

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		app.getAppBean().refreshTable();
		app.getDataBean("1492763571796").reloadTable();
		app.getDataBean("1492763571796").refreshTable();
		app.getDataBean("1492763579940").reloadTable();
		app.getDataBean("1492763579940").refreshTable();
		return 1;

	}

	/**
	 * 
	 * <p>
	 * 
	 * @Title: CONAUTH
	 * 
	 * @Description: 授予权限按钮
	 * @return
	 */
	public int CONAUTH() {
		return 1;
	}

	/**
	 * <p>
	 * 
	 * @Title: addUsercPergroup
	 * 
	 * @Description: 增加部门、岗位、专业与人员组映射表
	 * @param orgid
	 * @param siteid
	 * @param depnum
	 * @param postnum
	 * @param professionnum
	 * @param persongroup
	 * @throws MXException
	 * @throws RemoteException
	 */
	private void addUsercPergroup(String orgid, String siteid, String desc, String depnum, String postnum,
			String professionnum, String persongroup) throws RemoteException, MXException {
		MboSetRemote usercPergroupSet = getMbo().getMboSet("$USERCPERGROUP", "USERCPERGROUP");
		MboRemote usercPergroup = usercPergroupSet.add(11l);
		usercPergroup.setValue("orgid", orgid, 11l);
		usercPergroup.setValue("siteid", siteid, 11l);
		usercPergroup.setValue("description", desc, 11l);
		usercPergroup.setValue("depnum", depnum, 11l);
		usercPergroup.setValue("postnum", postnum, 11l);
		usercPergroup.setValue("professionnum", professionnum, 11l);
		usercPergroup.setValue("persongroup", persongroup, 11l);
		usercPergroupSet.save();
		usercPergroupSet.clear();
		usercPergroupSet.close();

	}

	/**
	 * <p>
	 * 
	 * @Title: addMaxRole
	 * 
	 * @Description: 新增角色
	 * @param persongroup
	 * @param desc
	 * @throws MXException
	 * @throws RemoteException
	 */
	private void addMaxRole() throws RemoteException, MXException {
		MboSetRemote maxRoleSet = getMbo().getMboSet("$MAXROLE", "MAXROLE", "MAXROLE = '" + personGroupNum + "'");// MAXROLE
		if (maxRoleSet.isEmpty()) {
			MboRemote maxRole = maxRoleSet.add(2l);
			maxRole.setValue("MAXROLE", personGroupNum, 11l);
			maxRole.setValue("DESCRIPTION", personGroupDesc, 11l);
			maxRole.setValue("TYPE", "人员组", 11l);
			maxRole.setValue("VALUE", personGroupNum, 11l);
			maxRole.setValue("ISBROADCAST", true, 11l);
			maxRoleSet.save();
		}

		maxRoleSet.clear();
		maxRoleSet.close();

	}

	/**
	 * <p>
	 * 
	 * @Title: addPersonGroup
	 * 
	 * @Description: 新增人员组
	 * @param persongroup
	 * @param desc
	 * @throws MXException
	 * @throws RemoteException
	 */
	private void addPersonGroup() throws RemoteException, MXException {
		MboSetRemote personGroupSet = getMbo().getMboSet("$persongroup", "persongroup",
				"persongroup = '" + personGroupNum + "'");
		if (personGroupSet.isEmpty()) {
			MboRemote personGroup = personGroupSet.add(2l);
			personGroup.setValue("persongroup", personGroupNum, 11l);
			personGroup.setValue("description", personGroupDesc, 11l);
			personGroupSet.save();
		}

		personGroupSet.clear();
		personGroupSet.close();

	}

	/**
	 * <p>
	 * 
	 * @Title: getMaxRole
	 * 
	 * @Description: 获取角色编码
	 * @param string
	 * @return
	 * @throws MXException
	 * @throws RemoteException
	 */
	private String getMaxRoleNum() throws RemoteException, MXException {
		MboSetRemote maxRoleSet = getMbo().getMboSet("$MAXROLE", "MAXROLE", "maxrole like 'JOB%'");
		String maxRoleNum = "";
		if (maxRoleSet.isEmpty()) {
			maxRoleNum = "JOB001";
		} else {
			maxRoleSet.setOrderBy("maxrole desc");
			maxRoleSet.reset();
			String maxRole = maxRoleSet.getMbo(0).getString("maxrole");
			maxRoleNum = maxRole.substring(3, maxRole.length());
			int num = Integer.parseInt(maxRoleNum) + 1;
			if (num < 10) {
				maxRoleNum = "JOB00" + num;
			} else if (num < 100) {
				maxRoleNum = "JOB0" + num;
			} else {
				maxRoleNum = "JOB" + num;
			}
		}
		maxRoleSet.clear();
		maxRoleSet.close();
		return maxRoleNum;
	}

	/**
	 * 
	 * <p>
	 * 
	 * @Title: insPerGroup
	 * 
	 * @Description: 将人员插入人员组中
	 * @param persongroup
	 * @param personid
	 * @throws RemoteException
	 * @throws MXException
	 * @throws SQLException
	 */
	public void insPerGroup(String personid) throws RemoteException, MXException, SQLException {
		MboSetRemote personGroupTeamSet = getMbo().getMboSet("$PERSONGROUPTEAM", "PERSONGROUPTEAM",
				"persongroup = '" + personGroupNum + "'");

		int resppartygroupseq = (int) personGroupTeamSet.max("resppartygroupseq") + 1;
		int resppartyseq = (int) personGroupTeamSet.max("resppartyseq") + 1;
		personGroupTeamSet.clear();
		personGroupTeamSet.close();

		String sql = "insert into PERSONGROUPTEAM (RESPPARTYGROUP, RESPPARTY, RESPPARTYGROUPSEQ, RESPPARTYSEQ, USEFORORG, USEFORSITE, GROUPDEFAULT, ORGDEFAULT, SITEDEFAULT, PERSONGROUPTEAMID, PERSONGROUP, ROWSTAMP, TYPE, JIZU, WORKTYPE, BC, CCXH, GZZ, ISCC, SFDB)values ('"
				+ personid + "', '" + personid + "', " + resppartygroupseq + ", " + resppartyseq
				+ ", null, null, 0, 0, 0, persongroupteamseq.nextval, '" + personGroupNum
				+ "', '139600015', null, null, null, null, null, 0, 0, 0)";
		Connection con = MXServer.getMXServer().getDBManager()
				.getConnection(MXServer.getMXServer().getSystemUserInfo().getConnectionKey());
		con.setAutoCommit(false);
		Statement stm = con.createStatement();
		stm.executeUpdate(sql);
		con.commit();
		stm.close();
		con.close();

	}

}