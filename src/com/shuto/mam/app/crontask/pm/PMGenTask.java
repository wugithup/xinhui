/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.crontask.pm;

import com.shuto.mam.app.pm.CustPm;
import com.shuto.mam.app.util.CronPmUtil;
import com.shuto.mam.util.JDBCUtil;
import psdi.app.system.CrontaskParamInfo;
import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author SumMer
 * @version V1.0
 * @Title: PMGenTask
 * @Package com.shuto.mam.app.crontask.pm
 * @Description: 预防性任务
 * @date 2018-11-30 0030 09:49
 */
public class PMGenTask extends SimpleCronTask implements MboConstants {

    private String siteid;

    private MXServer mxserver;

    private UserInfo userInfo;

    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private CronPmUtil cronPmUtil = new CronPmUtil();

    private Connection conn;

    @Override
    public void init() throws MXException {

        try {
            getCronTaskLogger().info("======>init方法<=====");
            super.init();
            readConfig();// 读取配置地点
            this.mxserver = MXServer.getMXServer();
            this.userInfo = getRunasUserInfo();
        } catch (Exception e) {
            getCronTaskLogger().error(e);
        }
    }

    private void readConfig() throws RemoteException, MXException {

        getCronTaskLogger().info("======>readConfig方法<=====");
        this.siteid = getParamAsString("SITEID");
    }

    @Override
    public CrontaskParamInfo[] getParameters() {

        getCronTaskLogger().info("======>getParameters方法<=====");
        try {
            String[] names = {"siteid"};
            String[] defs = {"修改成对应的siteid"};
            String[][] descriptions = {{"cronprepr", "CronParamSiteid"}};
            CrontaskParamInfo[] ret = new CrontaskParamInfo[names.length];
            for (int i = 0; i < names.length; ++i) {
                ret[i] = new CrontaskParamInfo();
                ret[i].setName(names[i]);
                ret[i].setDefault(defs[i]);
                ret[i].setDescription(descriptions[i][0], descriptions[i][1]);
            }
            return ret;
        } catch (Exception e) {
            if (getCronTaskLogger().isErrorEnabled()) {
                getCronTaskLogger().error(e);
            }
        }
        return null;
    }

    @Override
    public void cronAction() {

        try {
            this.conn = JDBCUtil.conn();
            this.conn.setAutoCommit(false);
            getCronTaskLogger().info("======>cronAction方法<=====");
            getCronTaskLogger()
                    .info("======>定时任务开始：" + this.dateTimeFormat.format(new Date()) + "<=====");
            this.setNoWith();
            this.setOverdueUpdateNextDate();
            this.checkNextdate();
            getCronTaskLogger()
                    .info("======>定时任务结束：" + this.dateTimeFormat.format(new Date()) + "<=====");
        } catch (Exception e) {
            getCronTaskLogger().error(e);
        }
    }

    /**
     * 设置不符合条件的PM为不活动
     */
    private void setNoWith() {

        try {
            getCronTaskLogger().info("======>setNoWith方法<=====");
            //查询位置，下个日期，位置是否合法的PM
            MboSetRemote pmSet = this.mxserver.getMboSet("PMLINE", this.userInfo);
            pmSet.setWhere(
                    "PMNUM IN(SELECT PMNUM FROM PM WHERE WORKTYPE = '预维护工单' AND NEXTDATE IS  NULL  AND LOCATION IN " +
                            "(SELECT LOCATION FROM LOCATIONS WHERE SITEID = '" + this.siteid +
                            "') AND LOCATION IS NULL AND SITEID='" + this.siteid +
                            "') AND  TRUNC(NEXTDATE)<=TRUNC(SYSDATE)");
            pmSet.reset();
            getCronTaskLogger().info("======>setNoWith--WHERE:" + pmSet.getWhere() + "<=====");
            if (!pmSet.isEmpty()) {
                getCronTaskLogger().info("======>不合格的PM数量:" + pmSet.count() + "<=====");
                //把不合格的PM设置为不活动
                for (int i = 0; pmSet.getMbo(i) != null; i++) {
                    getCronTaskLogger().info("======>设置第" + i + "个任务为不活动<=====");
                    pmSet.getMbo(i).setValue("STATUS", "不活动", NOACTION + NOADD + NOACCESSCHECK);
                }
            }
            pmSet.clear();
            pmSet.close();
        } catch (Exception e) {
            getCronTaskLogger().error(e);
        }
    }

