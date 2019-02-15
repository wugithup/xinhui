package com.shuto.mam.webclient.beans.operation.opticket;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.ConnectionKey;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXApplicationYesNoCancelException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.Utility;
import psdi.webclient.system.controller.WebClientEvent;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 作者 E-mail: xiamy@shuto.cn
 * @version 1.0
 * @date 创建时间：2017年7月25日 下午8:44:35
 * @changby zhenglb zhenglb@shuto.cn
 * @changDate 2017年8月28日
 * @Copyright: 2017 Shuto版权所有
 * @since
 */
public class BzOpticketAppBean extends AppBean {
    @Override
    public int SAVE() throws MXException, RemoteException {
        MboRemote mainMbo = this.app.getAppBean().getMbo();
        // 当前登录人
        String personid = mainMbo.getUserInfo().getPersonId();
        // 获取系统时间
        Date sysdate = new Date();
        // 格式化
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = dateformat.format(sysdate);
        mainMbo.setValue("CHANGEPERSON", personid);
        mainMbo.setValue("CHANGEDATE", date);
        return super.SAVE();
    }

    @Override
    protected void setCurrentRecordData(MboRemote mbo) throws MXException, RemoteException {
        String status = mbo.getString("status");
        if ("已批准".equals(status)) {
            mbo.setFlag(MboConstants.READONLY, true);
        }
        super.setCurrentRecordData(mbo);
    }

    /**
     * 设置为草稿状态
     *
     * @throws MXException
     * @throws RemoteException
     */
    public void WAPPR() throws RemoteException, MXException {
        MboRemote mbo = this.app.getAppBean().getMbo();
        // 设置不只读
        mbo.setValue("status", "草稿", 11L);
        mbo.setFlag(MboConstants.READONLY, false);
        save();
    }

    /**
     * 设置为已批准状态
     *
     * @throws MXException
     * @throws RemoteException
     */
    public void APPR() throws RemoteException, MXException {
        MboRemote mbo = this.app.getAppBean().getMbo();
        // 设置不只读
        mbo.setFlag(MboConstants.READONLY, false);
        mbo.setValue("status", "已批准", 11L);
        save();

    }

    /**
     * 删除标准操作票
     *
     * @return
     * @throws MXException
     * @throws RemoteException
     */
    public int DELOPTICK() throws RemoteException, MXException {
        /*  */
        String status = getString("status");
        if (!"草稿".equals(status)) {
            throw new MXApplicationException("", "只有草稿状态的单据才能被删除！");
        }
        WebClientEvent event = clientSession.getCurrentEvent();
        int msgRet = event.getMessageReturn();
        if (msgRet < 0) {
            // 弹出提示窗口问是否继续
            throw new MXApplicationYesNoCancelException("deletecontinueid", "system", "deletecontinue");
        }
        if (msgRet == 8) {
            MboSetRemote opticketline = this.getMbo().getMboSet("OPTICKETNUM_OPTICKETLINE");
            opticketline.deleteAll();
            MboSetRemote opticketpoint = this.getMbo().getMboSet("OPTICKETNUM_OPTICKETDPOINT");
            opticketpoint.deleteAll();
            delete();
            save();
            clientSession.showMessageBox(clientSession.getCurrentEvent(), "system", "deleterecord", (Object[]) null);
            gotoTab(clientSession, "list");
        }
        return 1;
    }

    @Override
    public int DUPLICATE() throws MXException, RemoteException {
        MboRemote opticketMbo = this.app.getAppBean().getMbo();
        MboRemote newmbo = getMboSet().add();
        System.out.println("这真的是测试数据===============测试数据");
        //运行值别
        newmbo.setValue("YXZB", opticketMbo.getString("YXZB"), 11L);
        //操作任务
        newmbo.setValue("MISSION", opticketMbo.getString("MISSION"), 11L);
        //业务类型
        newmbo.setValue("TYPE1", opticketMbo.getString("TYPE1"), 11L);
        //操作票类型
        newmbo.setValue("H_TYPE", opticketMbo.getString("H_TYPE"), 11L);
        //专业
        newmbo.setValue("PROFESSION", opticketMbo.getString("PROFESSION"), 11L);
        //危险区域
        newmbo.setValue("WXQUYU", opticketMbo.getString("WXQUYU"), 11L);
        //状态
        newmbo.setValue("STATUS", "草稿", 11L);
        //设置为标准操作票
        newmbo.setValue("ISOPTICKETBZ", 1, 11L);
        copybzopt(opticketMbo, newmbo);
        setCurrentRecordData(newmbo);
        return 1;
    }

