/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.operation.oplog;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.security.ConnectionKey;
import psdi.server.MXServer;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class OpLog extends Mbo implements OpLogRemote {

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    public OpLog(MboSet ms) throws RemoteException {

        super(ms);
    }

    @Override
    public void init() throws MXException {

        super.init();

        String status = getMboValue("status").getString();
        MboSetRemote appSet = getThisMboSet();
        try {
            String appname = appSet.getApp();

            if (!"OPLOG".equalsIgnoreCase(appname)) {
                return;
            }
            if ("CLOSE".equalsIgnoreCase(status)) {
                setFlag(7L, true);
                return;
            }
            MboRemote oplog = getMboValue("status").getMbo();
            setReadOnly(oplog);
        } catch (RemoteException e) {
            MX_LOGGER.error(e);
        }
    }

    public MboRemote getOnDutyPerson() throws RemoteException, MXException {

        String currentPersonId = getString("dbrperson");

        MboSetRemote currentPerson =
                getMboSet("$tmp_personcal", "person", "personid = '" + currentPersonId + "'");
        MboRemote personmbo = currentPerson.getMbo(0);
        currentPerson.close();

        return personmbo;
    }

    public MboRemote nextPeriod() throws RemoteException, MXException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        MX_LOGGER.info("==============接班、验证倒班表开始===");
        Date currentDate = getDate("zqdate");// 工作日期

        String sql = "(workdate = to_date('" + sdf.format(currentDate) + "','yyyy-MM-dd')";
        Calendar cal = Calendar.getInstance();
        cal.setTime(currentDate);
        cal.add(5, 1);
        String jiebanid = getLoginPerson().getString("personid");// 接班人personid

        String oplogtype = getString("oplogtype");// 日志类型
        // 运行日志配置人员Set
        MboSetRemote jiebanset = getMboSet("$oplogpersonnextperiod", "oplogperson",
                "personid='" + jiebanid + "'  and  oplogtype ='" + oplogtype + "'");
        MX_LOGGER.info("==============当前登录人是否在运行日志配置人员中，1是，0不是==" + jiebanset.count());
        // 运行日志配置人员表的日历
        String jiebanprimarycalnum = jiebanset.getMbo(0).getString("primarycalnum");
        MX_LOGGER.info("==============运行日志配置中人员的日历===" + jiebanprimarycalnum);
        sql = sql + "or workdate = to_date('" + sdf.format(cal.getTime()) + "','yyyy-MM-dd'))";
        MX_LOGGER.info("==============nextPeriod()7===sql==" + sql);
        sql = sql + "and calnum = '" + jiebanprimarycalnum + "' order by workdate,starttime";
        MX_LOGGER.info("接班、查询倒班表==select * from workperiod where " + sql);
        MboSetRemote nextperiods = getMboSet("$tmp_workperiod", "workperiod", sql);

        String zbstartdate = getDate("zbstartdate").toString().substring(11);
        MX_LOGGER.info("==============zbstartdate==" + zbstartdate);
        String currentDateStr = sdf.format(currentDate);// 工作日期格式化
        String nextStart = null;
        String workdate = null;
        MboRemote next = null;
        MX_LOGGER.info("==============倒班表中一天的班次数量==" + nextperiods.count());
        for (int i = 0; i < nextperiods.count(); ++i) {
            nextStart = nextperiods.getMbo(i).getDate("starttime").toString().substring(11);
            workdate = sdf.format(nextperiods.getMbo(i).getDate("workdate"));
            MX_LOGGER.info("==============workdate===" + workdate);
            MX_LOGGER.info("==============页面上工作日期：currentDateStr===" + currentDateStr);
            if (!workdate.equals(currentDateStr)) {
                MX_LOGGER.info("==============接班时间与倒班表不在一个次元===");// 倒班表配置有误
                continue;
            }
            MX_LOGGER.info("==============nextStart要与zbstartdate相等才行！！！nextStart==" + nextStart);
            MX_LOGGER
                    .info("==============nextStart要与zbstartdate相等才行！！！zbstartdate==" + zbstartdate);
            if (!nextStart.equals(zbstartdate)) {
                continue;
            }
            // 获取下一个班次，如果下一个班次为空这
            next = nextperiods.getMbo(i + 1);
            if (next == null) {
                MX_LOGGER.info("==============接班、验证倒班表失败===");
            } else {
                MX_LOGGER.info("==============接班、验证倒班表成功===");
            }
            return next;

        }

        jiebanset.close();
        nextperiods.close();
        return next;
    }

    public HashMap<String, String> nextPeriodMap() throws RemoteException, MXException {

        HashMap<String, String> workperoid = new HashMap<>(16);
        /**
         * yyyy-MM-dd
         */
        SimpleDateFormat sdfYMD = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdfHMS = new SimpleDateFormat("HH:mm:ss");
        Date zqdate = getDate("zqdate");// 工作日期

        Calendar cal = Calendar.getInstance();
        cal.setTime(zqdate);
        cal.add(Calendar.DAY_OF_MONTH, 1);// 下一天
        String jbpersonid = getLoginPerson().getString("personid");// 接班人personid

        String oplogtype = getString("oplogtype");// 日志类型

        // 运行日志配置人员Set
        MboSetRemote jbpersonSet = getMboSet("$oplogpersonnextperiod", "oplogperson",
                "personid='" + jbpersonid + "'  and  oplogtype ='" + oplogtype + "'");

        MX_LOGGER.info("==============当前登录人是否在运行日志配置人员中，1是，0不是==" + jbpersonSet.count());

        // 运行日志配置人员表的日历
        String jiebanprimarycalnum = jbpersonSet.getMbo(0).getString("primarycalnum");
        String siteid = jbpersonSet.getMbo(0).getString("siteid");

        MX_LOGGER.info("==============primarycalnum==" + jiebanprimarycalnum);
        MX_LOGGER.info("==============siteid==" + siteid);

        workperoid.put("siteid", siteid);// 地点

        // //运行倒班表
        // MboSetRemote calendar = getMboSet("$calendar",
        // "calendar", "siteid='" + siteid + "' and CALNUM ='" +
        // jiebanprimarycalnum + "'");

        String sql = "(workdate = to_date('" + sdfYMD.format(zqdate) +
                "','yyyy-MM-dd') or workdate = to_date('" + sdfYMD.format(cal.getTime()) +
                "','yyyy-MM-dd')) and calnum = '" + jiebanprimarycalnum +
                "' order by workdate,starttime asc";

        MboSetRemote nextperiods = getMboSet("$tmp_workperiod", "workperiod", sql);

        MX_LOGGER.info("==============倒班表中查询出的班次数量==" + nextperiods.count());
        // String zbstartdate = getDate("zbstartdate").toString().substring(11);

        String zbstartdate = sdfHMS.format(getDate("ZBSTARTDATE"));// 班次结束时间格式化
        String currentDateStr = sdfYMD.format(zqdate);// 工作日期格式化
        String nextStart;
        String nextEnd;

        for (int i = 0; nextperiods.getMbo(i) != null; ++i) {
            nextStart = sdfHMS.format(nextperiods.getMbo(i).getDate("starttime"));// 班次开始时间

            MX_LOGGER.info("==============页面上工作日期：currentDateStr===" + currentDateStr);

            // 下一班次开始时间要与现在班次结束时间相等才接班
            if (!nextStart.equals(zbstartdate)) {
                continue;
            }

            String newNextStart =
                    sdfHMS.format(nextperiods.getMbo(i + 1).getDate("starttime"));// 下一班次开始时间
            Date workdate = nextperiods.getMbo(i + 1).getDate("WORKDATE");
            String shiftnum = nextperiods.getMbo(i + 1).getString("SHIFTNUM");
            nextEnd = sdfHMS.format(nextperiods.getMbo(i + 1).getDate("endtime"));// 班次结束时间

            MX_LOGGER.info("==============下一工作日期：workdate===" + workdate.toString());
            MX_LOGGER.info("==============下一工作shiftnum===" + shiftnum);
            MX_LOGGER.info("==============下一工作开始时间：nextStart===" + newNextStart);
            MX_LOGGER.info("==============下一工作结束时间：nextEnd===" + nextEnd);

            workperoid.put("starttime", newNextStart);// 班次开始时间
            workperoid.put("endtime", nextEnd);// 班次结束时间
            workperoid.put("shiftnum", shiftnum);// 班次
            workperoid.put("workdate", sdfYMD.format(workdate));// 当班日期
            break;

        }

        jbpersonSet.close();
        nextperiods.close();

        return workperoid;
    }

    public MboRemote getShift(MboRemote period) throws RemoteException, MXException {

        SimpleDateFormat hourFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        MboSetRemote shifts = getMboSet("$temp_shift", "shift",
                "to_char(starttime,'yyyy-MM-dd hh24:mi:ss')='" +
                        hourFormat.format(period.getDate("starttime")) + "' " +
                        "and to_char(endtime,'yyyy-MM-dd hh24:mi:ss')='" +
                        hourFormat.format(period.getDate("endtime")) + "' and orgid='" +
                        period.getString("orgid") + "'");
        MboRemote shiftsMbo = shifts.getMbo(0);
        if ((shifts == null) || (shifts.count() == 0)) {
            shifts.close();
            throw new MXApplicationException("oplog", "opogmhsbc");
        }

        shifts.close();
        return shiftsMbo;
    }

    /**
     * 获取下一个班次 改进方法 根据站点过滤
     *
     * @return
     * @throws RemoteException
     * @throws MXException
     */
    public MboRemote getShift(HashMap<String, String> period) throws RemoteException, MXException {

        String starttime = period.get("starttime");
        String endtime = period.get("endtime");
        String siteid = period.get("siteid");

        MboSetRemote shifts = getMboSet("$temp_shift", "shift",
                "to_char(starttime,'hh24:mi:ss')='" + starttime + "'" +
                        " and to_char(endtime,'hh24:mi:ss')='" + endtime + "'" + " and siteid='" +
                        siteid + "'");
        if ((shifts == null) || (shifts.count() == 0)) {
            if (shifts != null) {
                shifts.close();
            }
            throw new MXApplicationException("oplog", "opogmhsbc");
        }

        MboRemote shiftsMbo = shifts.getMbo(0);
        shifts.close();
        return shiftsMbo;
    }

    public MboRemote getLoginPerson() throws RemoteException, MXException {

        String currentUserId = getUserInfo().getPersonId();
        return getMboSet("$tmp_current_person", "person", "personid='" + currentUserId + "'")
                .getMbo(0);
    }

    // public void initAssetStatus(MboRemote oplogmbo) throws RemoteException,
    public void initAssetStatus() throws RemoteException, MXException {
        // 获取日志类型
        String oplogtype = getString("oplogtype");
        // 获取日志编号
        String oplognum = getString("oplognum");
        if ((oplogtype == null) || (oplogtype.equals(""))) {
            throw new NullPointerException("日志类型为空");
        }
        String siteid = getString("siteid");

        MboSetRemote oplogcfgSet = getMboSet("$temp_optypeinitassetstatus", "oplogcfg",
                "oplogtype='" + oplogtype + "' and siteid = '" + siteid + "'");
        if (oplogcfgSet.isEmpty()) {
            oplogcfgSet.close();
            MX_LOGGER.info("日志类型的确不存在");
            throw new NullPointerException("日志类型不存在");
        }

        MboRemote typeRemote = oplogcfgSet.getMbo(0);// oplogcfg运行日志配置
        String oplogcfgnum = typeRemote.getString("oplogcfgnum");
        oplogcfgSet.close();

        MboSetRemote oplogcfgAseetSet = getMboSet("$temp_oplogcfgassetasset", "oplogcfgasset",
                "oplogcfgnum = '" + oplogcfgnum + "' and siteid = '" + siteid + "'");

        MboSetRemote oplogassetinfos = getMboSet("oplognum_oplogassetinfo");
        oplogassetinfos.deleteAll();

        if (!oplogcfgAseetSet.isEmpty()) {
            MboRemote oplogmboAsset = null;
            MboRemote oplogcfgAseet;

            for (int j = 0; j < oplogcfgAseetSet.count(); j++) {
                oplogmboAsset = oplogassetinfos.add();
                oplogcfgAseet = oplogcfgAseetSet.getMbo(j);
                oplogmboAsset.setValue("name", oplogcfgAseet.getString("description"), 11l);
                oplogmboAsset.setValue("ordernum", oplogcfgAseet.getString("ordernum"), 11l);

                oplogmboAsset
                        .setValue("oplogcfgassetnum", oplogcfgAseet.getString("oplogcfgassetnum"),
                                11l);

                oplogmboAsset.setValue("assetnum", oplogcfgAseet.getString("assetnum"), 11l);

                oplogmboAsset.setValue("location", oplogcfgAseet.getString("location"), 11l);

                oplogmboAsset.setValue("oplognum", oplognum, 11l);

                oplogmboAsset.setValue("unit", oplogcfgAseet.getString("unit"), 11l);

                oplogmboAsset.setValue("type", oplogcfgAseet.getString("cfgtype"), 11l);

                oplogmboAsset.setValue("ASSETSID", oplogcfgAseet.getString("ASSETSID"), 11l);// 唯一标识
                MX_LOGGER.info(j + "ASSETSID==" + oplogcfgAseet.getString("ASSETSID"));

            }

        }
        oplogassetinfos.close();
        oplogcfgAseetSet.close();
    }

    /*
     * 设备工况和重要参数初始化（运行日志维护-新建行和运行日志-接班共用）
     *
     **/
    public void assetAndPoint(String oplogtype, String newOplogNum, String designperson,
            String siteid, String orgid) {

        long startTime = System.currentTimeMillis(); // 获取开始时间
        Connection connection = null;
        ConnectionKey key;
        PreparedStatement sm1 = null;
        SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            key = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
            connection = MXServer.getMXServer().getDBManager().getConnection(key);
            connection.setAutoCommit(false);
            String sqlIsertSbgkAndZycs = "";// 设备工况和重要参数初始化SQL
            Date sysdate = new Date();
            String sysdateFormat = tdate.format(sysdate);
            sqlIsertSbgkAndZycs = "insert into OPLOGASSETINFO(" +
                    "OPLOGASSETINFOID,  SITEID, ORGID, NAME, HASLD,  OPLOGTYPE, ORDERNUM, OPLOGNUM, OPLOGCFGASSETNUM, COMMITNEXT, " +
                    "MEASUREMENTVALUE, UNIT, OPLOGASSETINFONUM, POINTNUM, RUN, BACKUP, OVERHAUL,   TYPE, DESIGNPERSON, " +
                    "DESIGNDATE, JDDATE, JDPERSON, ASSETNUM, LOCATION, FAULT, CDSJ,ASSETSID) select " +
                    "Oplogassetinfoidseq.Nextval,  SITEID, ORGID, description, HASLD,'" +
                    oplogtype + "', ORDERNUM, '" + newOplogNum + "',OPLOGCFGASSETNUM, 1, " +
                    "'', UNIT, Oplogassetinfoidseq.Nextval, POINTNUM, 1, 0, 0,   CFGTYPE, '" +
                    designperson + "'," + " to_date('" + sysdateFormat +
                    "','yyyy-mm-dd hh24:mi:ss'), to_date('" + sysdateFormat +
                    "','yyyy-mm-dd hh24:mi:ss'), '" + designperson +
                    "', ASSETNUM, LOCATION, 0, CDSJ,ASSETSID" +
                    " from oplogcfgasset where OPLOGCFGNUM=(SELECT OPLOGCFGNUM FROM OPLOGCFG WHERE OPLOGTYPE='" +
                    oplogtype + "' AND SITEID ='" + siteid + "' AND ORGID='" + orgid + "')";
            MX_LOGGER.info("===设备工况和重要参数初始化SQL==");
            MX_LOGGER.info(sqlIsertSbgkAndZycs);
            sm1 = connection.prepareStatement(sqlIsertSbgkAndZycs);
            sm1.executeUpdate();
            connection.commit();
        } catch (Exception e) {
            MX_LOGGER.error(e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                MX_LOGGER.error(e1);
            }
        } finally {
            try {
                if (sm1 != null) {
                    sm1.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                MX_LOGGER.error(e);
            }

        }
        long endTime = System.currentTimeMillis(); // 获取结束时间
        MX_LOGGER.info("设备工况、重要参数初始化程序运行时间： " + (endTime - startTime) + "ms");
    }

    /*
     * 设备工况、重要参数、交待事项（是否带入下一班） *
     */
    public void copyAssetAndPointToNewOplog(MboRemote oldOplog, MboRemote newOplog) {

        long startTime = System.currentTimeMillis(); // 获取开始时间

        Connection connection = null;
        ConnectionKey key;
        PreparedStatement sm2 = null;
        PreparedStatement sm3 = null;
        SimpleDateFormat tdate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            key = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
            connection = MXServer.getMXServer().getDBManager().getConnection(key);
            connection.setAutoCommit(false);
            String newOplogNum = newOplog.getString("OPLOGNUM");
            String designperson = newOplog.getString("DBRPERSON");
            String oplogtype = newOplog.getString("OPLOGTYPE");
            String siteid = newOplog.getString("SITEID");
            String orgid = newOplog.getString("ORGID");
            String zhibie = newOplog.getString("zhibie");

            Date sysdate = new Date();
            String sysdateFormat = tdate.format(sysdate);
            String oplogNum = oldOplog.getString("OPLOGNUM");
            String sqlUpdateSbgkAndZycs = "";// 设备工况和重要参数赋值SQL
            String sql1;
            assetAndPoint(oplogtype, newOplogNum, designperson, siteid, orgid);

            // 设备工况和重要参数赋值（带入下一班）
            sqlUpdateSbgkAndZycs =
                    "update oplogassetinfo a set(a.measurementvalue,a.remark,a.UNIT,a.RUN,a.BACKUP,a.OVERHAUL,a.FAULT) " +
                            "=(select b.measurementvalue,b.remark,b.UNIT,b.RUN,b.BACKUP,b.OVERHAUL,b.FAULT from oplogassetinfo b " +
                            "where b.oplognum = '" + oplogNum +
                            "' and b.commitnext=1 and a.ASSETSID=b.ASSETSID and a.oplognum = '" +
                            newOplogNum +
                            "' and a.siteid=b.siteid and a.orgid=b.orgid and a.siteid='" + siteid +
                            "'" + " and a.orgid='" + orgid +
                            "'  and a.ASSETSID is not null )  where exists (select 'X' from oplogassetinfo b where b.oplognum = '" +
                            oplogNum +
                            "' and b.commitnext=1 and  a.ASSETSID=b.ASSETSID and a.oplognum = '" +
                            newOplogNum + "' " +
                            " AND a.siteid=b.siteid and a.orgid=b.orgid and a.siteid='" + siteid +
                            "'" + " and a.orgid='" + orgid + "'  and a.ASSETSID is not null )";
            MX_LOGGER.info("===设备工况和重要参数赋值SQL==");
            MX_LOGGER.info(sqlUpdateSbgkAndZycs);
            sm2 = connection.prepareStatement(sqlUpdateSbgkAndZycs);
            sm2.executeUpdate();
            connection.commit();
            // 交待事项带入下一班
            sql1 =
                    "insert into OPLOGNOTE(OPLOGNOTEID, DESCRIPTION, SITEID, ORGID, CONTENT, HASLD, TYPE, OPLOGNUM, HAPPENDATE, WRITEDATE, " +
                            "WRITEPERSON, OPLOGTYPE, OPLOGNOTENUM, ZXPERSON, PROFESSION, ZHIBIE, YNZY, WRITEDATE2, YNSBYC, NOTETIME,COMMITNEXT,Style)" +
                            "select OPLOGNOTEIDSEQ.NEXTVAL, DESCRIPTION, SITEID, ORGID, CONTENT, HASLD, TYPE, '" +
                            newOplogNum + "', " + "to_date('" + sysdateFormat +
                            "','yyyy-mm-dd hh24:mi:ss'), WRITEDATE, '" + designperson +
                            "', OPLOGTYPE, OPLOGNOTEIDSEQ.NEXTVAL, ZXPERSON, " + "PROFESSION, '" +
                            zhibie + "', YNZY, WRITEDATE2, YNSBYC, NOTETIME,COMMITNEXT,STYLE " +
                            "from OPLOGNOTE" + " where OPLOGNUM ='" + oplogNum +
                            "' and COMMITNEXT=1";
            MX_LOGGER.info("===交待事项SQL1==");
            MX_LOGGER.info(sql1);
            sm3 = connection.prepareStatement(sql1);
            sm3.executeUpdate();
            connection.commit();

        } catch (Exception e) {
            MX_LOGGER.error(e);
            try {
                if (connection != null) {
                    connection.rollback();
                }
            } catch (SQLException e1) {
                MX_LOGGER.error(e1);
            }
        } finally {
            try {
                if (sm2 != null) {
                    sm2.close();
                }
                if (sm3 != null) {
                    sm3.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                MX_LOGGER.error(e);
            }

        }
        long endTime = System.currentTimeMillis(); // 获取结束时间
        MX_LOGGER.info("设备工况、重要参数、交待事项程序运行时间： " + (endTime - startTime) + "ms");

    }

    /**
     * 判断能否接班
     */
    public boolean canCarryon(MboRemote person) throws IOException, MXException {

        String loginid2 = person.getString("personid");

        String oplogtype = getString("oplogtype");
        String siteid = getString("siteid");
        MboSetRemote msrPersoncal = getMboSet("$oplogpersonpmsrPersoncal", "oplogperson",
                "personid = '" + loginid2 + "' and oplogtype = '" + oplogtype + "' and siteid = '" +
                        siteid + "'");

        if (!checkStatus("HASSHIFT")) {
            // 操作失败！只有已交班状态下，才能进行接班操作。
            throw new MXApplicationException("oplog", "oplogdbstatuscnjb");
        }

        if (msrPersoncal.isEmpty()) {
            throw new MXApplicationException("oplog", "oplogjbpersonno");// 交班人不在交接班人员组中
        }

        //		if (!isSameCalnum(person)) {
        //			throw new MXApplicationException("oplog", "oplognojbqx");// 操作失败！您没有接班权限，与当班人使用的不是同一日历
        //		}

        msrPersoncal.close();
        return true;
    }

    public boolean checkStatus(String status) throws RemoteException, MXException {

        return getString("status").equalsIgnoreCase(status);
    }

    //	private boolean isSameCalnum(MboRemote person) throws RemoteException, MXException {
    //		boolean flag =true;
    //		MboRemote currentPerson = getOnDutyPerson();
    //		if ((person == null) || (currentPerson == null)) {
    //			throw new MXApplicationException("oplog", "noAuthority");
    //		}
    //
    //		String oplogtype = getString("oplogtype");
    //
    //		String jiebanid = person.getString("personid");
    //		String siteid = getString("siteid");
    //		String jiaobanid = currentPerson.getString("personid");
    //
    //		MboSetRemote jiebanset = getMboSet("$oplogpersonjiebanset", "oplogperson",
    //				"personid='" + jiebanid + "'  and  oplogtype ='" + oplogtype + "' and siteid = '" + siteid + "'");
    //
    //		MboSetRemote jiaobanset = getMboSet("$oplogpersonjiaobanset", "oplogperson",
    //				"personid='" + jiaobanid + "'  and  oplogtype = '" + oplogtype + "'  and siteid = '" + siteid + "'");
    //		if (jiaobanset.getMbo(0) == null) {
    //			 flag =false;
    //			throw new MXApplicationException("交接班失败", "交班人不在交接班人员组中");
    //		}
    //		if (jiebanset.getMbo(0) == null) {
    //			flag =false;
    //			throw new MXApplicationException("交接班失败", "接班人不在交接班人员组中");
    //		}
    //
    ////		String oplogtypejieban = jiebanset.getMbo(0).getString("primarycalnum");
    ////
    ////		String oplogtypejiaoban = jiaobanset.getMbo(0).getString("primarycalnum");
    ////
    ////		if (("".equals(oplogtypejieban)) || (oplogtypejieban == null)) {
    ////			throw new MXApplicationException("oplog", "nocalendar");
    ////		}
    ////
    ////		if (("".equals(oplogtypejiaoban)) || (oplogtypejiaoban == null)) {
    ////			throw new MXApplicationException("oplog", "nocalendar");
    ////		}
    //
    //		jiebanset.close();
    //		jiaobanset.close();
    ////		return oplogtypejieban.equalsIgnoreCase(oplogtypejiaoban);
    //		return flag;
    //	}

    private boolean isSamePersonGroup(MboRemote person) throws RemoteException, MXException {

        String oplogtype = getString("oplogtype");
        String jiebanid = person.getString("personid");
        String siteid = getString("siteid");
        MboSetRemote jiebanset = getMboSet("$oplogpersonjiebansetnew", "oplogperson",
                "personid = '" + jiebanid + "' and oplogtype = '" + oplogtype + "' and siteid = '" +
                        siteid + "'");
        MboRemote jiebanmbo = jiebanset.getMbo(0);

        if ((jiebanmbo == null) || (jiebanset.count() == 0)) {
            jiebanset.close();
            return false;
        }
        jiebanset.close();
        return true;
    }

    // 出勤人员带入下一班（接班时调用）
    public boolean getNewPersonGroup(MboRemote newoplog) throws RemoteException, MXException {

        String oplogtype = getString("oplogtype");
        String siteid = getString("siteid");
        MboSetRemote newpersongroupSet = getMboSet("$temp_oplogpersonnewpersongroup", "oplogperson",
                "oplogtype = '" + oplogtype + "'  and siteid ='" + siteid + "'");

        MboRemote person = null;

        MboRemote attd = null;
        if (newpersongroupSet.count() > 0) {
            getMboSet("oplognum_oplogpersoncq").setFlag(7L, false);
            for (int i = 0; i < newpersongroupSet.count(); ++i) {
                person = newpersongroupSet.getMbo(i);
                attd = getMboSet("oplognum_oplogpersoncq").add();
                attd.setValue("personid", person.getString("personid"));

                attd.setValue("post", person.getString("post"));
                attd.setValue("oplognum", newoplog.getString("OPLOGNUM"));// 这里一定要插入新OPLOGNUM
                attd.setValue("CQSTATUS", "出勤");
            }
            newpersongroupSet.close();
            return true;
        }

        newpersongroupSet.close();
        return false;
    }

    // 出勤人员带入下一班（运行日志维护时调用）
    public boolean getNewPersonGroup() throws RemoteException, MXException {

        MX_LOGGER.info(0);

        String oplogtype = getString("oplogtype");
        String siteid = getString("siteid");
        MX_LOGGER.info(oplogtype + "===" + siteid);
        MboSetRemote newpersongroupSet = getMboSet("$temp_oplogpersonnewpersongroup", "oplogperson",
                "oplogtype = '" + oplogtype + "'  and siteid ='" + siteid + "'");

        MboRemote person = null;

        MboRemote attd = null;
        if (newpersongroupSet.count() > 0) {
            getMboSet("oplognum_oplogpersoncq").setFlag(7L, false);
            for (int i = 0; i < newpersongroupSet.count(); ++i) {
                person = newpersongroupSet.getMbo(i);
                attd = getMboSet("oplognum_oplogpersoncq").add(11L);
                attd.setValue("PERSONID", person.getString("PERSONID"), 11L);
                MX_LOGGER.info("出勤人员写入运行日志！" + i + 1);
                attd.setValue("POST", person.getString("POST"), 11L);
                attd.setValue("OPLOGNUM", getString("OPLOGNUM"), 11L);
                attd.setValue("CQSTATUS", "出勤", 11L);
            }
            newpersongroupSet.close();
            return true;
        }

        newpersongroupSet.close();
        return false;
    }

    private void setReadOnly(MboRemote oplog) throws RemoteException, MXException {

        MboSetRemote appSet = oplog.getThisMboSet();

        String appname = appSet.getApp();

        if (!"OPLOG".equalsIgnoreCase(appname)) {
            return;
        }
        String login2 = oplog.getUserInfo().getPersonId();
        String dbrPerson = oplog.getString("DBRPERSON");

        if ((login2.equals(dbrPerson)) || ("MAXADMIN".equalsIgnoreCase(login2))) {
            return;
        }
        oplog.getMboSet("oplognum_oplogassetinfo_jdsx").setFlag(7L, true);
        oplog.getMboSet("oplognum_oplogassetinfo_asset_1").setFlag(7L, true);
        oplog.getMboSet("oplognum_oplogassetinfo_asset_2").setFlag(7L, true);
        oplog.getMboSet("oplognum_oplogassetinfo_zycs_1").setFlag(7L, true);
        oplog.getMboSet("oplognum_oplogassetinfo_zycs_2").setFlag(7L, true);
        oplog.getMboSet("oplognum_oplogcoalstatus").setFlag(7L, true);
        oplog.getMboSet("oplognum_oplogcommand").setFlag(7L, true);
        oplog.getMboSet("oplognum_oplogfsrecord").setFlag(7L, true);
        oplog.getMboSet("oplognum_oplogkeylog").setFlag(7L, true);
        oplog.getMboSet("oplognum_oplogpersoncq").setFlag(7L, true);

        oplog.getMboSet("WORKORDER_TASK1").setFlag(7L, true);
        oplog.getMboSet("WORKORDER_UNTASK1").setFlag(7L, true);
        oplog.getMboSet("WORKORDER_TASKFINISH1").setFlag(7L, true);
        oplog.getMboSet("WORKORDER_TASKCANCEL1").setFlag(7L, true);
    }

    public String isReadOnly() throws MXException {

        String status = getMboValue("status").getString();
        if ("CLOSE".equalsIgnoreCase(status)) {
            return "日志已关闭,不能修改!";
        }
        return null;
    }
}