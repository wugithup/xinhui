/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.util;

import com.shuto.mam.util.JDBCUtil;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author SumMer
 * @version V1.0
 * @Title: AppUtil
 * @Package com.shuto.hydro.mam.app.util
 * @Description:
 * @date 2018-10-25 21:15
 */
public class AppUtil {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    /**
     * 提取循环获取应用名的方法，以便统一调用
     *
     * @param sourceMbo 对象结构
     * @return appname
     * @throws RemoteException
     */
    public static String getAppName(MboRemote sourceMbo) {

        String appName = null;
        MboSetRemote thisMboSet;
        MboRemote owner = sourceMbo;
        try {
            do {
                if (owner.isZombie()) {
                    break;
                }
                thisMboSet = owner.getThisMboSet();
                if (thisMboSet != null) {
                    appName = thisMboSet.getApp();
                    if (appName != null && !"".equalsIgnoreCase(appName)) {
                        break;
                    }
                }
            } while ((owner = owner.getOwner()) != null);
        } catch (RemoteException re) {
            MX_LOGGER.error(re);
        }
        return appName;
    }

    /**
     * 查询autokey
     *
     * @param siteid      地点
     * @param orgid       组织
     * @param autokeyname autokey字段名
     * @return 组合后的autokey
     */
    public String getAutoKey(String siteid, String orgid, String autokeyname) {

        Connection conn = JDBCUtil.conn();
        Statement st = null;
        ResultSet rs = null;
        String autokey = null;
        try {
            StringBuilder sql = new StringBuilder(
                    "SELECT PREFIX,SEED FROM AUTOKEY WHERE AUTOKEYNAME='" + autokeyname + "'");
            if ("".equalsIgnoreCase(siteid)) {
                sql.append(" AND SITEID='").append(siteid).append("'");
            }
            if ("".equalsIgnoreCase(orgid)) {
                sql.append(" AND ORGID='").append(orgid).append("'");
            }
            st = conn.createStatement();
            rs = st.executeQuery(sql.toString());
            if (rs.next()) {
                //前缀
                String prefix = rs.getString("PREFIX");
                //编号
                int seed = rs.getInt("SEED");
                autokey = prefix + String.valueOf(seed).trim();
            }
        } catch (SQLException e) {
            MX_LOGGER.error(e);
        } finally {
            JDBCUtil.colseResource(conn, st, rs);
        }
        return autokey;
    }
}