    /**
     * 过期任务并设置新的日期
     */
    private void setOverdueUpdateNextDate() {

        try {
            getCronTaskLogger().info("======>setOverdueUpdateNextDate方法<=====");
            //查询过期但是活动的任务，并更新日期
            MboSetRemote pmSet = this.mxserver.getMboSet("PMLINE", this.userInfo);
            pmSet.setWhere("PMNUM IN(SELECT PMNUM FROM PM WHERE WORKTYPE='预维护工单' AND STATUS='活动' " +
                    "AND NEXTDATE IS NOT NULL AND SITEID='" + this.siteid +
                    "') AND TRUNC(NEXTDATE)<TRUNC(SYSDATE)");
            //pmSet.setOrderBy("PMUID DESC");
            pmSet.reset();
            getCronTaskLogger()
                    .info("======>setOverdueUpdateNextDate--WHERE:" + pmSet.getWhere() + "<=====");
            if (!pmSet.isEmpty()) {
                getCronTaskLogger().info("======>过期但是活动的任务数量:" + pmSet.count() + "<=====");
                for (int i = 0; pmSet.getMbo(i) != null; i++) {
                    getCronTaskLogger().info("======>设置第" + i + "个任务的下个日期<=====");
                    pmSet.getMbo(i)
                         .setValue("nextdate", cronPmUtil.getNextdate(pmSet.getMbo(i)).getTime());
                }
            }
            pmSet.clear();
            pmSet.close();
        } catch (Exception e) {
            getCronTaskLogger().error(e);
        }
    }

    /**
     * 设置正常任务的下个日期
     */
    private void checkNextdate() {

        getCronTaskLogger().info("======>checkNextdate方法<=====");
        try {
            MboSetRemote pmSet = this.mxserver.getMboSet("PMLINE", this.userInfo);
            pmSet.setWhere(
                    "PMNUM IN (SELECT PMNUM FROM PM WHERE WORKTYPE='预维护工单' AND STATUS='活动' " +
                            "AND NEXTDATE IS NOT NULL AND SITEID='" + this.siteid +
                            "') AND TRUNC(NEXTDATE)=TRUNC(SYSDATE)");
            //pmSet.setOrderBy("PMUID DESC");
            pmSet.reset();
            getCronTaskLogger().info("======>checkNextdate--WHERE:" + pmSet.getWhere() + "<=====");
            if (!pmSet.isEmpty()) {
                getCronTaskLogger().info("符合条件的数量：" + pmSet.count());
                for (int i = 0; pmSet.getMbo(i) != null; i++) {
                    MboRemote pmMbo = pmSet.getMbo(i);
                    //生成预维护任务
                    this.genTask(pmMbo.getMboSet("PM").getMbo(0));
                    Calendar nextdate = cronPmUtil.getNextdate(pmMbo);
                    getCronTaskLogger().info("======>设置第" + i + "个任务的下个日期：'" +
                            this.dateFormat.format(nextdate.getTime()) + "'<=====");
                    //通过jdbc更新下一到期日
                    SqlFormat sf = new SqlFormat(
                            "UPDATE PMLINE SET NEXTDATE =:1 WHERE PMNUM = :2 AND PMLINENUM=:3");
                    sf.setDate(1, nextdate.getTime());
                    sf.setObject(2, "PM", "PMNUM", pmMbo.getString("PMNUM"));
                    sf.setObject(3, "PMLINE", "PMLINENUM", pmMbo.getString("PMLINENUM"));
                    conn.createStatement().executeUpdate(sf.format());
                }
            }
            //最后提交jdbc
            conn.commit();
            pmSet.close();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                getCronTaskLogger().error(e1);
            }
            getCronTaskLogger().error(e);
        }
    }

    /**
     * 生成预维护任务
     *
     * @param pmMbo
     * @throws RemoteException
     * @throws MXException
     */
    private void genTask(MboRemote pmMbo) throws RemoteException, MXException {
        //复制一个新的pm
        MboRemote newPmMbo = pmMbo.copy();
        //生成AUTOKEY
        newPmMbo.generateAutoKey();
        getCronTaskLogger().info(newPmMbo.getString("PMNUM"));
        newPmMbo.setValue("WORKTYPE", "预维护任务");
        newPmMbo.setValue("STATUS", "活动");
        newPmMbo.setValue("APPSTATUS", "已生成");
        newPmMbo.setValue("BGNUM", this.getNumber(newPmMbo));
        newPmMbo.setValue("PARENTNUM", pmMbo.getString("PMNUM"));
        newPmMbo.getThisMboSet().save();
    }

    /**
     * 获取任务编号
     *
     * @param mbo
     * @return
     * @throws RemoteException
     */
    private String getNumber(MboRemote mbo) throws RemoteException {

        getCronTaskLogger().info(mbo.getName());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy");
        SimpleDateFormat monthFormat = new SimpleDateFormat("MM");
        String year = yearFormat.format(new Date());
        String month = monthFormat.format(new Date());
        CustPm custPm = (CustPm) mbo;
        String bgnum = custPm.getNum(mbo, year, month);
        if (!"".equalsIgnoreCase(bgnum) && bgnum != null) {
            return bgnum;
        }
        return null;
    }
}
