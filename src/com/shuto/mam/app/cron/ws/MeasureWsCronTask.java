/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.cron.ws;

import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

public class MeasureWsCronTask extends SimpleCronTask {

    private static final String RCJLMXWEB_SERVICE =
            "http://cm.crpower.com.cn/erp_cm/cxf/RcjlmxWebService?wsdl";

    private static final String NAMESPACE_URL =
            "http://impl.service.batchacceptance.business.crp.com/";

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    @Override
    public void cronAction() {

        Service service;
        try {

            String url = MXServer.getMXServer().getProperty("mxe.db.url");
            String username = MXServer.getMXServer().getProperty("mxe.db.user");
            String password = MXServer.getMXServer().getProperty("mxe.db.password");

            Connection conn = DriverManager.getConnection(url, username, password);

            String sql = "select * from RLJLMXWH where jsdnum is null or  jsdnum ='' ";

            Statement stmt = conn.createStatement(1005, 1008);
            Statement stmt2 = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            service = Service.create(new URL(RCJLMXWEB_SERVICE),
                    new QName(NAMESPACE_URL, "RcjlmxWebService"));
            RcjlmxWebService rcService = service.getPort(RcjlmxWebService.class);
            while (rs.next()) {
                String jL = rs.getString("ysfs");
                Long jLType = Long.valueOf(0L);
                if ("火车".equals(jL)) {
                    jLType = Long.valueOf(265L);
                } else if ("汽车".equals(jL)) {
                    jLType = Long.valueOf(266L);
                }

                Long id = Long.valueOf(rs.getLong("RLJLMXWHID"));

                XMLGregorianCalendar jLDt2 = WsDateUtil.xmlToDate(rs.getTimestamp("kCCZSJ"));

                XMLGregorianCalendar dAteAttr1 = WsDateUtil.xmlToDate(rs.getTimestamp("ZCCZSJ"));

                BigDecimal jLSd = NumberFormatUtil.changeBig(Double.valueOf(rs.getDouble("GHSJ")));
                Long jLXh = Long.valueOf(rs.getLong("RLJLMXWHNUM"));
                String jLCch = rs.getString("CNUM");
                String jLFzm = rs.getString("FZ");
                String jLGys = rs.getString("GYS");
                String jLHwm = rs.getString("RLPZ");
                String jLShf = rs.getString("HWLX");
                String jLCx = rs.getString("CY");
                String vArAttr3 = rs.getString("KD");
                BigDecimal qTyMzFh = null;
                BigDecimal qTyZzFh =
                        NumberFormatUtil.changeBig(Double.valueOf(rs.getDouble("PPZ")));
                BigDecimal qTyJzFh = NumberFormatUtil.changeBig(Double.valueOf(rs.getDouble("jz")));
                BigDecimal qTyPz =
                        NumberFormatUtil.changeBig(Double.valueOf(rs.getDouble("PIAOZ")));
                BigDecimal qTyMz = NumberFormatUtil.changeBig(Double.valueOf(rs.getDouble("MZ")));
                BigDecimal qTyZz = NumberFormatUtil.changeBig(Double.valueOf(rs.getDouble("PZ")));
                BigDecimal qTyJz =
                        NumberFormatUtil.changeBig(Double.valueOf(rs.getDouble("jzjhk")));
                String jLJlr = rs.getString("createperson");
                String jLNote = "";
                String jLId = rs.getString("hid");
                String rEfId = "666666";

                String jLTimes = "1";
                String sPecType = "0";

                List results = rcService
                        .addRcjlmx2("WhZk_87538975", Long.valueOf(469L), "544", Long.valueOf(0L),
                                Long.valueOf(544L), jLType, jLDt2, jLSd, jLXh, jLCch, jLFzm, jLGys,
                                jLHwm, jLShf, jLCx, qTyMzFh, qTyZzFh, qTyJzFh, qTyPz, qTyMz, qTyZz,
                                qTyJz, jLJlr, jLNote, jLId, rEfId, null, null, jLTimes, sPecType,
                                "", "", "", vArAttr3, "", null, null, dAteAttr1, null);

                if ((results != null) && (!results.isEmpty())) {
                    if ("1".equals(results.get(0))) {
                        String changeStatus =
                                "update RLJLMXWH set jsdnum ='Y' where RLJLMXWHID=" + id;
                        stmt2.executeUpdate(changeStatus);
                    } else {
                        throw new IllegalArgumentException((String) results.get(1));
                    }
                }
            }

            rs.close();
            stmt.close();

            if (stmt2 != null) {
                stmt2.close();
            }

            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            MX_LOGGER.error(e);
        }
    }
}