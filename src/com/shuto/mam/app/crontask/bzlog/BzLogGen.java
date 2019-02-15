package com.shuto.mam.app.crontask.bzlog;

import com.shuto.mam.util.JDBCUtil;
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
 * @author wuchang
 * @version V1.0
 * @Title: BzLogGen
 * @Package com.shuto.mam.app.crontask.bzlog
 * @Description: <班组日志>的定时任务，用于生成新的班组日志
 * @date 2019/1/5 10:13
 */
public class BzLogGen extends SimpleCronTask {

    private MXServer mxserver;

    private UserInfo userInfo;

    private Connection conn;

    private SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public void init() throws MXException {

        try {
            super.init();
            this.mxserver = MXServer.getMXServer();
            this.userInfo = getRunasUserInfo();
        } catch (Exception e) {
            getCronTaskLogger().error(e);
        }
    }

    /**
     * 执行定时任务
     */
    @Override
    public void cronAction() {

        try {
            this.conn = JDBCUtil.conn();
            this.conn.setAutoCommit(false);
            getCronTaskLogger()
                    .info("======>定时任务开始============" + this.dateTimeFormat.format(new Date()));
            //添加班组日志
            addBzLog();
            getCronTaskLogger()
                    .info("======>定时任务结束============" + this.dateTimeFormat.format(new Date()));
        } catch (SQLException e) {
            getCronTaskLogger().error(e);
        }

    }

    /**
     * 用于添加班组日志
     */
    private void addBzLog() {

        //专业数组
        String[] profession = new String[]{"机务", "热工", "电气"};

        getCronTaskLogger().info("======>addBzLog============");
        MboSetRemote bzLogSet = null;
        MboSetRemote lastMboSetLog = null;
        MboSetRemote lastBzLogNote = null;
        MboSetRemote bzLogSetNote = null;
        MboSetRemote personMboSet = null;
        try {

            //获取班组日志对象，用于添加新的班组日志
            bzLogSet = this.mxserver.getMboSet("BZLOG", this.userInfo);
            bzLogSet.reset();

            //更新前天的班组日志
            updateBzLog();

            //添加班组日志主表数据
            if (!bzLogSet.isEmpty()) {
                for (String p : profession) {
                    MboRemote mboRemote = bzLogSet.add();
                    mboRemote.setValue("ZY", p);
                    String siteid = mboRemote.getString("SITEID");
                    //通过专业的中文描述以及地点查询
                    MboSetRemote professionMsr = mboRemote.getMboSet("$PROFESSION", "PROFESSION",
                            " DESCRIPTION ='" + p + "' AND SITEID = '" + siteid + "'");
                    //获取专业的编号
                    String professionid = professionMsr.getMbo(0).getString("PROFESSIONNUM");
                    //通过专业编号以及地点查询出所属专业的人员
                    personMboSet = this.mxserver.getMboSet("PERSON", this.userInfo);
                    personMboSet.setWhere(
                            "PROFESSION ='" + professionid + "' AND LOCATIONSITE= '" + siteid +
                                    "'");
                    personMboSet.reset();
                    //根据专业给出勤记录表添加纪录
                    addNote(mboRemote, personMboSet);

                    //获取前天的班组日志数据
                    lastMboSetLog = this.mxserver.getMboSet("BZLOG", this.userInfo);
                    //获取前一天
                    String date = getLastDay();
                    //获取前一天的专业的数据明显
                    lastMboSetLog.setWhere(
                            "TO_CHAR(CREATEDATE,'yyyy-mm-dd') = '" + date + "' AND ZY='" + p + "'");
                    lastMboSetLog.reset();
                    //将前天的符合条件的明细带入到当前班组日志中
                    int count = 0;
                    while (lastMboSetLog.getMbo(count) != null) {
                        getCronTaskLogger().info("======>addBzLog--while()============" + count);
                        //获取前天主表的数据
                        MboRemote lastMboLog = lastMboSetLog.getMbo(count);

                        //获取前天的子表数据
                        lastBzLogNote = lastMboLog.getMboSet("BZLOGNOTE");
                        //当前子表数据
                        bzLogSetNote = mboRemote.getMboSet("BZLOGNOTE");
                        //添加字表数据
                        addBzLogNote(lastBzLogNote, bzLogSetNote, mboRemote);
                        ++count;
                    }
                    bzLogSet.save();
                }

            }

        } catch (MXException | RemoteException e) {
            getCronTaskLogger().error(e);
        } finally {
            try {
                if (bzLogSet != null) {
                    bzLogSet.close();
                }
                if (lastMboSetLog != null) {
                    lastMboSetLog.close();
                }
                if (lastBzLogNote != null) {
                    lastBzLogNote.close();
                }
                if (bzLogSetNote != null) {
                    bzLogSetNote.close();
                }
                if (personMboSet != null) {
                    personMboSet.close();
                }

            } catch (MXException | RemoteException e) {
                getCronTaskLogger().error(e);
            }

        }

    }

