package com.shuto.mam.webclient.beans.sjsq;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.Utility;

/**
 * @Title: PostBean.java
 * @Description: 部门管理 子表Bean
 * @author: lull lull@shuto.cn
 * @date: 2017年4月18日 下午2:10:53
 * @version: V1.0.0
 */
public class PostBean extends DataBean {
	private String groupNum = "";// 安全组编号
	private String groupDesc = "";// 安全组描述
	private String personGroupNum = "";// 人员组、角色编号
	private String personGroupDesc = "";// 人员组、角色描述

	/**
	 * 
	 * <p>
	 * 安全组命名：地点+2位流水; 描述：地点+岗位
	 * <p>
	 * 人员组命名：地点+岗位编码或地点+岗位编码+专业; 描述：地点+部门+岗位
	 * <p>
	 * 角色命名：地点+岗位编码或地点+岗位编码+专业; 描述：地点+部门+岗位
	 * 
	 * @Title: CONAUTH
	 * 
	 * @Description: 新增安全组按钮
	 * @return
	 */
	public int CONAUTH() {
		try {
			MboRemote appMbo = app.getAppBean().getMbo();
			String depnum = appMbo.getString("DEPNUM");
			String siteid = appMbo.getString("SITEID");
			String orgid = appMbo.getString("orgid");
			String postnum = this.getMbo().getString("postnum");
			groupDesc = appMbo.getString("SITEID.description") + "_" + getMbo().getString("description");
			personGroupNum = siteid + "_" + depnum + "_" + postnum;
			personGroupDesc = appMbo.getString("SITEID.description") + "_" + appMbo.getString("description") + "_"
					+ getMbo().getString("description");
			MboSetRemote AuthSet = this.getMbo().getMboSet("$AUTHORITYGX", "AUTHORITYGX",
					"department = '" + depnum + "' and siteid = '" + siteid + "' and postnum = '" + postnum + "'");
			/*
			 * 查看部门关联安全组是否存在 a:如果不存在则新建安全组,安全组groupname规则siteid_2位流水；描述规则地点描述_岗位
			 * b:如果存在则不处理。
			 */

			if (AuthSet.isEmpty()) {
				MboSetRemote MaxGroupSet = appMbo.getMboSet("$MAXGROUP", "MAXGROUP",
						"groupname like '" + siteid + "_%'");
				if (MaxGroupSet.isEmpty()) {
					groupNum = siteid + "_01";
				} else {
					MaxGroupSet.setOrderBy("groupname desc ");
					MaxGroupSet.reset();
					int lenth = siteid.length() + 1;// 获取地点长度+1
					groupNum = MaxGroupSet.getMbo(0).getString("GROUPNAME");// 获取组织编码
					groupNum = groupNum.substring(lenth, groupNum.length());// 截取组织编码流水号
					int num = Integer.parseInt(groupNum) + 1;
					if (num < 10) {
						groupNum = siteid + "_0" + num;
					} else {
						groupNum = siteid + "_" + num;
					}
				}
				MaxGroupSet.clear();
				MaxGroupSet.close();
				// System.out.println(" gropunum = " + groupNum);
				MaxGroupSet = appMbo.getMboSet("$MAXGROUP", "MAXGROUP");
				MboRemote MaxGroup = MaxGroupSet.add(2l);
				MaxGroup.setValue("GROUPNAME", groupNum, 11l);
				MaxGroup.setValue("DESCRIPTION", groupDesc, 11l);
				MaxGroupSet.save();
				MaxGroupSet.clear();
				MaxGroupSet.close();

				String yetai = appMbo.getString("SITEID.YETAI");// 组织级别
				String sql = "";
				if ("控股".equals(yetai)) {
					sql = "ACTIVE = 1 ";
				} else if ("本部".equals(yetai)) {
					sql = "ORGID = '" + orgid + "' and ACTIVE = 1";
				} else {
					sql = "SITEID = '" + siteid + "'";
				}
				MboSetRemote siteSet = appMbo.getMboSet("$SITE", "SITE", sql);
				if (!siteSet.isEmpty()) {
					MboSetRemote siteAuthSet = appMbo.getMboSet("$SITEAUTH", "SITEAUTH");
					MboRemote site;
					for (int i = 0; ((site = siteSet.getMbo(i)) != null); i++) {
						MboRemote siteAuth = siteAuthSet.add(2l);
						siteAuth.setValue("GROUPNAME", groupNum, 11l);
						siteAuth.setValue("SITEID", site.getString("SITEID"), 11l);
						siteAuth.setValue("ORGID", site.getString("ORGID"), 11l);

					}
					siteAuthSet.save();
					siteAuthSet.clear();
					siteAuthSet.close();
				}
				siteSet.clear();
				siteSet.close();

				MboRemote auth = AuthSet.add(2l);
				auth.setValue("ORGID", orgid, 11l);
				auth.setValue("SITEID", siteid, 11l);
				auth.setValue("DEPARTMENT", depnum, 11l);
				auth.setValue("POSTNUM", postnum, 11l);
				auth.setValue("GROUPNAME", groupNum, 11l);
				auth.setValue("DESCRIPTION", groupDesc, 11l);
				AuthSet.save();

			}

			AuthSet.clear();
			AuthSet.close();
			/************************** 人员组 *************************************************/
			MboSetRemote usercPergroupSet = getMbo().getMboSet("$USERCPERGROUP", "USERCPERGROUP", "depnum = '" + depnum
					+ "' and siteid = '" + siteid + "' and postnum = '" + postnum + "' and professionnum is null");
			if (usercPergroupSet.isEmpty()) {
				MboSetRemote personGroupSet = appMbo.getMboSet("$persongroup", "persongroup",
						"persongroup = '" + personGroupNum + "'");
				if (personGroupSet.isEmpty()) {
					MboRemote personGroup = personGroupSet.add(2l);
					personGroup.setValue("persongroup", personGroupNum, 11l);
					personGroup.setValue("description", personGroupDesc, 11l);
					personGroupSet.save();
					// 新增角色
					addMaxRole();
				}
				personGroupSet.clear();
				personGroupSet.close();

				MboRemote usercPergroup = usercPergroupSet.add(2l);
				usercPergroup.setValue("orgid", orgid, 11l);
				usercPergroup.setValue("siteid", siteid, 11l);
				usercPergroup.setValue("depnum", depnum, 11l);
				usercPergroup.setValue("postnum", postnum, 11l);
				usercPergroup.setValue("persongroup", personGroupNum, 11l);
				usercPergroup.setValue("description", personGroupDesc, 11l);
				usercPergroupSet.save();

			}
			usercPergroupSet.clear();
			usercPergroupSet.close();

		} catch (RemoteException e) {
			e.printStackTrace();
		} catch (MXException e) {
			e.printStackTrace();
		}
		app.getAppBean().refreshTable();
		app.getDataBean("1492679056199").reloadTable();
		app.getDataBean("1492679056199").refreshTable();
		app.getDataBean("1492679530923").reloadTable();
		app.getDataBean("1492679530923").refreshTable();
		Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "hasnoAuth", "complete", null);
		return 1;
	}

	/**
	 * <p>
	 * 
	 * @Title: addMaxRole
	 * 
	 * @Description: 新建角色
	 * @param persongroup
	 * @param desc
	 * @throws MXException
	 * @throws RemoteException
	 */
	private void addMaxRole() throws RemoteException, MXException {
		MboSetRemote maxRoleSet = getMbo().getMboSet("$MAXROLE", "MAXROLE", "maxrole = '" + personGroupNum + "'");// MAXROLE
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
	 * @Title: getMaxRoleNum
	 * 
	 * @Description: TODO
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

}
