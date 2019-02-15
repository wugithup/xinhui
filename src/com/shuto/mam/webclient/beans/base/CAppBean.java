package com.shuto.mam.webclient.beans.base;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;

/**
 * @author lull lull@shuto.cn
 * @ClassName: CAppBean
 * @Description: appbean基础类  所有新建app都继承这个类
 * @date 2017年5月18日 上午9:12:38
 */
public class CAppBean extends AppBean {
    protected String OWNERTABLE = "";
    protected long OWNERID;
    private String appname;

    @Override
    public int DELETE() throws MXException, RemoteException {
        if (!hasAuth()) {
            throw new MXApplicationException("system", "noauth");
        }
        return super.DELETE();
    }

    public int SAVE() throws MXException, RemoteException {
        if (!hasAuth()) {
            throw new MXApplicationException("system", "noauth");
        }
        return super.SAVE();
    }

    public int ROUTEWF() throws MXException, RemoteException {
        if (!hasAuth()) {
            throw new MXApplicationException("system", "noauth");
        }
        return super.ROUTEWF();
    }

    public boolean hasAuth() throws MXException, RemoteException {
        MboRemote mbo = getMbo();
        if (mbo == null) {
            return true;
        }
        mbo.getThisMboSet().validate();
        String personid = mbo.getUserInfo().getPersonId();
        if (isSysuser()) {
            return true;
        }
        this.OWNERTABLE = getMbo().getName();
        this.OWNERID = getMbo().getUniqueIDValue();

        MboSetRemote wfinstance = mbo.getMboSet("instance", "WFINSTANCE",
                "ownertable='" + this.OWNERTABLE + "' and ownerid='" + this.OWNERID + "' and active = 1");
        boolean noInstance = wfinstance.isEmpty();
        wfinstance.close();
        if (noInstance) {
            String createperson = mbo.getString("createperson");
            return personid.equals(createperson);
        }
        String sql = "ownerid='" + this.OWNERID + "'" + " and ownertable='" + this.OWNERTABLE + "'"
                + " and assignstatus='活动'" + " and assigncode='" + personid + "'";
        MboSetRemote mbosetremote = mbo.getMboSet("$assigncode", "WFASSIGNMENT", sql);
        boolean hasAssigncode = mbosetremote.isEmpty();
        mbosetremote.close();
        return !hasAssigncode;
    }

    public boolean isSysuser() throws MXException, RemoteException {
        MboRemote mbo = getMbo();
        String personid = mbo.getUserInfo().getPersonId();
        MboSetRemote users = mbo.getMboSet("$tmp_maxuser", "maxuser", "sysuser =1 and personid='" + personid + "'");
        boolean isEmpty = (users.getMbo(0) != null) && (users.getMbo(1) == null);
        users.close();
        return isEmpty;
    }

    @Override
    public boolean saveYesNoCheck() throws MXException {
        try {
            if (!hasAuth()) {
                reset();
                this.resetRemote = true;
                return true;
            }
        } catch (RemoteException e) {
            e.printStackTrace();
            return true;
        }
        return super.saveYesNoCheck();
    }

    @Override
    protected void setCurrentRecordData(MboRemote mbo) throws MXException, RemoteException {
        // long startTime = System.currentTimeMillis(); // 获取开始时间

        String status = mbo.getString("status");
        mbo.setFlag(MboConstants.READONLY, false);

        long id = mbo.getUniqueIDValue();
        String tableName = mbo.getName().toUpperCase();
        String personid = mbo.getUserInfo().getPersonId();

        MboSetRemote mbosetremote = mbo.getMboSet("$instance", "WFINSTANCE",
                "ownertable='" + tableName + "' and ownerid='" + id + "' and active = 1");
        if (!mbosetremote.isEmpty()) {
            String sql = "ownerid='" + id + "' and ownertable='" + tableName
                    + "' and assignstatus='活动' and assigncode='" + personid + "'";
            MboSetRemote wfassignmentSet = getMbo().getMboSet("$assigncode", "WFASSIGNMENT", sql);
            if (!wfassignmentSet.isEmpty()) {
                String processname = wfassignmentSet.getMbo(0).getString("processname");
                String processrev = wfassignmentSet.getMbo(0).getString("processrev");
                String nodeid = wfassignmentSet.getMbo(0).getString("nodeid");
                String[] readonlyStr = WfUtil(processname, processrev, nodeid, "READONLY");
                String[] requiredStr = WfUtil(processname, processrev, nodeid, "REQUIRED");
                String[] noreadonlyStr = WfUtil(processname, processrev, nodeid, "NOREADONLY");
                if ((noreadonlyStr != null) && (noreadonlyStr.length > 0)) {
                    mbo.setFieldFlag(noreadonlyStr, MboConstants.READONLY, false);
                }
                if ((readonlyStr != null) && (readonlyStr.length > 0)) {
                    mbo.setFieldFlag(readonlyStr, MboConstants.READONLY, true);
                }
                if ((requiredStr != null) && (requiredStr.length > 0)) {
                    mbo.setFieldFlag(requiredStr, MboConstants.REQUIRED, true);
                }
            } else {
                mbo.setFlag(7L, true);
            }
            wfassignmentSet.close();
        }
        mbosetremote.close();

        if (("已关闭".equals(status)) || ("已作废".equals(status)) || ("已取消".equals(status))) {
            mbo.setFlag(MboConstants.READONLY, true);
        }
        super.setCurrentRecordData(mbo);
        // long endTime = System.currentTimeMillis(); // 获取结束时间
        // System.out.println("程序运行时间： " + (endTime - startTime) + "ms");
    }

