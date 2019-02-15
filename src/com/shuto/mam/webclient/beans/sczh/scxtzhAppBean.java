package com.shuto.mam.webclient.beans.sczh;

import psdi.mbo.MboConstants;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class scxtzhAppBean extends AppBean {

    @Override
    protected void setCurrentRecordData(MboRemote mbo) throws MXException, RemoteException {

        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String status = mainMbo.getString("status");
        if ("已关闭".equals(status)) {
            mbo.setFlag(MboConstants.READONLY, true);
        }
        super.setCurrentRecordData(mbo);
    }

    public Connection getConn(String url, String username, String password) {

        Connection conn = null;
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }

    public void SISDATA() throws RemoteException, MXException {

        Connection connMid = null;
        Connection conn = null;
        String success = "数据同步失败";
        try {
            //将数据同步到中间库
            connMid = getConn("jdbc:oracle:thin:@10.42.5.227:1521/xhmid", "maximo", "maximo");
            String sqlMid1 =
                    "delete from SISPROLOG where  to_char(bfform_dtm,'yyyy-mm-dd') = to_char(sysdate-1,'yyyy-mm-dd')";
            String sqlMid2 = "insert into SISPROLOG(BFFORM_DTM,DESCRIPTION,ORGID,SITEID,\n" +
                    "TAB0_TEXT11,TAB0_TEXT12,TAB0_TEXT14,TAB0_TEXT15,TAB0_TEXT17,TAB0_TEXT18,TAB0_TEXT20,\n" +
                    "TAB0_TEXT21,TAB0_TEXT23,TAB0_TEXT24,TAB0_TEXT29,TAB0_TEXT30,TAB0_TEXT35,TAB0_TEXT36,\n" +
                    "TAB0_TEXT37,TAB0_TEXT38,TAB0_TEXT4,TAB0_TEXT43,TAB0_TEXT45,TAB0_TEXT6,TAB0_TEXT68,\n" +
                    "TAB0_TEXT69,TAB0_TEXT7,TAB0_TEXT70,TAB0_TEXT71,TAB0_TEXT72,TAB0_TEXT73,TAB0_TEXT76,\n" +
                    "TAB0_TEXT77,TAB0_TEXT78,TAB0_TEXT79,TAB0_TEXT80)\n" +
                    "select BFFORM_DTM,'SIS中间库','XHXM','XHDC',\n" +
                    "TAB0_TEXT11,TAB0_TEXT12,TAB0_TEXT14,TAB0_TEXT15,TAB0_TEXT17,TAB0_TEXT18,TAB0_TEXT20,\n" +
                    "TAB0_TEXT21,TAB0_TEXT23,TAB0_TEXT24,TAB0_TEXT29,TAB0_TEXT30,TAB0_TEXT35,TAB0_TEXT36,\n" +
                    "TAB0_TEXT37,TAB0_TEXT38,TAB0_TEXT4,TAB0_TEXT43,TAB0_TEXT45,TAB0_TEXT6,TAB0_TEXT68,\n" +
                    "TAB0_TEXT69,TAB0_TEXT7,TAB0_TEXT70,TAB0_TEXT71,TAB0_TEXT72,TAB0_TEXT73,TAB0_TEXT76,\n" +
                    "TAB0_TEXT77,TAB0_TEXT78,TAB0_TEXT79,TAB0_TEXT80\n" +
                    "from liems6xh.scrbmis1016@to_sis  where to_char(bfform_dtm,'yyyy-mm-dd') = to_char(sysdate-1,'yyyy-mm-dd')";
            connMid.createStatement().executeUpdate(sqlMid1);
            connMid.createStatement().executeUpdate(sqlMid2);

            //将数据同步到maximo
            conn = MXServer.getMXServer().getDBManager().getSequenceConnection();
            String sql =
                    "delete from SISPROLOG where  to_char(bfform_dtm,'yyyy-mm-dd') = to_char(sysdate-1,'yyyy-mm-dd')";
            String sql2 =
                    "insert into SISPROLOG(BFFORM_DTM,DESCRIPTION,ORGID,SITEID,Sisprologid,hasld,\n" +
                            "TAB0_TEXT11,TAB0_TEXT12,TAB0_TEXT14,TAB0_TEXT15,TAB0_TEXT17,TAB0_TEXT18,TAB0_TEXT20,\n" +
                            "TAB0_TEXT21,TAB0_TEXT23,TAB0_TEXT24,TAB0_TEXT29,TAB0_TEXT30,TAB0_TEXT35,TAB0_TEXT36,\n" +
                            "TAB0_TEXT37,TAB0_TEXT38,TAB0_TEXT4,TAB0_TEXT43,TAB0_TEXT45,TAB0_TEXT6,TAB0_TEXT68,\n" +
                            "TAB0_TEXT69,TAB0_TEXT7,TAB0_TEXT70,TAB0_TEXT71,TAB0_TEXT72,TAB0_TEXT73,TAB0_TEXT76,\n" +
                            "TAB0_TEXT77,TAB0_TEXT78,TAB0_TEXT79,TAB0_TEXT80)\n" +
                            "select BFFORM_DTM,'从中间库中取出数据','XHXM','XHDC',SISPROLOGIDSEQ.nextval,'0',\n" +
                            "TAB0_TEXT11,TAB0_TEXT12,TAB0_TEXT14,TAB0_TEXT15,TAB0_TEXT17,TAB0_TEXT18,TAB0_TEXT20,\n" +
                            "TAB0_TEXT21,TAB0_TEXT23,TAB0_TEXT24,TAB0_TEXT29,TAB0_TEXT30,TAB0_TEXT35,TAB0_TEXT36,\n" +
                            "TAB0_TEXT37,TAB0_TEXT38,TAB0_TEXT4,TAB0_TEXT43,TAB0_TEXT45,TAB0_TEXT6,TAB0_TEXT68,\n" +
                            "TAB0_TEXT69,TAB0_TEXT7,TAB0_TEXT70,TAB0_TEXT71,TAB0_TEXT72,TAB0_TEXT73,TAB0_TEXT76,\n" +
                            "TAB0_TEXT77,TAB0_TEXT78,TAB0_TEXT79,TAB0_TEXT80\n" +
                            "from SISPROLOg@To_Sis_Mid where to_char(bfform_dtm,'yyyy-mm-dd') = to_char(sysdate-1,'yyyy-mm-dd')";
            conn.createStatement().executeUpdate(sql);
            conn.createStatement().executeUpdate(sql2);
            connMid.close();
            conn.close();
            success = "数据同步成功";
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            throw new MXApplicationException("sis", "sismessage", new Object[]{success});
        }

    }

    public int SAVE() throws MXException, RemoteException {

        MboRemote mainMbo = this.app.getAppBean().getMbo();
        String SCRB_TEXT1 = mainMbo.getString("SCRB_TEXT1");
        if ("".equals(SCRB_TEXT1)) {
            SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");

            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, -1);
            // 获取到昨天的数据
            String oldDate = sf.format(c.getTime());
            // 查询
            MboSetRemote sczhMboSet = mainMbo.getMboSet("SISPROLOG", "SISPROLOG",
                    " BFFORM_DTM =to_date('" + oldDate + "' , 'yyyy-mm-dd') ");
            MboRemote sczhMbo = sczhMboSet.getMbo(0);

            // 最高发电负荷
            double tab0Text4 = sczhMbo.getDouble("TAB0_TEXT4");
            // 最大供热流量
            double tab0Text6 = sczhMbo.getDouble("TAB0_TEXT6");
            // 生产厂用电量
            double tab0Text7 = sczhMbo.getDouble("TAB0_TEXT7");
            // #1机组发电量
            double tab0Text11 = sczhMbo.getDouble("TAB0_TEXT11");
            // #2机组发电量
            double tab0Text12 = sczhMbo.getDouble("TAB0_TEXT12");
            // #1机组供电量
            double tab0Text14 = sczhMbo.getDouble("TAB0_TEXT14");
            // #2机组供电量
            double tab0Text15 = sczhMbo.getDouble("TAB0_TEXT15");
            // #1机组运行小时数
            double tab0Text17 = sczhMbo.getDouble("TAB0_TEXT17");
            // #2机组运行小时数
            double tab0Text18 = sczhMbo.getDouble("TAB0_TEXT18");
            // #1机组非计划停运小时
            double tab0Text20 = sczhMbo.getDouble("TAB0_TEXT20");
            // #2机组非计划停运小时
            double tab0Text21 = sczhMbo.getDouble("TAB0_TEXT21");
            // #1机组检修小时
            double tab0Text23 = sczhMbo.getDouble("TAB0_TEXT23");
            // #2机组检修小时
            double tab0Text24 = sczhMbo.getDouble("TAB0_TEXT24");
            // #1机组可调出力
            double tab0Text29 = sczhMbo.getDouble("TAB0_TEXT29");
            // #2机组可调出力
            double tab0Text30 = sczhMbo.getDouble("TAB0_TEXT30");
            // 供热量t
            double tab0Text35 = sczhMbo.getDouble("TAB0_TEXT35");
            // 供热量GJ
            double tab0Text36 = sczhMbo.getDouble("TAB0_TEXT36");
            // 供汽压力
            double tab0Text37 = sczhMbo.getDouble("TAB0_TEXT37");
            // 供汽温度
            double tab0Text38 = sczhMbo.getDouble("TAB0_TEXT38");
            // 高备变电量
            double tab0Text43 = sczhMbo.getDouble("TAB0_TEXT43");
            // #2机组天然气量
            double tab0Text45 = sczhMbo.getDouble("TAB0_TEXT45");
            // #1机组天然气量
            double tab0Text68 = sczhMbo.getDouble("TAB0_TEXT68");
            // 分输站低热值
            double tab0Text69 = sczhMbo.getDouble("TAB0_TEXT69");
            // 分输站高热值
            double tab0Text70 = sczhMbo.getDouble("TAB0_TEXT70");
            // 分输站密度
            double tab0Text71 = sczhMbo.getDouble("TAB0_TEXT71");
            // 分输站气量Nm3
            double tab0Text72 = sczhMbo.getDouble("TAB0_TEXT72");
            // 分输站气量GJ
            double tab0Text73 = sczhMbo.getDouble("TAB0_TEXT73");
            // 调压站低热值
            double tab0Text76 = sczhMbo.getDouble("TAB0_TEXT76");
            // 调压站高热值
            double tab0Text77 = sczhMbo.getDouble("TAB0_TEXT77");
            // 调压站密度
            double tab0Text78 = sczhMbo.getDouble("TAB0_TEXT78");
            // 调压站气量
            double tab0Text79 = sczhMbo.getDouble("TAB0_TEXT79");
            // 调压站气量
            double tab0Text80 = sczhMbo.getDouble("TAB0_TEXT80");

            mainMbo.setValue("SCRB_TEXT3", tab0Text4, 11L);
            mainMbo.setValue("SCRB_TEXT5", tab0Text6, 11L);
            mainMbo.setValue("SCRB_TEXT6", tab0Text7, 11L);
            mainMbo.setValue("SCRB_TEXT10", tab0Text11, 11L);
            mainMbo.setValue("SCRB_TEXT11", tab0Text12, 11L);
            mainMbo.setValue("SCRB_TEXT13", tab0Text14, 11L);
            mainMbo.setValue("SCRB_TEXT14", tab0Text15, 11L);
            mainMbo.setValue("SCRB_TEXT16", tab0Text17, 11L);
            mainMbo.setValue("SCRB_TEXT17", tab0Text18, 11L);
            mainMbo.setValue("SCRB_TEXT19", tab0Text20, 11L);
            mainMbo.setValue("SCRB_TEXT20", tab0Text21, 11L);
            mainMbo.setValue("SCRB_TEXT22", tab0Text23, 11L);
            mainMbo.setValue("SCRB_TEXT23", tab0Text24, 11L);
            mainMbo.setValue("SCRB_TEXT28", tab0Text29, 11L);
            mainMbo.setValue("SCRB_TEXT29", tab0Text30, 11L);
            mainMbo.setValue("SCRB_TEXT34", tab0Text35, 11L);
            mainMbo.setValue("SCRB_TEXT35", tab0Text36, 11L);
            mainMbo.setValue("SCRB_TEXT36", tab0Text37, 11L);
            mainMbo.setValue("SCRB_TEXT37", tab0Text38, 11L);
            mainMbo.setValue("SCRB_TEXT42", tab0Text43, 11L);
            mainMbo.setValue("SCRB_TEXT46", tab0Text45, 11L);
            mainMbo.setValue("SCRB_TEXT47", tab0Text68, 11L);
            mainMbo.setValue("SCRB_TEXT67", tab0Text69, 11L);
            mainMbo.setValue("SCRB_TEXT68", tab0Text70, 11L);
            mainMbo.setValue("SCRB_TEXT69", tab0Text71, 11L);
            mainMbo.setValue("SCRB_TEXT70", tab0Text72, 11L);
            mainMbo.setValue("SCRB_TEXT71", tab0Text73, 11L);
            mainMbo.setValue("SCRB_TEXT76", tab0Text76, 11L);
            mainMbo.setValue("SCRB_TEXT77", tab0Text77, 11L);
            mainMbo.setValue("SCRB_TEXT78", tab0Text78, 11L);
            mainMbo.setValue("SCRB_TEXT79", tab0Text79, 11L);
            mainMbo.setValue("SCRB_TEXT80", tab0Text80, 11L);
        }
        // 发电量

        double text1 = mainMbo.getDouble("SCRB_TEXT10");
        double text2 = mainMbo.getDouble("SCRB_TEXT11");
        mainMbo.setValue("SCRB_TEXT12", text2 + text1, 11L);
        // 供电量
        double text3 = mainMbo.getDouble("SCRB_TEXT13");
        double text4 = mainMbo.getDouble("SCRB_TEXT14");
        mainMbo.setValue("SCRB_TEXT15", text3 + text4, 11L);
        // 运行小时数
        double text5 = mainMbo.getDouble("SCRB_TEXT16");
        double text6 = mainMbo.getDouble("SCRB_TEXT17");
        mainMbo.setValue("SCRB_TEXT18", text5 + text6, 11L);

        // 天然气量
        double text17 = mainMbo.getDouble("SCRB_TEXT43");
        double text18 = mainMbo.getDouble("SCRB_TEXT44");
        mainMbo.setValue("SCRB_TEXT45", text17 + text18, 11L);

        // "D25
        double d25 = mainMbo.getDouble("SCRB_TEXT79");
        // 供电量合计 C9
        double c9 = mainMbo.getDouble("SCRB_TEXT15");
        // 发电量合计 B9
        double b9 = mainMbo.getDouble("SCRB_TEXT12");
        // 生产厂用电量(万kwh) F3
        double f3 = mainMbo.getDouble("SCRB_TEXT6");
        // #1非计划停运小时(h) E7
        double e7 = mainMbo.getDouble("SCRB_TEXT19");
        // #2非计划停运小时(h) E8
        double e8 = mainMbo.getDouble("SCRB_TEXT20");
        // 发电量合计 B7
        double b7 = mainMbo.getDouble("SCRB_TEXT10");
        // 发电量合计 B8
        double b8 = mainMbo.getDouble("SCRB_TEXT11");
        // #1运行小时数(h) D7
        double d7 = mainMbo.getDouble("SCRB_TEXT16");
        // #1检修小时(h)F7
        double f7 = mainMbo.getDouble("SCRB_TEXT22");
        // #2检修小时(h)F8
        double f8 = mainMbo.getDouble("SCRB_TEXT23");
        // #2运行小时数(h) D8
        double d8 = mainMbo.getDouble("SCRB_TEXT17");
        // 合计运行小时数(h) D8
        double d9 = d8 + d7;
        // 调压站低热值(MJ/M3) A25
        double a25 = mainMbo.getDouble("SCRB_TEXT76");
        // 调压站密度(Kg/Nm3) C25
        double c25 = mainMbo.getDouble("SCRB_TEXT78");
        // 供热量（GJ） B13
        double b13 = mainMbo.getDouble("SCRB_TEXT35");
        // 分输站气量(Nm3) D22
        double d22 = mainMbo.getDouble("SCRB_TEXT70");
        // 分输站低热值(MJ/m3) A22
        double a22 = mainMbo.getDouble("SCRB_TEXT67");
        // 分输站密度(Kg/Nm3)： C22
        double c22 = mainMbo.getDouble("SCRB_TEXT69");
        // #1供电量 C7
        double c7 = mainMbo.getDouble("SCRB_TEXT13");
        // #2供电量 C8
        double c8 = mainMbo.getDouble("SCRB_TEXT14");
        // #1机组天然气量（Nm3) B17
        double b17 = mainMbo.getDouble("SCRB_TEXT43");
        // #2机组天然气量（Nm3) B18
        double b18 = mainMbo.getDouble("SCRB_TEXT44");
        // 合计天然气量（Nm3) B19
        double b19 = mainMbo.getDouble("SCRB_TEXT45");
        // 供热厂用电量（万Kwh） E13
        double e13 = mainMbo.getDouble("SCRB_TEXT38");
        // //#1平均负荷MW I7==IF(D7=0,0,B7/D7*10) getDiv
        double i13 = mainMbo.getDouble("SCRB_TEXT42");

        mainMbo.setValue("SCRB_TEXT31", getDiv(b7, d7) * 10, 11L);

        // #2平均负荷MW I8===IF(D8=0,0,B8/D8*10)
        mainMbo.setValue("SCRB_TEXT32", getDiv(b8, d8) * 10, 11L);
        // 合计平均负荷MW I9=IF(D9=0,0,B9/D9*10)
        mainMbo.setValue("SCRB_TEXT33", getDiv(b9, d9) * 10, 11L);
        // 调压站发电气耗（Nm3/kWh）=D25/C9/10000
        mainMbo.setValue("SCRB_TEXT84", getDiv(d25, c9) / 10000, 11L);
        // 调压站供电气耗(Nm3/kWh）)"=D25/B9/10000
        mainMbo.setValue("SCRB_TEXT83", getDiv(d25, b9) / 10000, 11L);
        // 调压站折标煤（t）=D25*A25/29271.2
        mainMbo.setValue("SCRB_TEXT82", getDiv(d25, a25) / 29271.2, 11L);

        // 调压站气量(t) =D25*C25/1000

        mainMbo.setValue("SCRB_TEXT81", getDiv(d25, c25) / 1000, 11L);

        // 分输站供电气耗(Nm3/kWh）) =D22/C9/10000

        mainMbo.setValue("SCRB_TEXT75", getDiv(d22, c9) / 10000, 11L);

        // 分输站发电气耗（Nm3/kWh） =D22/B9/10000

        mainMbo.setValue("SCRB_TEXT74", getDiv(d22, b9) / 10000, 11L);

        // 分输站折标煤（t） =D22*A22/29271.2

        mainMbo.setValue("SCRB_TEXT73", getDiv(d22, a22) / 29271.2, 11L);
        // 分输站气量(t) =D22*C22/1000
        mainMbo.setValue("SCRB_TEXT72", getDiv(d22, c22) / 1000, 11L);

        // 分输站折标煤（t） G22
        double g22 = mainMbo.getDouble("SCRB_TEXT73");
        // 分输站气量(t) F22
        double f22 = mainMbo.getDouble("SCRB_TEXT72");

        // 一号机天然气量(t） C17 =B17/B19*F22
        double c17 = getDiv(b17, b19) * f22;
        mainMbo.setValue("SCRB_TEXT46", c17, 11L);

        // 二号机天然气量(t） C18 ==B18/B19*F22
        double c18 = getDiv(b18, b19) * f22;
        mainMbo.setValue("SCRB_TEXT47", c18, 11L);

        // #1气量折标（t） D17==B17/B19*G22
        double d17 = getDiv(b17, b19) * g22;
        mainMbo.setValue("SCRB_TEXT49", d17, 11L);

        // #2气量折标（t） D18==B18/B19*G22
        double d18 = getDiv(b18, b19) * g22;
        mainMbo.setValue("SCRB_TEXT49", d18, 11L);
        // #1发电气耗(g/kWh) E17=IF(B7=0,0,B17/B7/10000)
        mainMbo.setValue("SCRB_TEXT52", getDiv(b17, b7) / 10000, 11L);

        // #2发电气耗(g/kWh) E18=IF(B8=0,0,B18/B8/10000)

        mainMbo.setValue("SCRB_TEXT53", getDiv(b18, b8) / 10000, 11L);

        // #1发电气耗(g/kWh) F17 =IF(B7=0,0,C17/B7*100)
        mainMbo.setValue("SCRB_TEXT55", getDiv(c17, b7) * 100, 11L);

        // #2发电气耗(g/kWh) F18 =IF(B8=0,0,C18/B8*100)
        mainMbo.setValue("SCRB_TEXT56", getDiv(c18, b8) * 100, 11L);

        // #1供电气耗(g/kWh) G17=IF(C7=0,0,C17/C7*100)
        mainMbo.setValue("SCRB_TEXT58", getDiv(c17, c7) * 100, 11L);

        // #2供电气耗(g/kWh) G18=IF(C8=0,0,C18/C8*100)

        mainMbo.setValue("SCRB_TEXT59", getDiv(c18, c8) * 100, 11L);

        // #1发电标准煤耗(g/kWh) H17 =IF(B7=0,0,D17/B7*100)
        mainMbo.setValue("SCRB_TEXT61", getDiv(d17, b7) * 100, 11L);

        // #2发电标准煤耗(g/kWh) H18 =IF(B8=0,0,D18/B8*100)

        mainMbo.setValue("SCRB_TEXT62", getDiv(d18, b8) * 100, 11L);

        // #1机 供电标准煤耗(g/kWh) I17 =IF(C7=0,0,D17/C7*100)
        mainMbo.setValue("SCRB_TEXT64", getDiv(d17, c7) * 100, 11L);

        // #2机 供电标准煤耗(g/kWh) I17 ==IF(C8=0,0,D18/C8*100)

        mainMbo.setValue("SCRB_TEXT65", getDiv(d18, c8) * 100, 11L);
        // 供热厂用电率 F13 ==IF(B13=0,0,E13*36/B13)

        mainMbo.setValue("SCRB_TEXT39", getDiv(e13 * 3, b13), 11L);

        // #1备用小时(h) G7=24-E7-F7-D7

        mainMbo.setValue("SCRB_TEXT25", 24 - e7 - f7 - d7, 11L);
        // #2备用小时(h) G8==24-D8-E8-F8

        mainMbo.setValue("SCRB_TEXT26", 24 - d8 - e8 - f8, 11L);
        // I3 供热厂用电率 I3==(F3-E13)/B9
        mainMbo.setValue("SCRB_TEXT9", getDiv(f3 - e13, b9), 11L);

        // 发电厂用电率 H2 ==(F3-I13)/B9

        mainMbo.setValue("SCRB_TEXT8", getDiv(f3 - i13, b9), 11L);

        // 生产厂用电率G3 ===IF((B9+B13/36)=0,0,F3/(B9+B13/36))

        mainMbo.setValue("SCRB_TEXT7", getDiv(f3, (b9 + b13) / 36), 11L);

        // 全厂负荷率 A3==I9/453*100%
        double i9 = mainMbo.getDouble("SCRB_TEXT33");
        mainMbo.setValue("SCRB_TEXT1", i9 / 453 * 100, 11L);

        // 非计划停运小时
        double text7 = mainMbo.getDouble("SCRB_TEXT19");
        double text8 = mainMbo.getDouble("SCRB_TEXT20");
        mainMbo.setValue("SCRB_TEXT21", text7 + text8, 11L);
        // 检修小时
        double text9 = mainMbo.getDouble("SCRB_TEXT22");
        double text10 = mainMbo.getDouble("SCRB_TEXT23");
        mainMbo.setValue("SCRB_TEXT24", text9 + text10, 11L);
        // 备用小时
        double text11 = mainMbo.getDouble("SCRB_TEXT25");
        double text12 = mainMbo.getDouble("SCRB_TEXT26");
        mainMbo.setValue("SCRB_TEXT27", text11 + text12, 11L);
        // 可调出力
        double text13 = mainMbo.getDouble("SCRB_TEXT28");
        double text14 = mainMbo.getDouble("SCRB_TEXT29");
        mainMbo.setValue("SCRB_TEXT30", text13 + text14, 11L);
        // 平均负荷
        double text15 = mainMbo.getDouble("SCRB_TEXT31");
        double text16 = mainMbo.getDouble("SCRB_TEXT32");
        mainMbo.setValue("SCRB_TEXT2", text15 + text16, 11L);
        // 天然气量（T）
        double text19 = mainMbo.getDouble("SCRB_TEXT46");
        double text20 = mainMbo.getDouble("SCRB_TEXT47");
        mainMbo.setValue("SCRB_TEXT48", text19 + text20, 11L);
        // 气量折标
        double text21 = mainMbo.getDouble("SCRB_TEXT49");
        double text22 = mainMbo.getDouble("SCRB_TEXT50");
        mainMbo.setValue("SCRB_TEXT51", text21 + text22, 11L);
        // 发电气耗Nm3/kWh）
        double text23 = mainMbo.getDouble("SCRB_TEXT52");
        double text24 = mainMbo.getDouble("SCRB_TEXT53");
        mainMbo.setValue("SCRB_TEXT54", text23 + text24, 11L);
        // 发电气耗
        double text25 = mainMbo.getDouble("SCRB_TEXT55");
        double text26 = mainMbo.getDouble("SCRB_TEXT56");
        mainMbo.setValue("SCRB_TEXT57", text25 + text26, 11L);
        // 供电气耗
        double text27 = mainMbo.getDouble("SCRB_TEXT59");
        double text28 = mainMbo.getDouble("SCRB_TEXT60");
        mainMbo.setValue("SCRB_TEXT61", text27 + text28, 11L);
        // 发电标准煤耗
        double text29 = mainMbo.getDouble("SCRB_TEXT61");
        double text30 = mainMbo.getDouble("SCRB_TEXT62");
        mainMbo.setValue("SCRB_TEXT63", text29 + text30, 11L);
        // 供电标准煤耗
        double text31 = mainMbo.getDouble("SCRB_TEXT64");
        double text32 = mainMbo.getDouble("SCRB_TEXT65");
        mainMbo.setValue("SCRB_TEXT66", text31 + text32, 11L);

        return super.SAVE();
    }

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
                String remark = reportMbo.getString("REMARK"); // 备注
                if (!"".equals(dialogID)) {
                    this.clientSession.setAttribute("rqreportname", rqreportname);
                    this.clientSession.setAttribute("rqreportnum", rqreportnum);
                    this.clientSession.setAttribute("remark", remark);
                    this.clientSession.loadDialog(dialogID);
                    return 0;
                }
                StringBuffer url =
                        new StringBuffer(MXServer.getMXServer().getProperty("mxe.runqian.url"));
                url.append("/" + rqreportname.toLowerCase() + ".rpx");
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
        } catch (MXException e) {
        }
        return 1;
    }

    // 规避除数为0
    private double getDiv(double a, double b) {

        if (0.0D == b) {
            return 0;
        }
        return a / b;
    }

    @Override
    public int toggledeleterow() throws MXException {

        return super.toggledeleterow();
    }
}
