/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.webclient.beans.sjsq;

import com.shuto.mam.webclient.beans.base.CAppBean;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author lull lull@shuto.cn
 * @ClassName: AuthoritybgAppBean
 * @Description: 人员权限变更APPBEAN
 * @date 2017年5月9日 下午4:31:39
 */
public class AuthoritybgAppBean extends CAppBean {

    private String groupNum = "";// 安全组编号

    private String personGroupNum = "";// 人员组、角色编号

    private String personGroupDesc = "";// 人员组、角色描述

    @Override
    public int SAVE() throws MXException, RemoteException {

        MboRemote ThisMbo = app.getAppBean().getMbo();
        Date date = new Date();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = dateformat.format(date);
        ThisMbo.setValue("CHANGEPERSON", ThisMbo.getUserName(), 11L);
        ThisMbo.setValue("CHANGEDATE", time, 11L);
        return super.SAVE();
    }

    /**
     * 权限变更按钮
     */
    public int QXBG() {

        System.out.println("-=========QXBG.start===============-");
        try {
            MboRemote appMbo = app.getAppBean().getMbo();
            String status = appMbo.getString("STATUS");
            if ("已关闭".equals(status)) {
                return 0;
                // throw new MXApplicationException("提示", "已关闭状态不能再调整！");
            }

            String newOrgid = appMbo.getString("NEWORGID");// 新组织
            String newSiteid = appMbo.getString("NEWSITEID");// 新地点
            String newDepnum = appMbo.getString("DEPARTMENT");// 新部门
            String newPostnum = appMbo.getString("POSTNUM");// 新岗位
            String newProfessionnum = appMbo.getString("PROFESSIONNUM");// 新专业
            String personid = appMbo.getString("personid");// 变更的人员

            // 保持用户表地点与人员地点一致
            MboSetRemote maxUserSet =
                    appMbo.getMboSet("$maxuser", "maxuser", "userid = '" + personid + "'");
            if (maxUserSet.getMbo(0) != null && maxUserSet.getMbo(1) == null) {
                maxUserSet.getMbo(0).setValue("defsite", newSiteid, 2l);
                maxUserSet.save();
            }
            maxUserSet.clear();
            maxUserSet.close();

            // 变更person表信息
            MboSetRemote personSet =
                    appMbo.getMboSet("$person", "person", "personid = '" + personid + "'");
            if (personSet.getMbo(0) != null && personSet.getMbo(1) == null) {
                MboRemote person = personSet.getMbo(0);
                person.setValue("LOCATIONORG", newOrgid, 11l);
                person.setValue("LOCATIONSITE", newSiteid, 11l);
                person.setValue("DEPARTMENT", newDepnum, 11l);
                person.setValue("POST", newPostnum, 11l);
                person.setValue("PROFESSION", newProfessionnum, 11l);
                personSet.save();
            }
            personSet.clear();
            personSet.close();

            /* 变更安全组 */
            // 清空原安全组
            MboSetRemote groupUserSet = appMbo.getMboSet("$GROUPUSER", "GROUPUSER",
                    "userid = '" + personid + "' and groupname !='MAXEVERYONE'");
            if (!groupUserSet.isEmpty()) {
                groupUserSet.deleteAll(11l);
                groupUserSet.save();
            }
            groupUserSet.clear();
            groupUserSet.close();

            // 添加安全组
            MboSetRemote authSet = appMbo.getMboSet("$AUTHORITYGX", "AUTHORITYGX",
                    "siteid = '" + newSiteid + "' and department = '" + newDepnum +
                            "' and postnum = '" + newPostnum + "'");
            if (!authSet.isEmpty()) {
                MboRemote auth;
                for (int i = 0; ((auth = authSet.getMbo(i)) != null); i++) {
                    groupNum = auth.getString("groupname");
                    insertGoupUser(personid);
                }
            }
            authSet.clear();
            authSet.close();

            // 清空人员组
            MboSetRemote personGropuTeamSet =
                    getMbo().getMboSet("$PERSONGROUPTEAM", "PERSONGROUPTEAM",
                            "resppartygroup = '" + personid + "'");
            if (!personGropuTeamSet.isEmpty()) {
                deletepersonteam(personid);
            }
            personGropuTeamSet.close();

            /*根据部门、岗位与人员组关系，将人员插入到人员组中*/
            MboSetRemote usercPergroupSet = getMbo().getMboSet("$USERCPERGROUP", "USERCPERGROUP",
                    "depnum = '" + newDepnum + "' and siteid = '" + newSiteid +
                            "' and postnum = '" + newPostnum + "' and professionnum is null");
            if (!usercPergroupSet.isEmpty()) {
                MboRemote usercPergroup;
                for (int i = 0; ((usercPergroup = usercPergroupSet.getMbo(i)) != null); i++) {
                    personGroupNum = usercPergroup.getString("persongroup");
                    insPerGroup(personid);
                }
            }
            usercPergroupSet.clear();
            usercPergroupSet.close();
            if (!"".equals(newProfessionnum) && newProfessionnum != null) {
                usercPergroupSet = getMbo().getMboSet("$USERCPERGROUP", "USERCPERGROUP",
                        "depnum = '" + newDepnum + "' and siteid = '" + newSiteid +
                                "' and postnum = '" + newPostnum + "' and professionnum = '" +
                                newProfessionnum + "'");

                if (usercPergroupSet.isEmpty()) {
                    personGroupNum = newSiteid + "_" + newPostnum + "_" + newProfessionnum;// 人员组编码
                    personGroupDesc = getMbo().getString("locationsite.description") + "_" +
                            getMbo().getString("post.description") + "_" +
                            newProfessionnum;// 人员组/角色描述
                    addPersonGroup();// 新建人员组
                    insPerGroup(personid);// 人员组增加人员
                    addMaxRole();// 新建角色
                    addUsercPergroup(newOrgid, newSiteid, personGroupDesc, newDepnum, newPostnum,
                            newProfessionnum, personGroupNum);// 增加关联表记录

                } else {
                    MboRemote usercPergroup;
                    for (int i = 0; ((usercPergroup = usercPergroupSet.getMbo(i)) != null); i++) {
                        personGroupNum = usercPergroup.getString("persongroup");
                        insPerGroup(personid);
                    }
                }
                usercPergroupSet.clear();
                usercPergroupSet.close();
            }
            String newPersonid = appMbo.getString("NEWPERSONID");// 新人员
            if (!appMbo.isNull("NEWPERSONID")) {
                MboSetRemote wfassSet = appMbo.getMboSet("$WFASSIGNMENT", "WFASSIGNMENT",
                        "assignstatus ='活动' and assigncode = '" + personid + "' ");
                if (!wfassSet.isEmpty()) {
                    MboRemote wfass;
                    for (int i = 0; ((wfass = wfassSet.getMbo(i)) != null); i++) {
                        wfass.setValue("assigncode", newPersonid, 11l);
                        wfass.setValue("origperson", newPersonid, 11l);
                    }
                    wfassSet.save();
                }
                wfassSet.clear();
                wfassSet.close();
            }

            appMbo = app.getAppBean().getMbo();
            appMbo.setValue("status", "已关闭", 11l);
            app.getAppBean().save();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MXException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        app.getAppBean().refreshTable();
        app.getDataBean("1492767729611").reloadTable();
        app.getDataBean("1492767729611").refreshTable();
        app.getDataBean("1492767732403").reloadTable();
        app.getDataBean("1492767732403").refreshTable();
        System.out.println("===================QXBG.end===================================");
        return 1;
    }

    /**
     * <p>
     *
     * @param newOrgid
     * @param newSiteid
     * @param desc
     * @param newDepnum
     * @param newPostnum
     * @param newProfessionnum
     * @param persongroup
     * @throws MXException
     * @throws RemoteException
     * @Title: addUsercPergroup
     * @Description: TODO
     */
    private void addUsercPergroup(String newOrgid, String newSiteid, String desc, String newDepnum,
            String newPostnum, String newProfessionnum, String persongroup)
            throws RemoteException, MXException {

        MboSetRemote usercPergroupSet = getMbo().getMboSet("$USERCPERGROUP", "USERCPERGROUP");
        MboRemote usercPergroup = usercPergroupSet.add(11l);
        usercPergroup.setValue("orgid", newOrgid, 11l);
        usercPergroup.setValue("siteid", newSiteid, 11l);
        usercPergroup.setValue("description", desc, 11l);
        usercPergroup.setValue("depnum", newDepnum, 11l);
        usercPergroup.setValue("postnum", newPostnum, 11l);
        usercPergroup.setValue("professionnum", newProfessionnum, 11l);
        usercPergroup.setValue("persongroup", persongroup, 11l);
        usercPergroupSet.save();
        usercPergroupSet.clear();
        usercPergroupSet.close();

    }

    /**
     * <p>
     *
     * @param persongroup
     * @param desc
     * @throws MXException
     * @throws RemoteException
     * @Title: addMaxRole
     * @Description: TODO
     */
    private void addMaxRole() throws RemoteException, MXException {

        MboSetRemote maxRoleSet = getMbo().getMboSet("$MAXROLE", "MAXROLE",
                "MAXROLE = '" + personGroupNum + "'");// MAXROLE
        // String maxRoleNum = getMaxRoleNum();
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
     * @param personid
     * @throws RemoteException
     * @throws MXException
     * @throws SQLException
     */
    private void deletepersonteam(String personid)
            throws RemoteException, MXException, SQLException {

        Connection con = MXServer.getMXServer().getDBManager().getConnection(
                MXServer.getMXServer().getSystemUserInfo().getConnectionKey());
        Statement stm = null;
        try {

            String sql = "DELETE  PERSONGROUPTEAM T WHERE T.RESPPARTYGROUP = '" + personid + "'";
            con.setAutoCommit(false);
            stm = con.createStatement();
            stm.executeUpdate(sql);
            con.commit();
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    /**
     * <p>
     *
     * @throws MXException
     * @throws RemoteException
     * @Title: addPersonGroup
     * @Description: TODO
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
     * @param personid
     * @throws MXException
     * @throws RemoteException
     * @throws SQLException
     * @Title: insPerGroup
     * @Description: TODO
     */
    private void insPerGroup(String personid) throws RemoteException, MXException, SQLException {

        MboSetRemote personGroupTeamSet = getMbo().getMboSet("$PERSONGROUPTEAM", "PERSONGROUPTEAM",
                "persongroup = '" + personGroupNum + "'");

        int resppartygroupseq = (int) personGroupTeamSet.max("resppartygroupseq") + 1;
        int resppartyseq = (int) personGroupTeamSet.max("resppartyseq") + 1;
        personGroupTeamSet.clear();
        personGroupTeamSet.close();

        String sql =
                "insert into PERSONGROUPTEAM (RESPPARTYGROUP, RESPPARTY,		 RESPPARTYGROUPSEQ, RESPPARTYSEQ, USEFORORG, USEFORSITE, GROUPDEFAULT,		 ORGDEFAULT, SITEDEFAULT, PERSONGROUPTEAMID, PERSONGROUP, ROWSTAMP,		 TYPE, JIZU, WORKTYPE, BC, CCXH, GZZ, ISCC, SFDB)values ('" +
                        personid + "', '" + personid + "', " + resppartygroupseq + ", " +
                        resppartyseq + ", null, null, 0, 0, 0, persongroupteamseq.nextval, '" +
                        personGroupNum + "', '139600015', null, null, null, null, null, 0, 0, 0)";
        Connection con = MXServer.getMXServer().getDBManager().getConnection(
                MXServer.getMXServer().getSystemUserInfo().getConnectionKey());
        con.setAutoCommit(false);
        Statement stm = con.createStatement();
        stm.executeUpdate(sql);
        con.commit();
        stm.close();
        con.close();

    }

    /**
     * <p>
     *
     * @param personid
     * @throws MXException
     * @throws RemoteException
     * @Title: insertGoupUser
     * @Description: TODO
     */
    private void insertGoupUser(String personid) throws RemoteException, MXException {

        MboSetRemote groupUserSet = getMbo().getMboSet("$GROUPUSER", "GROUPUSER");
        MboRemote groupUser = groupUserSet.add(2l);
        groupUser.setValue("groupname", groupNum, 11l);
        groupUser.setValue("userid", personid, 11l);
        groupUserSet.save();
        groupUserSet.clear();
        groupUserSet.close();

    }

}