    private String[] WfUtil(String processname, String processrev, String nodeid, String str)
            throws RemoteException, MXException {
        MboSetRemote accessControlSet = getMbo().getMboSet("$ACCESSCONTROL", "ACCESSCONTROL",
                "PROCESSNAME = '" + processname + "'AND PROCESSREV = " + processrev + "  AND NODEID = " + nodeid
                        + " and ISBTORZD = '" + str + "'");

        String[] rdStr = new String[0];
        if (!accessControlSet.isEmpty()) {
            rdStr = accessControlSet.getMbo(0).getString("description").split(",");
        }
        accessControlSet.close();
        return rdStr;
    }

    public int OPENREPORT() {
        try {
            MboRemote mbo = this.app.getAppBean().getMbo();
            MboSetRemote reportSet = null;
            if (mbo == null) {
                String mboname = this.app.getAppBean().getMboName();
                MboSetRemote mboSet = MXServer.getMXServer().getMboSet(mboname,
                        MXServer.getMXServer().getSystemUserInfo());
                mbo = mboSet.getMbo(0);
            }
            reportSet = mbo.getMboSet("RQREPORT");
            if (reportSet.getMbo(0) != null && reportSet.getMbo(1) == null) {
                MboRemote reportMbo = reportSet.getMbo(0);
                String dialogID = reportMbo.getString("DIALOGID");
                String rqreportname = reportMbo.getString("RQREPORTNAME");// 报表名
                String rqreportnum = reportMbo.getString("rqreportnum");// 报表编号
                String remark = reportMbo.getString("REMARK"); // 备注
                System.out.println(remark);
                if (!"".equals(dialogID)) {
                    this.clientSession.setAttribute("rqreportname", rqreportname);
                    this.clientSession.setAttribute("rqreportnum", rqreportnum);
                    this.clientSession.setAttribute("remark", remark);
                    this.clientSession.loadDialog(dialogID);
                    return 0;
                }
                StringBuffer url = new StringBuffer(MXServer.getMXServer().getProperty("mxe.runqian.url"));
                url.append("/" + rqreportname.toLowerCase() + ".rpx");
                MboSetRemote rqreportset = mbo.getMboSet("$RQREPORTPARAM", "RQREPORTPARAM",
                        "rqreportnum = '" + rqreportnum + "'");
                if (!rqreportset.isEmpty()) {
                    for (int i = 0; i < rqreportset.count(); i++) {
                        url.append("&");
                        url.append(rqreportset.getMbo(i).getString("PARAMNAME").toLowerCase());
                        url.append("=");
                        url.append(mbo.getString(rqreportset.getMbo(i).getString("PARAMVALUE")));
                    }

                }
                rqreportset.close();
                this.app.openURL(url.toString(), true);
            } else if (reportSet.count() > 1) {
                this.clientSession.loadDialog("RQREPORT1");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MXException e) {
            e.printStackTrace();
        }
        return 1;
    }

    /**
     *   增加代办说明
     ----增加系统属性mxe.maximo.url 值为系统登录地址 http://ip:prot/maximo/ui/
     ----更新系统属性
     update MAXPROPVALUE set propvalue = 'INSERT,SAVE,CLEAR,PREVIOUS,NEXT,NAVHISTORY,STRECLOCK,ASSIGNWF,STATUS,OPENREPORT,
     CHANGE,HANDOVER,SHOWRQ,PREVNOTE,NEXTNOTE' where propname = 'mxe.webclient.showOnToolbar';
     ----增加签名选项
     insert into SIGOPTION(app,Optionname,Description,Esigenabled,Visible,Alsogrants,Alsorevokes,Prerequisite,Sigoptionid,Langcode,Hasld)
     values('WOTRACK','NEXTNOTE','下一条代办',0,1,'SAVE','DUPLICATE','',sigoptionseq.nextval,'ZH',0);
     insert into SIGOPTION(app,Optionname,Description,Esigenabled,Visible,Alsogrants,Alsorevokes,Prerequisite,Sigoptionid,Langcode,Hasld)
     values('WOTRACK','PREVNOTE','上一条代办',0,1,'SAVE','DUPLICATE','',sigoptionseq.nextval,'ZH',0);
     ----增加系统工具栏图标

     select t.*,t.rowid  from MAXMENU t where Keyvalue = 'NEXTNOTE'
     Moduleapp = 'WOTRACK' and menutype= 'APPTOOL' order by position,subposition

     insert into MAXMENU(menutype,Moduleapp,Position,Subposition,Elementtype,Keyvalue,Visible,Image,Tabdisplay,Maxmenuid)
     values('APPTOOL','WOTRACK',300,0,'SEP','AT3',1,'','',maxmenuseq.nextval);
     insert into MAXMENU(menutype,Moduleapp,Position,Subposition,Elementtype,Keyvalue,Visible,Image,Tabdisplay,Maxmenuid)
     values('APPTOOL','WOTRACK',300,1,'OPTION','PREVNOTE',1,'nav_icon_undo.gif','主要',maxmenuseq.nextval);
     insert into MAXMENU(menutype,Moduleapp,Position,Subposition,Elementtype,Keyvalue,Visible,Image,Tabdisplay,Maxmenuid)
     values('APPTOOL','WOTRACK',300,2,'OPTION','NEXTNOTE',1,'nav_icon_down.gif','主要',maxmenuseq.nextval);
     ----增加授权
     insert into applicationauth(groupname,app,optionname,applicationauthid)
     values('MAXADMIN','WOTRACK','PREVNOTE',applicationauthseq.nextval);
     insert into applicationauth(groupname,app,optionname,applicationauthid)
     values('MAXADMIN','WOTRACK','NEXTNOTE',applicationauthseq.nextval);

     */
    /**
     * 上一个代办
     *
     * @return
     * @throws RemoteException
     * @throws MXException
     */
    public int PREVNOTE() throws RemoteException, MXException {
        long ownerid = getMbo().getUniqueIDValue();// 当前单据唯一编号
        String personid = getMbo().getUserInfo().getPersonId();// 当前登录人
        String tableName = getMbo().getName().toUpperCase();
        MboSetRemote wfassignmentSet = getMbo().getMboSet("$wfassignment", "wfassignment",
                "ownertable = '" + tableName + "' and  ownerid = " + ownerid + " and assigncode = '" + personid + "'");
        String sql = "";
        if (!wfassignmentSet.isEmpty()) {
            String startdate = wfassignmentSet.getMbo(0).getString("startdate");
            sql = "assignstatus ='活动' and assigncode = '" + personid + "' and startdate > to_date('" + startdate
                    + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
            sql = "assignstatus ='活动' and assigncode = '" + personid + "' ";
        }
        wfassignmentSet.close();
        wfassignmentSet = getMbo().getMboSet("$wfassignment", "wfassignment", sql);
        wfassignmentSet.setOrderBy("startdate");
        wfassignmentSet.reset();
        if (wfassignmentSet.isEmpty()) {
            wfassignmentSet.close();
            return 0;
        }
        ownerid = wfassignmentSet.getMbo(0).getLong("ownerid");
        String appToGo = wfassignmentSet.getMbo(0).getString("app");
        wfassignmentSet.close();
        String servletBase = MXServer.getMXServer().getProperty("mxe.maximo.url");
        String queryString = servletBase + "?event=loadapp&value=" + appToGo + "&additionalevent=inboxwf&uniqueid="
                + ownerid + "";
        app.setRedirectURL(queryString);
        return 1;
    }

    /**
     * @return 下一个待办
     * @throws RemoteException
     * @throws MXException
     */
    public int NEXTNOTE() throws RemoteException, MXException {
        long ownerid = getMbo().getUniqueIDValue();// 当前单据唯一编号
        String personid = getMbo().getUserInfo().getPersonId();// 当前登录人
        String tableName = getMbo().getName().toUpperCase();
        MboSetRemote wfassignmentSet = getMbo().getMboSet("$wfassignment", "wfassignment",
                "ownertable = '" + tableName + "' and  ownerid = " + ownerid + " and assigncode = '" + personid + "'");
        String sql = "";
        if (!wfassignmentSet.isEmpty()) {
            String startdate = wfassignmentSet.getMbo(0).getString("startdate");
            sql = "assignstatus ='活动' and assigncode = '" + personid + "' and startdate < to_date('" + startdate
                    + "','yyyy-MM-dd HH24:mi:ss')";
        } else {
            sql = "assignstatus ='活动' and assigncode = '" + personid + "' ";
        }
        wfassignmentSet.close();
        wfassignmentSet = getMbo().getMboSet("$wfassignment", "wfassignment", sql);
        wfassignmentSet.setOrderBy("startdate desc");
        wfassignmentSet.reset();
        if (wfassignmentSet.isEmpty()) {
            wfassignmentSet.close();
            return 0;
        }
        ownerid = wfassignmentSet.getMbo(0).getLong("ownerid");
        String appToGo = wfassignmentSet.getMbo(0).getString("app");
        wfassignmentSet.close();
        String servletBase = MXServer.getMXServer().getProperty("mxe.maximo.url");
        String queryString = servletBase + "?event=loadapp&value=" + appToGo + "&additionalevent=inboxwf&uniqueid="
                + ownerid + "";
        app.setRedirectURL(queryString);
        return 1;
    }
}