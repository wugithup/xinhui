/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.app.crontask.pm;

import psdi.app.pm.PM;
import psdi.app.system.CrontaskParamInfo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;
import psdi.util.logging.MXLogger;
import psdi.util.logging.MXLoggerFactory;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author wuqi
 * @version V1.0
 * @Title: HDCronPMLine.java
 * @Package com.shuto.mam.app.crontask.pm
 * @Description: (预防应维护 （ 运行定期工作 ） 配置 定时任务类 ， 用于创建工单 ， 修改下一个到期日)
 * @date 2017-5-16 上午09:40:06
 */
public class HDCronPMLine extends SimpleCronTask {

    // 定义变量
    public static final String ACTIVE = "活动";

    private static final MXLogger MX_LOGGER = MXLoggerFactory.getLogger("maximo.application");

    private String siteid;

    private MXServer mxserver;

    private UserInfo userInfo;

    private SimpleDateFormat dtfmt1 = new SimpleDateFormat("yyyy/MM/dd");

    private SimpleDateFormat dtfmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Calendar today = null;

    private Date newnextday = new Date();

    /**
     * <p> 此方法为初始化方法， 初始化时将得到到maxserver与userinfo
     *
     * @throws MXException
     */
    @Override
    public void init() throws MXException {

        try {
            super.init();
            siteid = null;
            mxserver = MXServer.getMXServer();
            userInfo = getRunasUserInfo();
        } catch (Exception e) {
            MX_LOGGER.error(e);
        }
    }

    /**
     * <p> 此方法为定时执行方法
     */
    @Override
    public void cronAction() {

        MX_LOGGER.info("\n>>>>>>>>>> 火电业态-" + siteid + "-运行定期工作 执行开始HDCronPMLine (" +
                this.dtfmt2.format(new Date()) + ") >>>>>>>>>>");
        today = Calendar.getInstance();
        MboSetRemote pms;
        try {
            pms = mxserver.getMboSet("pm", userInfo);
            pms.setWhere("status='" + ACTIVE + "' and siteid='" + siteid + "'");
            MboRemote pm;

            for (int i = 0; pms.getMbo(i) != null; i++) {
                pm = pms.getMbo(i);
                MX_LOGGER.info(">>>>>pmnum=" + pm.getString("pmnum"));
                // 将pm传入解析时间行方法中，返回时间点
                //检查是否能创建工单，调用方法创建工单
                checkDate(pm);
            }
            pms.reset();
            pms.close();
        } catch (Exception e) {
            MX_LOGGER.error(e);
        }
        MX_LOGGER.info("\n>>>>>>>>>> 火电业态-" + siteid + "-运行定期工作 执行结束HDCronPMLine (" +
                this.dtfmt2.format(new Date()) + ") >>>>>>>>>>");
    }

    @Override
    public void start() {

        try {
            readConfig();
        } catch (Exception e) {
            MX_LOGGER.error(e);
        }
    }

    /**
     * <p> 此方法为检查“下一个到期时间方法”， 当前时间大于下一个开始时间时，
     * 触发创建工单
     * 检查pmline 中的下一个到期时间（nextdate）
     * 当前时间大于下一个到期日时间（nextdate），创建工单并且更新下一个到期时间
     *
     * @throws MXException
     * @throws RemoteException
     */
    @SuppressWarnings("null")
    public void checkDate(MboRemote pm) throws RemoteException, MXException {
        //得到时间行（pmline）的mboset集合
        MboSetRemote pmlineset = pm.getMboSet("pmnum_pmline");
        MboRemote pmline;
        Calendar nextday;
        Date nextdate;
        //循环遍历pmline结果集
        if (!pmlineset.isEmpty()) {
            for (int i = 0; pmlineset.getMbo(i) != null; i++) {
                pmline = pmlineset.getMbo(i);
                // 获得当前到期日期
                nextday = Calendar.getInstance();
                nextdate = pmline.getDate("nextdate");
                nextday.setTime(nextdate);
                if (today.getTimeInMillis() > nextday.getTimeInMillis()) {
                    // 创建工单
                    //将pm  pmline 传入道创建工单方法中
                    createWO(pm, pmline, pmline.getString("shift"));
                }
            }
        }
        pmlineset.save();
        pmlineset.close();
    }

