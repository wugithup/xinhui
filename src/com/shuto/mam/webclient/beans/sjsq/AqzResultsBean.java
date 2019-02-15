/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.webclient.beans.sjsq;

import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.ResultsBean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AqzResultsBean extends ResultsBean {

    @Override
    protected MboSetRemote getMboSetRemote() throws MXException, RemoteException {

        MboSetRemote msr = super.getMboSetRemote();
        String personid = "";
        String glyjb = "";
        String siteid = "";
        String orgid = "";

        String sql = "";
        Statement st = null;
        ResultSet rs = null;
        Connection con = null;
        if (!msr.isEmpty()) {
            personid = this.clientSession.getUserInfo().getPersonId();

            String CXSQL = "select  glyjb,locationsite,locationorg from  person  where personid='" +
                    personid + "'";
            try {
                con = MXServer.getMXServer().getDBManager().getConnection(
                        MXServer.getMXServer().getSystemUserInfo().getConnectionKey());
                st = con.createStatement();
                rs = st.executeQuery(CXSQL);
                while (rs.next()) {
                    glyjb = rs.getString("glyjb");
                    orgid = rs.getString("locationorg");
                    siteid = rs.getString("locationsite");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                    if (con != null) {
                        con.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        if ("01".equals(glyjb)) {
            //System.out.println("控股管理员");
            sql = "1=1";
        }
        if ("02".equals(glyjb) && !orgid.isEmpty()) {
            // System.out.println("大区管理员");
            sql = "orgid='" + orgid + "'";
        }
        if ("03".equals(glyjb) && !orgid.isEmpty() && !siteid.isEmpty()) {
            // System.out.println("项目管理员");
            sql = "orgid='" + orgid + "' and  siteid ='" + siteid + "'";
        }
        msr.setWhere(sql);
        msr.reset();
        return msr;
    }
}
