/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.util;

import psdi.security.ConnectionKey;
import psdi.server.MXServer;
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
 * @Title: JDBCUtil
 * @Package com.shuto.mam.util
 * @Description: JDBC连接方法
 * @date 2018-10-17 20:51
 */
public class JDBCUtil {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    /**
     * 获取connection
     *
     * @return
     */
    public static Connection conn() {

        Connection connection = null;
        try {
            ConnectionKey key = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
            connection = MXServer.getMXServer().getDBManager().getConnection(key);
        } catch (RemoteException e) {
            MX_LOGGER.error(e);
        }
        return connection;
    }

    /**
     * 释放资源
     *
     * @param conn
     * @param st
     * @param rs
     */
    public static void colseResource(Connection conn, Statement st, ResultSet rs) {

        closeResultSet(rs);
        closeStatement(st);
        closeConnection(conn);
    }

    /**
     * 释放连接 Connection
     *
     * @param conn
     */
    private static void closeConnection(Connection conn) {

        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                MX_LOGGER.error(e);
            }
        }
    }

    /**
     * 释放语句执行者 Statement
     *
     * @param st
     */
    private static void closeStatement(Statement st) {

        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                MX_LOGGER.error(e);
            }
        }
    }

    /**
     * 释放结果集 ResultSet
     *
     * @param rs
     */
    private static void closeResultSet(ResultSet rs) {

        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                MX_LOGGER.error(e);
            }
        }
    }
}