    /**
     * <p> 此方法为创建工单的方法，并触发更新下一个到期时间方法
     *
     * @throws MXException
     * @throws RemoteException
     */
    private void createWO(MboRemote pm, MboRemote pmline, String shift)
            throws RemoteException, MXException {

        MX_LOGGER.info("====PM 创建运行定期工作 开始====");
        // 如果已经生成过工单，则不再生成
        MboSetRemote wos = mxserver.getMboSet("workorder", userInfo);
        wos.setWhere("pmnum='" + pm.getString("pmnum") + "' and orgid='" + pm.getString("orgid") +
                "' and targstartdate=to_date('" + dtfmt1.format(today.getTime()) +
                "','yyyy-mm-dd')");
        wos.setOrderBy("wonum desc");
        //业务类别——运行定期工作
        String worktype = pm.getString("worktype");
        //日志类别
        String oplogtype = pm.getString("OPLOGTYPE");
        //专业
        String sProfession = pm.getString("S_PROFESSION");
        //工作任务
        String yxTask = pm.getString("YX_TASK");
        //备注
        String remark = pm.getString("REMARK");
        if ("运行定期工作".equals(worktype)) {
            //创建工单类型为“运行定期工作”的wo，该wo需要插入班次字段
            ((PM) pm).generateWork(false, 0);
            wos = mxserver.getMboSet("workorder", userInfo);
            wos.setWhere("pmnum='" + pm.getString("pmnum") +
                    "' and targstartdate=trunc(sysdate,'DD') and  cwozb is null ");
            wos.setOrderBy("wonum desc");
            if (wos.count() > 0) {
                wos.getMbo(0).setValue("OPLOG_BANCI", shift, 11L);//班次
                wos.getMbo(0).setValue("OPLOG_PROFESSION", sProfession, 11L);//专业
                wos.getMbo(0).setValue("OPLOG_TASK", yxTask, 11L);//工作任务
                wos.getMbo(0).setValue("OPLOG_REMARK", remark, 11L);//备注
                wos.getMbo(0).setValue("OPLOGTYPE", oplogtype, 11L);//日志类别
                wos.getMbo(0).setValue("OPLOG_STATUS", "生成", 11L);//状态
                wos.getMbo(0).setValue("OPLOG_CREATEDATE", new Date(), 11L);//创建时间

                wos.save();
                MX_LOGGER.info("====PM 创建运行定期工作 结束（成功）====");
            }
        } else {//创建其他类型的wo
            wos = mxserver.getMboSet("workorder", userInfo);
            wos.setWhere("pmnum='" + pm.getString("pmnum") +
                    "' and targstartdate=trunc(sysdate,'DD') and  cwozb is null ");
            ((PM) pm).generateWork(false, 0);
            if (wos.count() > 0) {
                wos.save();
            }
        }
        wos.close();
        //更新下一个开始时间
        upateNextdate(pmline);
    }

