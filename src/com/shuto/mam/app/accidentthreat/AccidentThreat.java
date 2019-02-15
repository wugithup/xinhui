/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.accidentthreat;

import psdi.mbo.Mbo;
import psdi.mbo.MboSet;
import psdi.security.ConnectionKey;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Calendar;

public class AccidentThreat extends Mbo implements AccidentThreatRemote {

    private final String APPLOGGER = "maximo.application.ACCIDENTTHREAT";

    private MXLogger log;

    public AccidentThreat(MboSet mboSet0) throws RemoteException {

        super(mboSet0);
        this.log = MXLoggerFactory.getLogger("maximo.application.ACCIDENTTHREAT");
    }

    public void add() throws MXException, RemoteException {

        setValue("ATNUM", "保存后生成...");
        super.add();
    }

    // 界面控制实现
    // public void init() throws MXException {
    // String status = getMboValue("STATUS").getString();
    //
    // if ((!"WAPPR".equals(status)) && (!"UCP".equals(status)))
    // if (("CAN".equals(status)) || ("CLOSE".equals(status))) {
    // setFlag(7L, true);
    // } else if ("BMAJZGYPG".equals(status)) {
    // setFieldFlag("MAYRESULT", 128L, true);
    // setFieldFlag("PREVALUATEATLEVAL", 128L, true);
    // setFieldFlag("PREVALUATEDEPTBZ", 128L, true);
    // } else if ("BMFZRYPG".equals(status)) {
    // setFieldFlag("MAYRESULT", 128L, true);
    // setFieldFlag("PREVALUATEATLEVAL", 128L, true);
    // setFieldFlag("EHSSAFTYPERSON", 128L, true);
    // setFieldFlag("GOVERNPERSON", 128L, true);
    // setFieldFlag("GOVERNDEPT", 128L, true);
    // } else if ("EHSZGPG".equals(status)) {
    // if ("重大隐患".equals(getMboValue("ATLEVAL").toString())) {
    // setFieldFlag("LEADER", 128L, true);
    // }
    // setFieldFlag("ATLEVAL", 128L, true);
    // } else if ("LDHD".equals(status)) {
    // setFieldFlag("LEADERREMARKS", 128L, true);
    // } else if ("BMZGCLZ".equals(status)) {
    // setFieldFlag("GOVERNFROMDATE", 128L, true);
    // setFieldFlag("GOVERNTODATE", 128L, true);
    // setFieldFlag("GOVERNRESULT", 128L, true);
    // setFieldFlag("TOTALGOVERNFUNDS", 128L, true);
    // setFieldFlag("ACCEPTEREQDEPT", 128L, true);
    // setFieldFlag("ACCEPTEREQPERSON", 128L, true);
    // } else if ("YSZ".equals(status)) {
    // setFieldFlag("ACCEPTEDDEPT", 128L, true);
    // setFieldFlag("ACCEPTEDPERSON", 128L, true);
    // setFieldFlag("ACCEPTEDREMARK", 128L, true);
    // setFieldFlag("ACCEPTEDCONCLUSION", 128L, true);
    // } else if ("REQREWORD".equals(status)) {
    // setFieldFlag("REWORDEHSREMARK", 128L, true);
    // setFieldFlag("APPRREWORDPERSON", 128L, true);
    // } else if (("LDSH".equals(status)) || ("APPRREWORD".equals(status))) {
    // setFieldFlag("REWORDEHSREMARK", 128L, true);
    // setFieldFlag("APPRREWORDREMARK", 128L, true);
    // }
    // super.init();
    // }

    public void save() throws MXException, RemoteException {

        if (toBeAdded()) {
            setValue("ATNUM", genATNum(), 11L);
        }
        super.save();
    }

    private String genATNum() throws RemoteException, MXException {

        Calendar cal = Calendar.getInstance();
        StringBuilder prefix = new StringBuilder(getString("ORGID"));
        prefix = prefix.append(String.valueOf(cal.get(1)));

        int nextNum = 1;
        ConnectionKey conKey = null;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conKey = new ConnectionKey(MXServer.getMXServer().getSystemUserInfo());
            con = MXServer.getMXServer().getDBManager().getConnection(conKey);
            stmt = con.createStatement();
            stmt.setQueryTimeout(10);
            String sql =
                    "select max(substr(atnum,10)) as curatnum from ACCIDENTTHREAT where atnum like '" +
                            prefix + "%' ";
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                if (Integer.valueOf(rs.getInt("curatnum")) != null) {
                    nextNum = rs.getInt("curatnum") + 1;
                }
            }
        } catch (SQLException e) {
            log.error(e);
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException e1) {
                log.error(e1);
            }
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    stmt = null;
                }
                if (con != null) {
                    con.close();
                    con = null;
                }
                if (rs != null) {
                    rs.close();
                    rs = null;
                }
            } catch (SQLException e) {
                log.error(e);
            }
        }
        return prefix + Integer.toString(nextNum);
    }
}