/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.util;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;

/**
 * @author SumMer
 * @version V1.0
 * @Title: AutoNumUtil
 * @Package com.shuto.hydro.mam.app.util
 * @Description: 自定义自增长编号
 * @date 2018-10-25 19:44
 */
public class AutoNumUtil {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    public AutoNumUtil() {

        MX_LOGGER.info("自定义自增长编号");
    }

    public static int getNextNumBer(MboRemote mbo, String appname, String yeas, String month,
            String type, String profession, String attName) {

        return getNum(mbo, appname, yeas, month, type, profession, attName);
    }

    /**
     * 获取下一个编号
     *
     * @param mbo        当前对象
     * @param yeas       年份
     * @param month      月份
     * @param type       类型
     * @param profession 专业
     * @param attName    字段名
     * @return 当前编号
     */
    public static int getNextNumBer(MboRemote mbo, String yeas, String month, String type,
            String profession, String attName) throws RemoteException {

        String appName = mbo.getThisMboSet().getApp();
        return getNum(mbo, appName, yeas, month, type, profession, attName);
    }

    private static int getNum(MboRemote mbo, String appname, String yeas, String month, String type,
            String profession, String attName) {

        int num = 1;
        try {
            if (!mbo.getThisMboSet().isEmpty()) {
                String siteid = mbo.getString("siteid");
                String orgid = mbo.getString("orgid");
                StringBuilder sql = new StringBuilder(
                        "SITEID='" + siteid + "' AND ORGID='" + orgid + "' AND APPNAME='" +
                                appname + "' ");
                if (!"".equalsIgnoreCase(yeas)) {
                    sql.append(" AND YEAR='").append(yeas).append("'");
                }
                if (!"".equalsIgnoreCase(month)) {
                    sql.append(" AND MONTH='").append(month).append("'");
                }
                if (!"".equalsIgnoreCase(type)) {
                    sql.append(" AND TYPE='").append(type).append("'");
                }
                if (!"".equalsIgnoreCase(profession)) {
                    sql.append(" AND PROFESSION='").append(profession).append("'");
                }
                if (!"".equalsIgnoreCase(attName)) {
                    sql.append(" AND OWNERATTRIBUTENAME='").append(attName).append("'");
                }
                MboSetRemote mboSet = MXServer.getMXServer().getMboSet("AUTODATESITENUM",
                        MXServer.getMXServer().getSystemUserInfo());
                mboSet.setWhere(sql.toString());
                if (mboSet.isEmpty()) {
                    MboRemote autoMbo = mboSet.add();
                    autoMbo.setValue("SITEID", siteid);
                    autoMbo.setValue("ORGID", orgid);
                    autoMbo.setValue("APPNAME", appname);
                    autoMbo.setValue("YEAR", yeas);
                    autoMbo.setValue("MONTH", month);
                    autoMbo.setValue("PROFESSION", profession);
                    autoMbo.setValue("TYPE", type);
                    autoMbo.setValue("OWNERATTRIBUTENAME", attName);
                    autoMbo.setValue("NUM", num);
                } else {
                    MboRemote mboRemote = mboSet.getMbo(0);
                    num = mboRemote.getInt("NUM");
                    mboRemote.setValue("NUM", ++num);
                }
                mboSet.save();
                mboSet.close();
            }
        } catch (MXException | RemoteException e) {
            MX_LOGGER.error(e);
        }
        return num;
    }

}