    /**
     * <p> 此方法为创更新"下一个到期时间"方法,更新pmline开始时间（nextdate） 字段
     * 创建工单后根据具体下一个到期时间，更新下一个到期时间字段（nextdate
     *
     * @throws MXException
     * @throws RemoteException
     */
    public void upateNextdate(MboRemote pmline) throws RemoteException, MXException {

        Calendar nextday = Calendar.getInstance();
        //频率
        int schedule = pmline.getInt("schedule");
        //下一个开始时间
        Date nextdate = pmline.getDate("nextdate");
        //获得下nextdate字段中的时间
        nextday.setTime(nextdate);
        //单位（天、周、固定天，固定周...）
        String unit = pmline.getString("unit");
        String fixeddate = pmline.getString("fixeddate");
        boolean flag = false;
        // 固定日期：1~(28,29,30,31)
        if (unit.equals("固定日期")) {
            if (fixeddate != null && !fixeddate.equals("")) {
                if (today.get(Calendar.DAY_OF_MONTH) < Integer.valueOf(fixeddate)) {
                    flag = true;
                }
                // 修改PMLINE的"下一到期日"：还在同一个月
                if (flag) {
                    flag = true;
                    nextday.set(Calendar.DATE, Integer.valueOf(fixeddate));
                    newnextday = nextday.getTime();
                    pmline.setValue("nextdate", newnextday);
                    MX_LOGGER.info("更新下一个任务开始时间（固定日期，本月）:" + dtfmt1.format(newnextday));
                }
                // 修改PMLINE的"下一到期日"下一个月
                if (!flag) {
                    //添加一个月
                    nextday.add(Calendar.MONTH, 1);
                    nextday.set(Calendar.DATE, Integer.valueOf(fixeddate));
                    newnextday = nextday.getTime();
                    pmline.setValue("nextdate", newnextday);
                    MX_LOGGER.info("更新下一个任务开始时间（固定日期，下一个月）:" + dtfmt1.format(newnextday));
                }
            } else {
                MX_LOGGER.info("pmline=" + pmline.getString("pmlinenum") +
                        ", error - don't config fixedday.");
            }

        }
        // 固定星期：1~7
        else if (unit.equals("固定星期")) {
            if (!"".equals(fixeddate) && !fixeddate.isEmpty()) {
                // 修改PMLINE的"下一到期日"：还在同一个星期
                if (today.get(Calendar.DAY_OF_WEEK) - 1 < Integer.valueOf(fixeddate)) {
                    flag = true;
                }
                // 修改PM的"下一到期日"：还在同一个星期
                if (flag) {
                    flag = true;
                    nextday.set(Calendar.DAY_OF_WEEK, Integer.valueOf(fixeddate) + 1);
                    newnextday = nextday.getTime();
                    pmline.setValue("nextdate", newnextday);
                    MX_LOGGER.info("pmline===" + pmline.getString("pmlinenum"));
                    MX_LOGGER.info("更新下一个任务开始时间（固定星期，本周）:" + dtfmt1.format(newnextday));
                }
                // 修改PM的"下一到期日"：下一个周期所在的星期
                if (!flag) {
                    nextday.add(Calendar.DATE, 7);// 下一个周期所在的星期
                    nextday.set(Calendar.DAY_OF_WEEK, Integer.valueOf(fixeddate) + 1);
                    newnextday = nextday.getTime();
                    pmline.setValue("nextdate", newnextday);
                    MX_LOGGER.info("更新下一个任务开始时间（固定星期，下一周）:" + dtfmt1.format(newnextday));
                }
            }
            // 修改PM的"下一到期日"：还在同一个星期
            else {
                MX_LOGGER.info("pmline=" + pmline.getString("pmlinenum") +
                        ", error - don't config fixedday.");
            }
        }
        // 按天   更新下一个到期日
        else if (unit.equals("天")) {
            if (schedule > 0) {
                nextday.add(Calendar.DATE, schedule);
                newnextday = nextday.getTime();
                pmline.setValue("nextdate", newnextday);
                MX_LOGGER.info("更新下一个任务开始时间（按天）:" + dtfmt1.format(newnextday));
            }
        }
        // 按周   更新下一个到期日
        else if (unit.equals("周")) {
            if (schedule > 0) {
                //7天为周期跟心系一个开始时间
                nextday.add(Calendar.DATE, schedule * 7);
                newnextday = nextday.getTime();
                pmline.setValue("nextdate", newnextday);
                MX_LOGGER.info("更新下一个任务开始时间（按周）:" + dtfmt1.format(newnextday));
            }
        }
        // 按月  更行下一个到期日
        else if (unit.equals("月")) {
            if (schedule > 0) {
                //按月  周期跟心系一个开始时间
                nextday.add(Calendar.MONTH, schedule);
                newnextday = nextday.getTime();
                pmline.setValue("nextdate", newnextday);
                MX_LOGGER.info("更新下一个任务开始时间（按月）:" + dtfmt1.format(newnextday));
            } else {
                MX_LOGGER.info("pmline=" + pmline.getString("pmlinenum") +
                        ", error - don't set nextdate.");
            }
        }
        // 年
        else if (unit.equals("年")) {
            if (schedule > 0) {
                //按月  周期跟心系一个开始时间
                nextday.add(Calendar.YEAR, schedule);
                newnextday = nextday.getTime();
                pmline.setValue("nextdate", newnextday);
                MX_LOGGER.info("更新下一个任务开始时间（按年）:" + dtfmt1.format(newnextday));
            } else {
                MX_LOGGER.info("pmline=" + pmline.getString("pmlinenum") +
                        ", error - don't set nextdate.");
            }
        }
        // 标准方法未生成
        else {
            MX_LOGGER.info("pmline=" + pmline.getString("pmlinenum") + ", standard");
        }
        // 按班次，即每天
    }

    /**
     * <p>取得任务参数：<br>
     * siteid ：地点（每个任务实例只负责计算一个地点，简化逻辑，且减少错误影响面）<br>
     */
    private void readConfig() throws RemoteException, MXException {

        siteid = getParamAsString("siteid");
    }

    @Override
    public CrontaskParamInfo[] getParameters() {

        String[] names = {"siteid"};
        String[] defs = {"RDCENTER"};
        String[][] descriptions = {{"cronprepr", "CronParamSiteid"}};
        CrontaskParamInfo[] ret = new CrontaskParamInfo[names.length];
        for (int i = 0; i < names.length; i++) {
            ret[i] = new CrontaskParamInfo();
            ret[i].setName(names[i]);
            ret[i].setDefault(defs[i]);
            ret[i].setDescription(descriptions[i][0], descriptions[i][1]);
        }
        return ret;
    }
}