    /**
     * 获取前一天
     *
     * @return
     */
    private String getLastDay() {

        Calendar ca = Calendar.getInstance();
        ca.setTime(new Date());
        ca.add(Calendar.DATE, -1);
        Date lastday = ca.getTime();
        return this.dateFormat.format(lastday);
    }

    /**
     * 添加班组信息子表数据
     */
    private void addBzLogNote(MboSetRemote lastBzLogNote, MboSetRemote bzLogSetNote,
            MboRemote jwMbo) {

        getCronTaskLogger().info("======>addBzLogNote============");
        int count = 0;
        MboRemote bzLogNote = null;
        try {
            bzLogSetNote.reset();

            while (lastBzLogNote.getMbo(count) != null) {
                getCronTaskLogger().info("======>addBzLogNote-while()============" + count);
                if (lastBzLogNote.getMbo(count).getBoolean("DRXYT")) {
                    bzLogNote = bzLogSetNote.add();
                    bzLogNote.setValue("DRXYT", 1);
                    bzLogNote.setValue("BZLOGNUM", jwMbo.getString("BZLOGNUM"));
                    bzLogNote.setValue("LOGDATE", lastBzLogNote.getMbo(count).getDate("LOGDATE"));
                    bzLogNote.setValue("JZ", lastBzLogNote.getMbo(count).getString("JZ"));
                    bzLogNote.setValue("NOTETYPE",
                            lastBzLogNote.getMbo(count).getString("NOTETYPE"));
                    bzLogNote.setValue("NOTE", lastBzLogNote.getMbo(count).getString("NOTE"));
                    bzLogNote.setValue("CREATEBY",
                            lastBzLogNote.getMbo(count).getString("CREATEBY"));
                    ++count;
                }

            }
            bzLogSetNote.save();

        } catch (MXException e) {
            getCronTaskLogger().error(e);
        } catch (RemoteException e) {
            getCronTaskLogger().error(e);
        }

    }

    /**
     * 更新班组日志历史数据
     */

    private void updateBzLog() {

        getCronTaskLogger().info("======>updateBzLog============");
        //获取当前时间的前一天的日期
        String date = getLastDay();

        try {
            //通过jdbc更新状态
            SqlFormat sf = new SqlFormat(
                    "UPDATE BZLOG SET STATUS ='已关闭' WHERE TO_CHAR(CREATEDATE,'yyyy-mm-dd')='" +
                            date + "'");
            conn.createStatement().executeUpdate(sf.format());
            //最后提交jdbc
            conn.commit();
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                getCronTaskLogger().error(e1);
            }
            getCronTaskLogger().error(e);
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                getCronTaskLogger().error(e);
            }
        }

    }

    /**
     * 给出勤记录表添加数据
     *
     * @param mainMbo:传入相关专业的主表对象
     * @param personMboSet:传入相关专业的人员信息对象
     */
    private void addNote(MboRemote mainMbo, MboSetRemote personMboSet) {

        getCronTaskLogger().info("======>addNote============");
        //--ZY101-电气,ZY107-热工,ZY104-机务SITEID
        MboRemote personMbo;
        MboRemote cqjlMbo;
        MboSetRemote cqjlSetMbo = null;

        try {
            if (!personMboSet.isEmpty()) {
                cqjlSetMbo = mainMbo.getMboSet("BZCQJL");
                cqjlSetMbo.reset();
                getCronTaskLogger()
                        .info("======> personMboSet.count()============" + personMboSet.count());
                for (int i = 0; personMboSet.getMbo(i) != null; i++) {
                    getCronTaskLogger()
                            .info("======>addNote--for()============" + mainMbo.getString("ZY") +
                                    "===" + i);
                    personMbo = personMboSet.getMbo(i);
                    cqjlMbo = cqjlSetMbo.add();
                    cqjlMbo.setValue("BZLOGNUM", mainMbo.getString("BZLOGNUM"));
                    cqjlMbo.setValue("CQPERSON", personMbo.getString("PERSONID"));
                    cqjlMbo.setValue("GW", personMbo.getString("POST"));
                    cqjlMbo.setValue("PHONENUM", personMbo.getString("MOBILEPHONE"));
                }
                cqjlSetMbo.save();
            }

        } catch (MXException | RemoteException e) {
            getCronTaskLogger().error(e);
        }

    }

}
