package com.shuto.mam.webclient.beans.workorder;

import com.shuto.mam.webclient.beans.base.CAppBean;
import psdi.app.workorder.WoSafetyLinkRemote;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;
import psdi.webclient.system.runtime.WebClientRuntime;
import psdi.webclient.system.session.WebClientSession;
import psdi.workflow.WorkFlowServiceRemote;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 扩展工作票功能，增加创建许可证的功能
 *
 * @author zhangjq
 */

/**
 * com.shuto.mam.webclient.beans.workorder.ExtWoTicketAppBean
 *
 * @author shanbh 2016-3-30 隔离冲突检测 生成标准工作票 安错自动保存到隔离点库
 */
public class ExtWoTicketAppBean extends CAppBean {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private PreparedStatement spstatement = null;
    private ResultSet srs = null;

    private PreparedStatement pszx;
    private ResultSet rszx;

    private Map<String, String> relationMap = new HashMap<String, String>();

    public ExtWoTicketAppBean() {
        super();
        relationMap.put("aqcsline", "安全措施");
        relationMap.put("wxyline", "危险源");
        relationMap.put("zbline", "准备工作");
    }

    public void ALLZX() throws RemoteException, MXException, SQLException {

        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String wonum = mainMbo.getString("wonum");
        MboSetRemote WOSAFETYLINKZXMSR = getMbo().getMboSet("$WOSAFETYLINKZX", "WOSAFETYLINK",
                "wonum = '" + wonum + "' and  TAGOUTID is  not null");
        if (WOSAFETYLINKZXMSR.count() == 0) {
            throw new MXApplicationException("workorder", "aqcs");
        }
        WOSAFETYLINKZXMSR.close();
        this.conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
        String sql = "update  WOSAFETYLINK set  s_iszx='1'  where wonum =  '" + wonum
                + "'   and  TAGOUTID is  not null";
        System.out.println(sql);
        this.pszx = this.conn.prepareStatement(sql);
        this.rszx = this.pszx.executeQuery();

        if (pszx != null) {
            pszx.close();
        }
        if (rszx != null) {
            rszx.close();
        }
        if (conn != null) {
            conn.close();
        }
        SAVE();
        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "waring", "该工作票中所有措施已全部执行完毕！", 1);
        return;
    }

    public void ALLHF() throws RemoteException, MXException, SQLException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String wonum = mainMbo.getString("wonum");
        MboSetRemote WOSAFETYLINKZXMSR = getMbo().getMboSet("$WOSAFETYLINKHF", "WOSAFETYLINK",
                "wonum = '" + wonum + "' and  TAGOUTID is  not null");
        if (WOSAFETYLINKZXMSR.count() == 0) {
            throw new MXApplicationException("workorder", "aqcs");
        }
        WOSAFETYLINKZXMSR.close();
        this.conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
        String sql = "update  WOSAFETYLINK set  s_ishf='1'  where wonum =  '" + wonum
                + "'   and  TAGOUTID is  not null";
        System.out.println(sql);
        this.pszx = this.conn.prepareStatement(sql);
        this.rszx = this.pszx.executeQuery();
        if (pszx != null) {
            pszx.close();
        }
        if (rszx != null) {
            rszx.close();
        }
        if (conn != null) {
            conn.close();
        }
        SAVE();
        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "waring", "该工作票中所有措施已全部恢复完毕！", 1);
        return;
    }

    /**
     * 菏泽工作票中创建检修记录
     *
     * @throws RemoteException
     * @throws MXException
     */
    public void CREJXJL() throws RemoteException, MXException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String WONUM = mainMbo.getString("WONUM");
        String STATUS = mainMbo.getString("STATUS");
        MboSetRemote woparset = mainMbo.getMboSet("$sonworkorder", "workorder",
                "worktype='检修记录' and parent='" + WONUM + "'");
        String S_WOTKNUM = mainMbo.getString("S_WOTKNUM");

        if (!"已关闭".equals(STATUS) && !"已作废".equals(STATUS) && !S_WOTKNUM.isEmpty()) {
            if (woparset.count() > 0) {// 如果已经创建过检修记录
                WebClientEvent wce = clientSession.getCurrentEvent();
                int msgRet = wce.getMessageReturn();
                if (msgRet < 0) {
                    // 弹出提示窗口问是否继续
                    throw new MXApplicationYesNoCancelException("提示是否继续", "workorder", "sfcjjxjl");
                }
                if (msgRet == 2) {
                    crejxjlimp();
                }

            } else {
                crejxjlimp();
            }
        } else {
            Utility.showMessageBox(this.clientSession.getCurrentEvent(), "waring",
                    "该状态不允许生成检修记录 ,只有在许可后(且未关闭、未作废)才可生成检修记录", 1);
            return;
        }
        woparset.close();
    }

    private void crejxjlimp() throws RemoteException, MXException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String WONUM = mainMbo.getString("WONUM");
        MboSetRemote msr = mainMbo.getMboSet("jxjl");
        // 就生成新的检修记录
        MboRemote bzwo = msr.add();
        bzwo.setValue("worktype", "检修记录", 11L);
        bzwo.setValue("parent", WONUM, 11L);
        bzwo.setValue("DESCRIPTION", mainMbo.getString("DESCRIPTION"), 11L);
        bzwo.setValue("LOCATION", mainMbo.getString("LOCATION"), 11L);
        bzwo.setValue("S_ORDERTYPE", mainMbo.getString("S_ORDERTYPE"), 11L);
        bzwo.setValue("S_PROFESSION", mainMbo.getString("S_PROFESSION"), 11L);
        bzwo.setValue("TEAMNUM", mainMbo.getString("TEAMNAME"), 11L);
        bzwo.setValue("LEAD", mainMbo.getString("LEAD"), 11L);

        msr.save();
        WebClientEvent event = this.sessionContext.getCurrentEvent();
        WebClientSession wcs = event.getWebClientSession();
        String additionalEvent = event.getAdditionalEvent();
        String additionalEventValue = event.getAdditionalEventValue();

        String url = "?event=gotoapp&value=JXJL";
        if (!(WebClientRuntime.isNull(additionalEvent))) {
            url = url + "&additionalevent=" + additionalEvent;
            if (!(WebClientRuntime.isNull(additionalEventValue))) {
                url = url + "&additionaleventvalue=" + additionalEventValue;
            }
        }
        url = url + "&uniqueid=" + bzwo.getUniqueIDValue();

        wcs.getCurrentApp().put("forcereload", "true");
        wcs.gotoApplink(url);
        event.cancelRender();

        // DataBean jxjldatabean = this.app.getDataBean("1426736016644");
        // jxjldatabean.reloadTable();
        // jxjldatabean.refreshTable();

    }

    /**
     * 保护投退跳转
     *
     * @throws RemoteException
     * @throws MXException
     */
    public void BHTT() throws RemoteException, MXException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();

        MboSetRemote OPAMDOPMsr = mainMbo.getMboSet("OPAMDOP");
        MboRemote experimentMbo = OPAMDOPMsr.add();

        experimentMbo.setValue("WONUM", mainMbo.getString("wonum"), 11L);
        experimentMbo.setValue("BHTTNAME", mainMbo.getString("DESCRIPTION"), 11L);

        OPAMDOPMsr.save();
        WebClientEvent event = this.sessionContext.getCurrentEvent();
        WebClientSession wcs = event.getWebClientSession();
        String additionalEvent = event.getAdditionalEvent();
        String additionalEventValue = event.getAdditionalEventValue();

        String url = "?event=gotoapp&value=H_BHTT";
        if (!(WebClientRuntime.isNull(additionalEvent))) {
            url = url + "&additionalevent=" + additionalEvent;
            if (!(WebClientRuntime.isNull(additionalEventValue))) {
                url = url + "&additionaleventvalue=" + additionalEventValue;
            }
        }
        url = url + "&uniqueid=" + experimentMbo.getUniqueIDValue();

        wcs.getCurrentApp().put("forcereload", "true");
        wcs.gotoApplink(url);
        event.cancelRender();
    }

    /**
     * @throws RemoteException
     * @throws MXException
     */

    public int ROUTEWF() throws MXException, RemoteException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String wonum = mainMbo.getString("wonum");
        String status = mainMbo.getString("status");
        String siteid = mainMbo.getString("siteid");
        Date date = new Date();
        String S_XKKSDATE = mainMbo.getString("S_XKKSDATE");// 许可工作开始时间
        String S_PZGZJSDATE = mainMbo.getString("S_PZGZJSDATE");// 批准工作结束时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (status.equals("待许可开工")) {
            try {
                Date date1 = df.parse(S_XKKSDATE);// 许可工作开始时间
                Date date2 = df.parse(S_PZGZJSDATE);// 批准工作结束时间
                if (date1.getTime() > date2.getTime()) {// date.getTime() >
                    // date1.getTime()||
                    // 要大于当前时间且
                    throw new MXApplicationException("提示", "许可工作开始时间要小于批准工作结束时间！");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        MboSetRemote HSEWOWHSET = mainMbo.getMboSet("$HSEWOWH", "HSEWOWH", " siteid='" + siteid + "'");
        // 许可人许可之前判断
        if (status.equals("待许可开工") && !HSEWOWHSET.isEmpty()) {//
            String maxvalue = null;
            ArrayList<String> al = new ArrayList<String>();

            // 得到本工作票中的所有隔离点结果集
            MboSetRemote wotagoutset = mainMbo.getMboSet("$wosafetylink", "wosafetylink",
                    " wonum='" + wonum + "'  and  REQUIREDSTATE2 is not null");
            if (!wotagoutset.isEmpty()) {
                // 遍历本工作票中的所有隔离点 并找出冲突的
                for (int i = 0; i < wotagoutset.count(); i++) {
                    MboRemote wotagoutmbo = wotagoutset.getMbo(i);
                    String TAGOUTID = wotagoutmbo.getString("TAGOUTID");
                    // 查询其他工作票中与本工作票级联的隔离点内部值
                    String selectsql = " select  maxvalue from  ALNDOMAIN where DOMAINID='DEVICESTATUS' and value in ("
                            + "select REQUIREDSTATE2 from  wosafetylink  where    REQUIREDSTATE2 is not null and"
                            + " wonum in (select wonum from  hse_tagout)" + "and  TAGOUTID ='" + TAGOUTID + "'  )";
                    try {
                        System.out.println(selectsql);

                        this.conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
                        // 查询本工作票中隔离点的内部值
                        String sql = "select tagoutid,maxvalue  from wosafetylink, ALNDOMAIN where wonum = '" + wonum
                                + "' and TAGOUTID='" + TAGOUTID + "'"
                                + " and DOMAINID = 'DEVICESTATUS' and wotagout.requiredstate = ALNDOMAIN.Value ";
                        this.ps = this.conn.prepareStatement(sql);
                        System.out.println("1.1--查询本工作票中隔离点的内部值:  " + sql);
                        this.rs = this.ps.executeQuery();
                        if (this.rs.next()) {
                            maxvalue = this.rs.getString("maxvalue");
                            System.out.println("1.2==本工作票中的隔离点及内部值：" + TAGOUTID + " ======= " + maxvalue);

                            spstatement = conn.prepareStatement(selectsql);
                            System.out.println("2.1--查询级联工作票中隔离点的内部值:  " + selectsql);
                            srs = spstatement.executeQuery();
                            if (this.srs.next()) {
                                String qtmaxvalue = this.srs.getString("maxvalue");
                                System.out.println("2.2==其他工作票中的内部值" + TAGOUTID + "   " + qtmaxvalue);
                                // 如果隔离冲突加入到集合之后
                                if (!maxvalue.equals(qtmaxvalue)) {
                                    System.out.println("3 >>>>此隔离点隔离冲突： " + TAGOUTID + "；   本工作票票里面的内部值是 " + maxvalue
                                            + "；   级联工作票票里面的是 " + qtmaxvalue);
                                    al.add(TAGOUTID);
                                    continue;// 隔离冲突加入集合中 结束本次循环
                                }
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (rs != null) {
                                rs.close();
                            }
                            if (ps != null) {
                                ps.close();
                            }
                            if (srs != null) {
                                srs.close();
                            }
                            if (spstatement != null) {
                            }
                            if (conn != null) {
                                conn.close();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                if (!al.isEmpty()) {
                    // 提示所有本工作票隔离点与级联工作票隔离点中隔离冲突的隔离点
                    Utility.showMessageBox(this.clientSession.getCurrentEvent(), "waring",
                            "该工作票中隔离点'" + al + "'与其他工作票隔离冲突！系统不允许通过！", 1);
                    return 1;
                } else {
                    return super.ROUTEWF();
                }

            }
            HSEWOWHSET.close();
            wotagoutset.close();
        }
        return super.ROUTEWF();
    }

    // duplicate
    @Override
    public int DUPLICATE() throws MXException, RemoteException {
        super.DUPLICATE();
        MboRemote bzwo = this.getMbo();
        bzwo.setValue("parent", "", 11L);
        bzwo.setValue("SCHEDFINISH", "", 11L);
        bzwo.setValue("SCHEDSTART", "", 11L);
        bzwo.setValue("BOXCODE", "", 11L);// 清空钥匙箱信息
        bzwo.setValue("AQSID", "", 11L);// 清空安全锁信息
        bzwo.setValue("KZSID", "", 11L);// 清空控制锁信息
        bzwo.setValue("BZWONUM", "", 11L);
        bzwo.setValue("LEAD", "", 11L);// 清空负责人信息
        bzwo.setValue("S_WORKCY", "", 11L);// 清空工作班成员信息
        bzwo.setValue("S_WORKCYQTY", "", 11L);// 清空成员数量信息
        bzwo.setValue("S_GZJHPERSON", "", 11L);// 工作监护人

        bzwo.setValue("PHONE", "", 11L);// 负责人电话
        bzwo.setValue("S_GZJHPERSON", "", 11L);// 专职监护人
        bzwo.setValue("S_JIZU", "", 11L);// 运行集控
        bzwo.setValue("S_PROFESSION", "", 11L);// 专业
        bzwo.setValue("TEAMNAME", "", 11L);// 作业部门
        bzwo.setValue("S_REPWONUM", "", 11L);// 关联工作票
        bzwo.setValue("S_DEPARTMENT", "", 11L);// 动火执行单位
        bzwo.setValue("DHFS", "", 11L);// 动火方式
        bzwo.setValue("SFGLDH", "", 11L);// 是否关联动火票

        // 附件信息 15
        bzwo.setValue("ISYP", "0", 11L);
        bzwo.setValue("ISAJHJSJD", "0", 11L);
        bzwo.setValue("SFGLDH", "0", 11L);
        bzwo.setValue("JXCXWJ", "0", 11L);
        bzwo.setValue("XKZSZY", "0", 11L);
        bzwo.setValue("XKZGCZY", "0", 11L);
        bzwo.setValue("XKZQDZY", "0", 11L);
        bzwo.setValue("XKZWKZY", "0", 11L);
        bzwo.setValue("ISSY", "0", 11L);
        bzwo.setValue("ISJXX", "0", 11L);
        bzwo.setValue("ISXFS", "0", 11L);
        bzwo.setValue("ISYSKQ", "0", 11L);
        bzwo.setValue("ISLSYD", "0", 11L);
        bzwo.setValue("ISCZPSQ", "0", 11L);
        bzwo.setValue("ISBHTT", "0", 11L);
        // 审批信息 12
        bzwo.setValue("S_WWQFR", "", 11L);
        bzwo.setValue("S_QFPERSON", "", 11L);
        bzwo.setValue("S_ZBFZPERSON", "", 11L);
        bzwo.setValue("S_ZZPERSON", "", 11L);
        bzwo.setValue("S_XKPERSON", "", 11L);
        bzwo.setValue("S_XKKSDATE", "", 11L);
        bzwo.setValue("S_WWQFDATA", "", 11L);
        bzwo.setValue("S_QFDATE", "", 11L);
        bzwo.setValue("S_ZBJPDATE", "", 11L);
        bzwo.setValue("S_ZZPZDATE", "", 11L);
        bzwo.setValue("S_XKDATE", "", 11L);
        bzwo.setValue("S_PZGZJSDATE", "", 11L);
        // 终结信息 5
        bzwo.setValue("S_ZJDATE", "", 11L);
        bzwo.setValue("S_ZJFZPERSON", "", 11L);
        bzwo.setValue("S_ZJFZQRDATE", "", 11L);
        bzwo.setValue("S_ZJXKPERSON", "", 11L);
        bzwo.setValue("S_ZJXKQRDATE", "", 11L);
        // 作废信息 5
        bzwo.setValue("S_ZFSQR", "", 11L);
        bzwo.setValue("ZFYY", "", 11L);
        bzwo.setValue("S_SQZFDATE", "", 11L);
        bzwo.setValue("S_ZFPERSON", "", 11L);
        bzwo.setValue("S_ZFDATE", "", 11L);
        // 负责人变更信息 9
        bzwo.setValue("S_YFZPERSON", "", 11L);
        bzwo.setValue("S_XFZPERSON", "", 11L);
        bzwo.setValue("S_BGZFPERSON", "", 11L);
        bzwo.setValue("S_BGFZDATE", "", 11L);
        bzwo.setValue("S_BGFZDATE", "", 11L);
        bzwo.setValue("S_BGQFPERSON", "", 11L);
        bzwo.setValue("S_BGQFDATE", "", 11L);
        bzwo.setValue("S_BGXKPERSON", "", 11L);
        bzwo.setValue("S_BGXKDATE", "", 11L);
        // 延期信息 7
        bzwo.setValue("S_YQTODATE", "", 11L);
        bzwo.setValue("OPLOG_DELAYCAUSE", "", 11L);
        bzwo.setValue("S_YQFZPERSON", "", 11L);
        bzwo.setValue("C_YQXKR", "", 11L);
        bzwo.setValue("S_YQXKDATE", "", 11L);
        bzwo.setValue("S_YQZZPERSON", "", 11L);
        bzwo.setValue("S_YQZZDATE", "", 11L);
        // 动火签字信息 14
        bzwo.setValue("S_XFMARK", "", 11L);
        bzwo.setValue("S_XFPERSON", "", 11L);
        bzwo.setValue("S_XFQZDATE", "", 11L);
        bzwo.setValue("S_SQBMZGMARK", "", 11L);
        bzwo.setValue("S_SQBMZGPERSON", "", 11L);
        bzwo.setValue("S_SQBMZGDATE", "", 11L);
        bzwo.setValue("S_AJBMMARK", "", 11L);
        bzwo.setValue("S_AQJCFZPERSON", "", 11L);
        bzwo.setValue("S_ABQZDATE", "", 11L);
        bzwo.setValue("S_BMZGMARK", "", 11L);
        bzwo.setValue("S_BMZGPERSON", "", 11L);
        bzwo.setValue("S_BMZGDATE", "", 11L);
        bzwo.setValue("S_GSLEADMARK", "", 11L);
        bzwo.setValue("S_GSLEADPERSON", "", 11L);

        this.app.getAppBean().refreshTable();
        this.app.getAppBean().reloadTable();
        return 1;
    }

    /**
     * 生成标准工作票
     *
     * @throws RemoteException
     * @throws MXException
     */
    public void CREBZWO() throws RemoteException, MXException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String status = mainMbo.getString("status");
        String BZGZP = mainMbo.getString("BZWONUM");
        String location = mainMbo.getString("location");
        String wonum = mainMbo.getString("wonum");

        String S_WOTKNUM = mainMbo.getString("S_WOTKNUM");
        System.out.println("状态+++++======" + status);
        System.out.println("编号+++++======" + S_WOTKNUM);

        if ("已作废".equals(status) || S_WOTKNUM.isEmpty()) {
            Utility.showMessageBox(this.clientSession.getCurrentEvent(), "waring",
                    "该状态不允许生成标准工作票 ,只有在许可后且未作废才可生成标准工作票", 1);
            return;
        }
        MboSetRemote bzwoset = mainMbo.getMboSet("$sonworkorder", "workorder",
                "worktype='标准工作票' and woeq1='" + wonum + "'");
        if (bzwoset.count() > 0 && BZGZP.isEmpty()) {
            Utility.showMessageBox(this.clientSession.getCurrentEvent(), "waring", "该工作票已经创建过标准工作票，不可以再次创建！", 1);
            return;
        }
        MboSetRemote wolocset = mainMbo.getMboSet("$sonworkorder", "workorder",
                "worktype='标准工作票' and location='" + location + "'");
        // 判断该位置下是否成成过标准工作票
        if (wolocset.count() > 0) {
            WebClientEvent wce = clientSession.getCurrentEvent();
            int msgRet = wce.getMessageReturn();
            if (msgRet < 0) {
                // 弹出提示窗口提示是否继续
                throw new MXApplicationYesNoCancelException("提示是否继续", "workorder", "sfcjbzgd");
            }
            if (msgRet == 2) {
                MboSetRemote msr = mainMbo.getMboSet("WORKSELF");
                if ("".equals(BZGZP)) {// 如果标准工作票引入字段为空
                    // 就生成新的工作票
                    MboRemote bzwo = msr.getMbo(0).duplicate();
                    crebzwoimp(bzwo);
                    insertOtherTagout(bzwo);

                }
                // 如果引入标准工作票字段有值 就直接修改对应的标准工作票
                // 首先删除当前标准工作票 然后复制开工工作票 并把原先的标准票号还赋值给新生成的标准工作票
                else if (!"".equals(BZGZP)) {
                    MboSetRemote relatedrecordset = mainMbo.getMboSet("$relatedrecord111", "relatedrecord",
                            "orgid='" + mainMbo.getString("orgid")
                                    + "' and class='工单' and RELATETYPE='相关'  and Recordkey='" + BZGZP + "'");

                    if (relatedrecordset.count() > 0) {
                        String Relatedreckey = relatedrecordset.getMbo(0).getString("Relatedreckey");
                        relatedrecordset.deleteAndRemove();
                        relatedrecordset.deleteAll();
                        relatedrecordset.save();
                        MboSetRemote relatedreckeyset = mainMbo.getMboSet("$relatedrecord222", "relatedrecord",
                                "orgid='" + mainMbo.getString("orgid")
                                        + "' and class='工单'  and RELATETYPE='相关'   and Relatedreckey='" + BZGZP + "'");
                        relatedreckeyset.deleteAndRemove();
                        relatedreckeyset.close();
                        MboSetRemote wosonset = mainMbo.getMboSet("$sonworkorder", "workorder",
                                "wonum='" + BZGZP + "'");
                        wosonset.deleteAll();
                        wosonset.save();
                        wosonset.close();
                        MboRemote bzwo = msr.getMbo(0).duplicate();
                        bzwo.setValue("wonum", BZGZP, 11L);//
                        // 把原先的标准票号还赋值给新生成的标准工作票
                        crebzwoimp(bzwo);
                        insertOtherTagout(bzwo);

                        MboSetRemote rrelatedrecordset = mainMbo.getMboSet("$relatedrecord333", "relatedrecord");
                        MboRemote prowo = rrelatedrecordset.add();
                        prowo.setValue("Recordkey", Relatedreckey, 11L);
                        prowo.setValue("Relatedreckey", BZGZP, 11L);
                        prowo.setValue("class", "工单", 11L);
                        prowo.setValue("RELATEDRECCLASS", "工单", 11L);
                        prowo.setValue("RELATETYPE", "相关", 11L);
                        prowo.setValue("RELATEDRECORGID", mainMbo.getString("ORGID"), 11L);
                        prowo.setValue("RELATEDRECSITEID", mainMbo.getString("SITEID"), 11L);
                        prowo.setValue("ORGID", mainMbo.getString("ORGID"), 11L);
                        prowo.setValue("SITEID", mainMbo.getString("SITEID"), 11L);
                        rrelatedrecordset.save();
                        relatedrecordset.close();

                    } else {
                        MboSetRemote wosonset = mainMbo.getMboSet("$sonworkorder", "workorder",
                                "wonum='" + BZGZP + "'");
                        wosonset.deleteAll();
                        wosonset.save();
                        wosonset.close();
                        MboRemote bzwo = msr.getMbo(0).duplicate();
                        bzwo.setValue("wonum", BZGZP, 11L);// 把原先的标准票号还赋值给新生成的标准工作票
                        crebzwoimp(bzwo);
                        insertOtherTagout(bzwo);

                    }
                    relatedrecordset.close();
                }
                msr.save();
                Utility.showMessageBox(this.clientSession.getCurrentEvent(), "Success", "标准工作票创建成功. ", 1);
            }
        } else {// 该位置没有创建过标准票
            MboSetRemote msr = mainMbo.getMboSet("WORKSELF");
            if ("".equals(BZGZP)) {// 如果标准工作票引入字段为空
                // 就生成新的工作票
                MboRemote bzwo = msr.getMbo(0).duplicate();
                crebzwoimp(bzwo);
                insertOtherTagout(bzwo);

            }
            // 如果引入标准工作票字段有值 就直接修改对应的标准工作票
            // 首先删除当前标准工作票 然后复制开工工作票 并把原先的标准票号还赋值给新生成的标准工作票
            else if (!"".equals(BZGZP)) {
                MboSetRemote wosonset = mainMbo.getMboSet("$sonworkorder", "workorder", "wonum='" + BZGZP + "'");
                wosonset.deleteAll();// 将当前mboset中所有的记录标记为删除
                wosonset.save();
                wosonset.close();
                MboRemote bzwo = msr.getMbo(0).duplicate();
                bzwo.setValue("wonum", BZGZP, 11L);
                crebzwoimp(bzwo);
                insertOtherTagout(bzwo);

            }
            bzwoset.close();
            wolocset.close();
            msr.save();
            Utility.showMessageBox(this.clientSession.getCurrentEvent(), "Success", "标准工作票创建成功. ", 1);
        }

    }

    /**
     * 生成标准工作票的时候 需要改变的信息 并刷新该工作票已经创建过的标准工作票列表
     *
     * @throws RemoteException
     * @throws MXException
     */
    public void crebzwoimp(MboRemote bzwo) throws RemoteException, MXException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String WONUM = mainMbo.getString("WONUM");
        bzwo.setValue("worktype", "标准工作票", 11L);
        bzwo.setValue("parent", "", 11L);
        bzwo.setValue("SCHEDFINISH", "", 11L);
        bzwo.setValue("SCHEDSTART", "", 11L);
        bzwo.setValue("BOXCODE", "", 11L);// 清空钥匙箱信息
        bzwo.setValue("AQSID", "", 11L);// 清空安全锁信息
        bzwo.setValue("KZSID", "", 11L);// 清空安全锁信息
        bzwo.setValue("BZWONUM", "", 11L);
        bzwo.setValue("WOEQ1", WONUM, 11L);
        bzwo.setValue("LEAD", "", 11L);// 清空负责人信息
        bzwo.setValue("S_WORKCY", "", 11L);// 清空工作班成员信息
        bzwo.setValue("S_WORKCYQTY", "", 11L);// 清空成员数量信息
        bzwo.setValue("S_GZJHPERSON", "", 11L);//

        MboSetRemote LICENCEMAIN = mainMbo.getMboSet("LICENCEMAIN5");// 工作票mbo
        if (!LICENCEMAIN.isEmpty()) {
            MboSetRemote supMsr = bzwo.getMboSet("LICENCEMAIN5");
            for (int i = 0; i < supMsr.count(); i++) {
                supMsr.getMbo(i).delete();
            }
            for (int i = 0; i < LICENCEMAIN.count(); i++) {
                MboRemote suppleMbo = supMsr.add();
                MboRemote suppleMainMbo = LICENCEMAIN.getMbo(i);
                // suppleMbo.setValue("STARTDATE",
                // suppleMainMbo.getString("STARTDATE"));
                // suppleMbo.setValue("ALN1", suppleMainMbo.getString("ALN1"));
                // suppleMbo.setValue("ZYDW", suppleMainMbo.getString("ZYDW"));

                suppleMbo.setValue("WONUM", bzwo.getString("WONUM"));
                suppleMbo.setValue("type", "1005");

            }

        }

        MboSetRemote LICENCEMAIN11 = mainMbo.getMboSet("LICENCEMAIN5");
        if (!LICENCEMAIN11.isEmpty()) {
            MboSetRemote WXYLINE = LICENCEMAIN11.getMbo(0).getMboSet("WXYLINE");

            if (!WXYLINE.isEmpty()) {
                MboSetRemote supMsr1 = bzwo.getMboSet("LICENCEMAIN5");

                MboSetRemote supMsr = supMsr1.getMbo(0).getMboSet("WXYLINE");
                for (int i = 0; i < supMsr.count(); i++) {
                    supMsr.getMbo(i).delete();
                }
                for (int i = 0; i < WXYLINE.count(); i++) {
                    MboRemote suppleMbo = supMsr.add();
                    MboRemote suppleMainMbo = WXYLINE.getMbo(i);
                    suppleMbo.setValue("parent", supMsr1.getMbo(0).getString("licencemainid"));
                    suppleMbo.setValue("DESCRIPTION", suppleMainMbo.getString("DESCRIPTION"));
                    suppleMbo.setValue("SN", suppleMainMbo.getString("SN"));
                    suppleMbo.setValue("type", "危险源");

                }

            }
        }

        // this.app.getDataBean("1429945735745").reloadTable();//
        // 该工作票已经创建过的标准工作票列表
        super.SAVE();

    }

    public void insertOtherTagout(MboRemote bzMbo) throws RemoteException, MXException {
        MXServer mxServer = MXServer.getMXServer();
        String url = mxServer.getProperty("mxe.db.url");
        String username = mxServer.getProperty("mxe.db.user");
        String password = mxServer.getProperty("mxe.db.password");

        Connection conn = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String sql = "update wosafetylink "
                    + " set  S_ISZX   = 0,   S_ISHF   = 0,  kgid     = '',   DXID     = '',  BSPID    = '',   ZHIXINGPERSON = '',  CCPERSON = '' "
                    + " where wonum='" + bzMbo.getString("wonum") + "' and tagoutid is not null ";
            stmt.executeUpdate(sql);
            System.out.println(sql);
            conn.commit();

        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
                throw new MXApplicationException("", e1.getMessage());
            }
            e.printStackTrace();
            throw new MXApplicationException("", e.getMessage());
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public int SAVE() throws MXException, RemoteException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String siteid = mainMbo.getString("siteid");
        String S_ORDERTYPE = mainMbo.getString("S_ORDERTYPE");
        String Status = mainMbo.getString("STATUS");
        String S_XKKSDATE = mainMbo.getString("S_XKKSDATE");// 许可工作开始时间
        String S_PZGZJSDATE = mainMbo.getString("S_PZGZJSDATE");// 批准工作结束时间
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        // 不得超过24小时
        if ("待许可开工".equals(Status) && S_ORDERTYPE.equals("一级动火工作票") && !S_XKKSDATE.isEmpty()
                && !S_PZGZJSDATE.isEmpty()) {
            try {
                Date start = df.parse(S_XKKSDATE);
                Date end = df.parse(S_PZGZJSDATE);// 批准工作结束时间
                long cha = end.getTime() - start.getTime();
                double result = cha * 1.0 / (1000 * 60 * 60);
                if (result > 24) {
                    throw new MXApplicationException("提示", "许可工作开始时间到批准工作结束时间不能超过24小时！");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if ("待许可开工".equals(Status) && S_ORDERTYPE.equals("二级动火工作票") && !S_XKKSDATE.isEmpty()
                && !S_PZGZJSDATE.isEmpty()) {
            try {
                Date start = df.parse(S_XKKSDATE);
                Date end = df.parse(S_PZGZJSDATE);// 批准工作结束时间
                long cha = end.getTime() - start.getTime();
                double result = cha * 1.0 / (1000 * 60 * 60 * 24);
                if (result > 5) {
                    throw new MXApplicationException("提示", "许可工作开始时间到批准工作结束时间不能超过5天！");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }

        if ("待许可开工".equals(Status) && (!S_ORDERTYPE.equals("一级动火工作票") && !S_ORDERTYPE.equals("二级动火工作票"))) {
            String wonum1 = mainMbo.getString("WONUM");
            String lead = mainMbo.getString("LEAD");
            String where = "LEAD ='" + lead + "' and wonum <> '" + wonum1 + "' and "
                    + "status not in('等待批准','退回修改','待外委单位签发','待签发人签发','待值班负责人接票',"
                    + "'待值长审批','待许可开工','已关闭','已押票' ,'取消','已作废','已取消') and S_ORDERTYPE not in('一级动火工作票','二级动火工作票')  ";
            MboSetRemote sjset = mainMbo.getMboSet("$WORKORDER1122", "WORKORDER", where);
            if (sjset.count() > 0) {
                String wonum = sjset.getMbo(0).getString("WONUM");
                if (wonum != null) {
                    throw new MXApplicationException("提示", "该负责人已负责编号为：" + wonum + "的工作票,请终结后在许可！ ");
                }
            }
            sjset.close();
        }

        if (Status.equals("等待批准") && siteid.equals("FYHR000")// 安健环技术交底检查卡
                && (!S_ORDERTYPE.equals("一级动火工作票") || !S_ORDERTYPE.equals("二级动火工作票"))) {
            mainMbo.setValue("ISAJHJSJD", "1", 11L);
        }
        if (Status.equals("等待批准") && siteid.equals("FYHR000")) {// 阜阳工作票验收单必打
            mainMbo.setValue("ISYSD", "1", 11L);
        }
        saveTagout();

        return super.SAVE();

    }

    private void saveTagout() throws MXException, RemoteException {
        MboSetRemote tagoutSet = null;
        // DataBean databean = this.app.getDataBean("WOSAFETYLINK001");//
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        MboSetRemote databean = mainMbo.getMboSet("WOSATAGOUT");
        if (databean == null) {
            return;
        }
        // System.out.println("保存隔离点WOSATAGOUT： " + databean.count());
        for (int i = 0; i < databean.count(); i++) {
            WoSafetyLinkRemote wosafetylink = (WoSafetyLinkRemote) databean.getMbo(i);// 数据库配置中表的对象类
            // System.out.println(i + " bb "
            // + wosafetylink.getString("TAGOUTID"));
            // String TAGOUTIDQZ =
            // wosafetylink.getString("TAGOUTID").substring(0,
            // 2);// 得到隔离点前缀
            if (wosafetylink == null) {
                continue;
            }
            if (wosafetylink.toBeDeleted()) {
                continue;

            }
            tagoutSet = wosafetylink.getMboSet("TAGOUT");
            // System.out.println("保存隔离点shanboheng tagoutSet.count() "
            // + tagoutSet.count());
            if (tagoutSet.isEmpty()) {
                MboRemote tagout = tagoutSet.add();
                String siteid = this.getString("siteid");
                String s_ordertype = this.getString("S_ORDERTYPE");
                if (!"一级动火工作票".equals(s_ordertype)// "FYDC".equals(siteid) &&
                        && !"二级动火工作票".equals(s_ordertype) && !"工作任务单".equals(s_ordertype)) {
                    copyAttrsFromWOSLTagoutToTagout(tagout, wosafetylink);
                }
                tagoutSet.save();
            }

        }

    }

    public void copyAttrsFromWOSLTagoutToTagout(MboRemote tagout, MboRemote wosltagout)
            throws MXException, RemoteException {

        // System.out.println("隔离点保存开始》》》》》》》》》》》》");
        String[] tagoutAttrs = {"description", "location", "assetnum", "TAG01", "TAG02", "TAG03", "TAG04", "TAG05",
                "TAG06", "TAG07", "TAG08", "TAGOUTID", "TAGMETHOD", "REQUIREDSTATE", "ISKG", "ISDX", "ISBSP"};// 隔离点库字段

        String[] wosltagoutAttrs = {"tagoutdescription2", "location", "assetnum", "TAG01", "TAG02", "TAG03", "TAG04",
                "TAG05", "TAG06", "TAG07", "TAG08", "TAGOUTID", "TAGMETHOD", "REQUIREDSTATE2", "ISKG", "ISDX",
                "ISBSP"};// 工作票安错

        for (int i = 0; i < tagoutAttrs.length; i++) {
            tagout.setValue(tagoutAttrs[i], wosltagout.getString(wosltagoutAttrs[i]), 11L);
        }
        String siteid = this.getString("siteid");
        String s_ordertype = this.getString("S_ORDERTYPE");
        if (!"一级动火工作票".equals(s_ordertype)// "FYDC".equals(siteid) &&
                && !"二级动火工作票".equals(s_ordertype) && !"工作任务单".equals(s_ordertype)) {
            tagout.setValue("tag01", "隔离点");

        }

    }

    // 创建受限空间工作许可证
    public void addsyxkz() throws RemoteException, MXException {
        app.getAppBean().save();

        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN");

        // 设置许可证的类型
        syMbo.setValue("type", "1001");

        // 添加相关子表信息
        addData(syMbo);

    }

    /**
     * 创建挖掘许可证
     *
     * @throws RemoteException
     * @throws MXException
     */
    public void addwzxkz() throws RemoteException, MXException {
        app.getAppBean().save();

        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN3");

        // 设置许可证的类型
        syMbo.setValue("type", "1003");

        // 添加相关子表信息
        addData(syMbo);
    }

    /**
     * 创建高处作业许可证
     *
     * @throws RemoteException
     * @throws MXException
     */
    public void addgcxkz() throws RemoteException, MXException {
        app.getAppBean().save();

        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN2");
        // 设置许可证的类型
        syMbo.setValue("type", "1002");
        // false
        // 添加相关子表信息
        addData(syMbo);
    }

    // 创建起吊作业许可证
    public void addqdxkz() throws RemoteException, MXException {
        app.getAppBean().save();
        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN4");
        // 设置许可证的类型
        syMbo.setValue("type", "1004");
        // // 添加相关子表信息
        // addData(syMbo);

    }

    // 创建安健环技术交底卡
    public void addajhjdk() throws RemoteException, MXException {
        app.getAppBean().save();
        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN5");
        // 设置许可证的类型
        syMbo.setValue("type", "1005");
        // // 添加相关子表信息
        // addData(syMbo);

    }

    // 创建检修箱使用申请单
    public void addjxx() throws RemoteException, MXException {
        app.getAppBean().save();
        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN6");
        // 设置许可证的类型
        syMbo.setValue("type", "1006");
    }

    // /////////////////////////////////////////////////////
    // 创建临时用电申请单
    public void addlsyd() throws RemoteException, MXException {
        app.getAppBean().save();
        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN7");
        // 设置许可证的类型
        syMbo.setValue("type", "1007");
    }

    // 创建压缩空气使用申请单
    public void addyskq() throws RemoteException, MXException {
        app.getAppBean().save();
        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN8");
        // 设置许可证的类型
        syMbo.setValue("type", "1008");
    }

    // 创建设备试验试转申请单
    public void addsbsy() throws RemoteException, MXException {
        app.getAppBean().save();
        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN9");
        // 设置许可证的类型
        syMbo.setValue("type", "1009");
    }

    // 创建消防水使用申请单
    public void addxfs() throws RemoteException, MXException {
        app.getAppBean().save();
        // 创建一个许可证对象
        MboRemote syMbo = addXkzMbo("LICENCEMAIN1");
        // 设置许可证的类型
        syMbo.setValue("type", "1010");
    }

    public void jxxsywf() throws RemoteException, MXException {
        WorkFlowServiceRemote wfsr = ((WorkFlowServiceRemote) MXServer.getMXServer().lookup("WORKFLOW"));
        MboRemote mainMbo = app.getAppBean().getMbo();
        MboSetRemote licSet = mainMbo.getMboSet("LICENCEMAIN6");
        MboRemote tmpMbo = null;
        if (!licSet.isEmpty()) {
            tmpMbo = licSet.getMbo(0);
        }
        wfsr.initiateWorkflow("EC_GZPFJ", tmpMbo);
        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "Success", "检修箱使用申请单流程发送成功. ", 1);
    }

    public void lsydwf() throws RemoteException, MXException {
        WorkFlowServiceRemote wfsr = ((WorkFlowServiceRemote) MXServer.getMXServer().lookup("WORKFLOW"));
        MboRemote mainMbo = app.getAppBean().getMbo();
        MboSetRemote licSet = mainMbo.getMboSet("LICENCEMAIN7");
        MboRemote tmpMbo = null;
        if (!licSet.isEmpty()) {
            tmpMbo = licSet.getMbo(0);
        }
        wfsr.initiateWorkflow("EC_GZPFJ", tmpMbo);
        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "Success", "临时用电申请流程发送成功. ", 1);
    }

    public void yskqwf() throws RemoteException, MXException {
        WorkFlowServiceRemote wfsr = ((WorkFlowServiceRemote) MXServer.getMXServer().lookup("WORKFLOW"));
        MboRemote mainMbo = app.getAppBean().getMbo();
        MboSetRemote licSet = mainMbo.getMboSet("LICENCEMAIN8");
        MboRemote tmpMbo = null;
        if (!licSet.isEmpty()) {
            tmpMbo = licSet.getMbo(0);
        }
        wfsr.initiateWorkflow("EC_GZPFJ", tmpMbo);
        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "Success", "压缩空气使用申请单流程发送成功. ", 1);
    }

    public void sbsywf() throws RemoteException, MXException {
        WorkFlowServiceRemote wfsr = ((WorkFlowServiceRemote) MXServer.getMXServer().lookup("WORKFLOW"));
        MboRemote mainMbo = app.getAppBean().getMbo();
        MboSetRemote licSet = mainMbo.getMboSet("LICENCEMAIN9");
        MboRemote tmpMbo = null;
        if (!licSet.isEmpty()) {
            tmpMbo = licSet.getMbo(0);
        }
        wfsr.initiateWorkflow("EC_GZPFJ", tmpMbo);
        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "Success", "设备试验试转申请单流程发送成功. ", 1);
    }

    public void xfswf() throws RemoteException, MXException {
        WorkFlowServiceRemote wfsr = ((WorkFlowServiceRemote) MXServer.getMXServer().lookup("WORKFLOW"));
        MboRemote mainMbo = app.getAppBean().getMbo();
        MboSetRemote licSet = mainMbo.getMboSet("LICENCEMAIN1");
        MboRemote tmpMbo = null;
        if (!licSet.isEmpty()) {
            tmpMbo = licSet.getMbo(0);
        }
        wfsr.initiateWorkflow("EC_GZPFJ", tmpMbo);
        Utility.showMessageBox(this.clientSession.getCurrentEvent(), "Success", "消防水使用申请单流程发送成功. ", 1);
    }

    /**
     * 创建一个许可证对象
     *
     * @return
     * @throws RemoteException
     * @throws MXException
     */
    public MboRemote addXkzMbo(String relationName) throws RemoteException, MXException {
        if (!hasExists(relationName)) {
            String xkzName = "";
            if ("LICENCEMAIN".equalsIgnoreCase(relationName)) {
                xkzName = "受限空间作业许可证";
            } else if ("LICENCEMAIN3".equalsIgnoreCase(relationName)) {
                xkzName = "挖掘作业许可证";
            } else if ("LICENCEMAIN4".equalsIgnoreCase(relationName)) {
                xkzName = "起吊作业许可证";
            } else if ("LICENCEMAIN5".equalsIgnoreCase(relationName)) {
                xkzName = "安健环技术交底检查卡";
            } else if ("LICENCEMAIN6".equalsIgnoreCase(relationName)) {
                xkzName = "检修箱使用申请单";
            } else if ("LICENCEMAIN7".equalsIgnoreCase(relationName)) {
                xkzName = "临时用电申请单";
            } else if ("LICENCEMAIN8".equalsIgnoreCase(relationName)) {
                xkzName = "压缩空气使用申请单";
            } else if ("LICENCEMAIN9".equalsIgnoreCase(relationName)) {
                xkzName = "设备试验试转申请单";
            } else if ("LICENCEMAIN1".equalsIgnoreCase(relationName)) {
                xkzName = "消防水使用申请单";
            } else {
                xkzName = "高处作业许可证";
            }
            throw new MXApplicationException("xkz", "xkzexists", new String[]{xkzName});
        }
        // 得到主表对象
        MboRemote mainMbo = getMbo();
        // 得到工单号
        String wonum = mainMbo.getString("wonum");
        // 得到受限许可证对象集
        MboSetRemote sySet = mainMbo.getMboSet(relationName);
        // 添加受限许可证对象
        MboRemote syMbo = sySet.add();
        // 设置关联的工单号
        syMbo.setValue("wonum", wonum);

        return syMbo;
    }

    private boolean hasExists(String relationName) throws RemoteException, MXException {
        MboRemote mainMbo = getMbo();
        MboSetRemote set = mainMbo.getMboSet(relationName);
        if (set.count() > 0) {
            return false;
        }
        return true;
    }

    /**
     * 添加危险源
     *
     * @throws RemoteException
     * @throws MXException
     */
    public void addData(MboRemote mbo) throws RemoteException, MXException {
        String type = mbo.getString("type"); // 得到许可证类型
        // 查找许可证配置中的相关类型的许可证配置信息
        MboSetRemote licenceConfigSet = mbo.getMboSet("$LICENCEMAIN", "LICENCEMAIN",
                "type ='" + type + "' and config = 1 ");
        // 倒叙排序，只取时间最新的
        licenceConfigSet.setOrderBy("createdate desc");
        if (licenceConfigSet != null && !licenceConfigSet.isEmpty()) {
            MboRemote licenceConfigMbo = licenceConfigSet.getMbo(0);
            mbo.setValue("JJQKSM", licenceConfigMbo.getString("JJQKSM"));// 紧急情况说明
            mbo.setValue("remark", licenceConfigMbo.getString("remark"));// 备注

            // 添加子表信息
            addLineData(licenceConfigMbo, mbo);
        }
    }

    /**
     * 添加危险源
     *
     * @throws RemoteException
     * @throws MXException
     */
    public void addLineData(MboRemote licenceConfigMbo, MboRemote mbo) throws RemoteException, MXException {
        for (Map.Entry<String, String> entry : relationMap.entrySet()) {
            // 得到子表类型
            String type = entry.getValue();
            // 得到关联名称
            String relationName = entry.getKey();
            MboSetRemote wxLineSet = licenceConfigMbo.getMboSet(relationName);
            wxLineSet.setOrderBy("sn");

            MboSetRemote wowxLineSet = mbo.getMboSet(relationName);
            for (int x = 0; x < wxLineSet.count(); x++) {
                MboRemote wxMbo = wxLineSet.getMbo(x);
                MboRemote newMbo = wowxLineSet.addAtEnd();
                // 设置序号
                newMbo.setValue("sn", wxMbo.getInt("sn"));
                // 设置描述信息
                newMbo.setValue("description", wxMbo.getString("description"));
                // 设置类型
                newMbo.setValue("type", type);

                newMbo.setValue("parent", mbo.getLong("licencemainid"));
            }
        }
    }

    /**
     * 设置为草稿状态
     *
     * @throws MXException
     * @throws RemoteException
     */
    public void WAPPR() throws RemoteException, MXException {
        MboRemote mbo = this.app.getAppBean().getMbo();
        String appname = mbo.getThisMboSet().getName();
        if ("BZGZPGL".equalsIgnoreCase(appname)) {
            // 设置不只读
            mbo.setValue("status", "草稿", 11L);
            mbo.setFlag(MboConstants.READONLY, false);
            save();
        }
    }

    /**
     * 设置为已批准状态
     *
     * @throws MXException
     * @throws RemoteException
     */
    public void APPR() throws RemoteException, MXException {
        MboRemote mbo = this.app.getAppBean().getMbo();
        String appname = mbo.getThisMboSet().getName();
        if ("BZGZPGL".equalsIgnoreCase(appname)) {
            // 设置不只读
            mbo.setFlag(MboConstants.READONLY, false);
            mbo.setValue("status", "已批准", 11L);
            save();
        }
    }

}