    /**
     * 复制标准操作票步骤和危险点
     *
     * @param opticketMbo
     * @param newmbo
     * @throws MXException
     * @throws RemoteException
     */
    private void copybzopt(MboRemote opticketMbo, MboRemote newmbo) throws MXException, RemoteException {
        try {
            long a = System.currentTimeMillis();
            ConnectionKey key = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
            Connection connection = MXServer.getMXServer().getDBManager().getConnection(key);
            //获取copy后的操作票编号
            String opticketnum = newmbo.getString("opticketnum");
            //操作步骤sql
            String optLine = "select ORDERNUM,OPTICKETPROJECT,YUKONG,siteid,orgid from OPTICKETLINE " +
                    "where opticketnum='" + opticketMbo.getString("opticketnum") + "'";
            //危险预防措施sql
            String dpLine = "select ORDERNUM,DANGEROUSPOINT,DPFCMS,FXTYPES,FXTYPEH,FXTYPEE,siteid,orgid from OPTICKETDPOINT " +
                    "where  opticketnum='" + opticketMbo.getString("opticketnum") + "'";
            connection.setAutoCommit(false);
            Statement optLinestm = connection.createStatement();
            ResultSet optLiners = optLinestm.executeQuery(optLine);

            String insertoptlinesql = "INSERT INTO OPTICKETLINE(ORDERNUM,OPTICKETPROJECT,YUKONG,siteid,orgid,hasld,createperson,opticketnum,OPTICKETLINEID,isop)" +
                    "VALUES (?,?,?,?,?,0,?,?,OPTICKETLINEIDSEQ.NEXTVAL,0)";
            PreparedStatement optLinesm = connection.prepareStatement(insertoptlinesql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            while (optLiners.next()) {
                optLinesm.setInt(1, optLiners.getInt("ordernum"));
                optLinesm.setString(2, optLiners.getString("opticketproject"));
                optLinesm.setString(3, optLiners.getString("yukong"));
                optLinesm.setString(4, optLiners.getString("siteid"));
                optLinesm.setString(5, optLiners.getString("orgid"));
                optLinesm.setString(6, opticketMbo.getUserName());
                optLinesm.setString(7, opticketnum);
                optLinesm.addBatch();

            }
            optLinesm.executeBatch();
            optLiners.close();
            optLinestm.close();
            optLinesm.close();

            //-----------------------------------可爱的分割线------------------------------------------------------------//
            Statement dpLinestm = connection.createStatement();
            ResultSet dpLiners = dpLinestm.executeQuery(dpLine);
            //添加危险点的sql
            String insertdplinesql = "INSERT INTO OPTICKETDPOINT(ORDERNUM,DANGEROUSPOINT,DPFCMS,FXTYPES,FXTYPEH,FXTYPEE,SITEID,ORGID,HASLD,OPTICKETNUM,OPTICKETDPOINTID)" +
                    "VALUES (?,?,?,?,?,?,?,?,0,?,OPTICKETDPOINTIDSEQ.NEXTVAL)";
            PreparedStatement dpLinesm = connection.prepareStatement(insertdplinesql, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println(insertdplinesql);
            while (dpLiners.next()) {
                dpLinesm.setInt(1, dpLiners.getInt("ordernum"));
                dpLinesm.setString(2, dpLiners.getString("DANGEROUSPOINT"));
                dpLinesm.setString(3, dpLiners.getString("DPFCMS"));
                dpLinesm.setString(4, dpLiners.getString("FXTYPES"));
                dpLinesm.setString(5, dpLiners.getString("FXTYPEH"));
                dpLinesm.setString(6, dpLiners.getString("FXTYPEE"));
                dpLinesm.setString(7, dpLiners.getString("siteid"));
                dpLinesm.setString(8, dpLiners.getString("orgid"));
                dpLinesm.setString(9, opticketnum);
                dpLinesm.addBatch();
            }
            dpLinesm.executeBatch();
            dpLiners.close();
            dpLinestm.close();
            dpLinesm.close();
            connection.commit();
            connection.close();
            Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "",
                    "操作成功\r<br>新的标准票编号为：" + opticketnum + "\r<br>执行耗时：" + (float) (System.currentTimeMillis() - a) / 1000.0F + " 秒 ",
                    1);
        } catch (Exception e) {
            e.printStackTrace();
            Utility.showMessageBox(this.sessionContext.getCurrentEvent(), "", "复制失败，请联系管理员", 1);
        }
    }
}
