package com.shuto.mam.webclient.beans.ticket;

import com.ibm.icu.text.DecimalFormat;
import com.ibm.ism.content.mriu.StringUtil;
import com.shuto.mam.app.expand.AutoDateSiteNum;
import com.shuto.mam.app.sr.SRMbo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.beans.servicedesk.TicketAppBean;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CTicketAppBean extends TicketAppBean {
    protected String OWNERTABLE = "";
    protected long OWNERID;

    @Override
    public synchronized void save() throws MXException {
        super.save();
    }

    @Override
    public int DELETE() throws MXException, RemoteException {
        if (!hasAuth()) {
            throw new MXApplicationException("system", "noauth");
        }
        return super.DELETE();
    }

    @Override
    public int SAVE() throws MXException, RemoteException {
        if (!hasAuth()) {
            throw new MXApplicationException("system", "noauth");
        }
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        addTurnMajor(mainMbo);

        return super.SAVE();
    }

    /**
     * 添加转专业次数
     * @param mainMbo:主表的mbo
     * @return
     * @throws RemoteException
     * @throws MXException
     */
    private void addTurnMajor(MboRemote mainMbo) throws RemoteException, MXException {
        //获得缺陷流水号
        String ticketid = mainMbo.getString("TICKETID");
        //转专业
        String s_zzyto = mainMbo.getString("S_ZZYTO");
        //原专业
        String s_yzy = mainMbo.getString("S_YZY");
        //最终专业
        String profession = mainMbo.getString("PROFESSION");
        String s_zzymemo = mainMbo.getString("S_ZZYMEMO");

        if (StringUtil.isEmpty(s_yzy))
        {
            setValue("S_YZY",profession);
        }else{
            if (!StringUtil.isEmpty(s_zzyto))
            {

                //获得转专业对象
                MboSetRemote turnMajorMboSet = mainMbo.getMboSet("TICKETID");
                MboRemote turnMajorMbo =null;
                if (!turnMajorMboSet.isEmpty())
                {

                    turnMajorMboSet.setOrderBy("CREATEDATE DESC");
                    String turnMajorS_zzyto = turnMajorMboSet.getMbo(0).getString("S_ZZYTO");
                    if (!s_zzyto.equals(turnMajorS_zzyto))
                    {
                        //记录转专业次数
                        int cont  = turnMajorMboSet.count()+1;

                        turnMajorMboSet.reset();
                        turnMajorMbo = turnMajorMboSet.add();
                        turnMajorMbo.setValue("S_YZY",turnMajorS_zzyto);
                        setValue("S_YZY",turnMajorS_zzyto);
                        turnMajorMbo.setValue("S_ZZYTO",s_zzyto);
                        turnMajorMbo.setValue("S_ZZZY3",profession);
                        turnMajorMbo.setValue("TICKETID",ticketid);
                        turnMajorMbo.setValue("S_ZZYMEMO",s_zzymemo);
                        turnMajorMbo.setValue("DESCRIPTION","第"+cont+"次转专业");
                        turnMajorMbo.getThisMboSet().save();
                    }
                }else{
                    turnMajorMboSet.reset();
                    turnMajorMbo = turnMajorMboSet.add();
                    turnMajorMbo.setValue("S_ZZYTO",s_zzyto);
                    turnMajorMbo.setValue("S_YZY",s_yzy);
                    turnMajorMbo.setValue("S_ZZZY3",profession);
                    turnMajorMbo.setValue("TICKETID",ticketid);
                    turnMajorMbo.setValue("S_ZZYMEMO",s_zzymemo);
                    turnMajorMbo.setValue("DESCRIPTION","第1次转专业");
                    turnMajorMbo.getThisMboSet().save();
                }




            }


        }

    }

    @Override
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

    private boolean isSysuser() throws MXException, RemoteException {
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

        String[] rdStr = null;
        if (!accessControlSet.isEmpty()) {
            rdStr = accessControlSet.getMbo(0).getString("description").split(",");
        }
        accessControlSet.close();
        return rdStr;
    }

    @Override
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
                if (!"".equals(dialogID)) {
                    this.clientSession.setAttribute("rqreportname", rqreportname);
                    this.clientSession.setAttribute("rqreportnum", rqreportnum);
                    this.clientSession.loadDialog(dialogID);
                    return 0;
                }
                StringBuilder url = new StringBuilder(MXServer.getMXServer().getProperty("mxe.runqian.url"));
                url.append("/").append(rqreportname.toLowerCase()).append(".rpx");
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

    private void scbh() throws MXException, RemoteException {
        MboRemote localMboRemote = this.app.getAppBean().getMbo();
        MboSetRemote localMboSetRemote = localMboRemote.getThisMboSet();
        String str1 = localMboRemote.getString("orgid");
        String str2 = localMboRemote.getString("siteid");
        String str3 = "QX";
        String str4 = localMboRemote.getString("S_QXLB");
        String str5 = localMboRemote.getString("T_TICKETID");
        String str6 = localMboRemote.getThisMboSet().getApp();
        String str7 = new SimpleDateFormat("yyMM").format(new Date());
        if ((!str4.isEmpty()) && (str5.isEmpty())) {
            AutoDateSiteNum localAutoDateSiteNum = new AutoDateSiteNum(localMboSetRemote);
            int i = localAutoDateSiteNum.getNextAutoDateSiteNum(str1, str2, str6, null, str3);
            localMboRemote.setValue("T_TICKETID", str3 + str7 + new DecimalFormat("0000").format(i), 11L);
        }
    }
}