package com.shuto.mam.app.crontask.pm;

import psdi.app.pm.PM;
import psdi.app.system.CrontaskParamInfo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.ConnectionKey;
import psdi.security.UserInfo;
import psdi.server.MXServer;
import psdi.server.SimpleCronTask;
import psdi.util.MXException;
import psdi.workflow.WorkFlowServiceRemote;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author huangpf huangpf@shuto.cn
 * @version 1.0
 * @date 创建时间：2017-10-24 下午09:39:04
 * @修改历史：xiamy@shuto.cn 2018-01-08 18:37
 */
public class PMGenWOWork extends SimpleCronTask {
    public static final String WORKTYPE = "预维护工单";
    public static final String ACTIVE = "活动";
    public static final String FIXEDDAY = "固定日期";
    public static final String WEEKDAY = "固定星期";
    public static final String DAYS = "天";
    public static final String WEEK = "周";
    public static final String MONTH = "月";
    public static final String YEAR = "年";
    private String siteid;
    private MXServer mxserver;
    private UserInfo userInfo;
    private SimpleDateFormat dtfmt1 = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat dtfmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Calendar today = null;
    private Connection con = null;
    private CallableStatement stmt = null;
    private ConnectionKey key = null;
    private String sql = " update pm set nextdate=to_date(?,'yyyy-MM-dd') where pmnum=? and siteid=? ";
    private boolean debug = true;
    private int m = 0;
    private int n = 0;
    private int k = 0;
    String pmwrong = "";
    List<String> logList = new ArrayList<String>();

    @Override
    public void init() throws MXException {
        try {

            println("\n>>>>>>>>>>" + this.siteid + " 进入 init() 方法");
            super.init();
            readConfig();// 读取配置地点
            this.mxserver = MXServer.getMXServer();
            this.userInfo = getRunasUserInfo();
        } catch (Exception e) {
            println(getErrorInfoFromException(e));
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        try {
            println("\n>>>>>>>>>>" + this.siteid + " 进入 start() 方法");
            readConfig();
        } catch (Exception e) {
            println(getErrorInfoFromException(e));
            e.printStackTrace();
        }
    }

    @Override
    public void cronAction() {
        logList = new ArrayList<String>();
        println("\n>>>>>>>>>>" + this.siteid + "预防性维护定时任务 PM 执行  开始时间>>>>>> (" + this.dtfmt2.format(new Date()) + ") >>>>>>>>>>");
        m = 0;
        n = 0;
        k = 1;
        try {
            this.key = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
            this.con = MXServer.getMXServer().getDBManager().getConnection(this.key);
            this.con.setAutoCommit(false);
            this.stmt = this.con.prepareCall(this.sql);

            this.today = Calendar.getInstance();
            this.today.setTime(this.dtfmt1.parse(this.dtfmt1.format(new Date())));

            checkNextdate();
        } catch (Exception e) {
            try {
                this.con.rollback();
            } catch (SQLException e1) {
                println(e1.getMessage());
                e1.printStackTrace();
            }
            println(getErrorInfoFromException(e));
            e.printStackTrace();
        } finally {
            try {
                if (this.stmt != null) {
                    this.stmt.close();
                }
                if (this.con != null) {
                    this.con.close();
                }
            } catch (SQLException e) {
                println(getErrorInfoFromException(e));
                e.printStackTrace();
            }
        }
        println("\n>>>>>>>>>>本次成功创建并取到工单 " + m + " 个，成功发送工作流 " + n + " 个!");
        println("\n>>>>>>>>>>" + this.siteid + "预防性维护定时任务 PM 结束 结束时间>>>>> (" + this.dtfmt2.format(new Date()) + ") >>>>>>>>>>");

        Statement stm = null;
        try {
            // TODO Auto-generated
            String sql = " delete interfacelog where synchdate < sysdate-7 and description='预维护工单--" + this.siteid + "'";
            con = MXServer.getMXServer().getDBManager().getSequenceConnection();
            con.createStatement().executeUpdate(sql);
            stm = con.createStatement();
            int count = stm.executeUpdate(sql);
            println("\n>>>>>count:" + count);

        } catch (SQLException | RemoteException e) {
            println(getErrorInfoFromException(e));
            e.printStackTrace();
        } finally {
            if (stm != null) {
                try {
                    stm.close();
                } catch (SQLException e) {
                    println(getErrorInfoFromException(e));
                    e.printStackTrace();
                }
            }
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                println(getErrorInfoFromException(e));
                e.printStackTrace();
            }
            try {
                MboSetRemote interfacelogmsr = null;
                interfacelogmsr = this.mxserver.getMboSet("interfacelog", this.userInfo);
                MboRemote interfacelogmbo = interfacelogmsr.add();
                interfacelogmbo.setValue("description", "预维护工单--" + this.siteid);
                interfacelogmbo.setValue("synchtype", "生成");
                interfacelogmbo.setValue("synchdate", this.dtfmt2.format(new Date()));
                interfacelogmbo.setValue("datalog", logList.toString());
                interfacelogmsr.close();
                interfacelogmsr.save();
            } catch (MXException | RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void checkNextdate() throws RemoteException, MXException {
        println("\n------- checkNextdate -------");
        //活动的，但nextdate为空、或位置为空、或位置不合法的，设为不活动
        MboSetRemote pms2 = this.mxserver.getMboSet("pm", this.userInfo);
        pms2.setWhere(" worktype='预维护工单' and status='活动' and (nextdate is null "
                + "or location is null or location not in (select location from locations where siteid='" + this.siteid + "')) and siteid='"
                + this.siteid + "'  and trunc(nextdate)<=trunc(sysdate) ");
        pms2.setOrderBy("PMUID desc");
        pms2.reset();
        int count2 = pms2.count();
        println("\n>>>>>nextdate为空但活动的预防性维护共 " + count2 + " 个");
        if (!pms2.isEmpty()) {
            for (int i = 0; i < count2; i++) {
                MboRemote pm = pms2.getMbo(i);
                println("\n>>>>>循环检查中，总共 " + pms2.count() + " 个，当前第 " + (i + 1) + " 个，pmnum：" + pm.getString("pmnum"));
                try {
                    setNextdate(pm.getString("pmnum"), null);
                } catch (SQLException e) {
                    println(getErrorInfoFromException(e));
                    e.printStackTrace();
                }
            }
        }
        pms2.close();
        //过期但活动的，更新其nextdate
        MboSetRemote pms3 = this.mxserver.getMboSet("pm", this.userInfo);
        pms3.setWhere(" worktype='预维护工单' and status='活动' and trunc(nextdate)<trunc(sysdate) " + "and nextdate is not null and siteid='" + this.siteid
                + "'");
        pms3.setOrderBy("PMUID desc");
        pms3.reset();
        int count3 = pms3.count();
        println("\n>>>>>过期但活动的预防性维护共 " + count3 + " 个");
        if (!pms3.isEmpty()) {
            for (int i = 0; i < count3; i++) {
                MboRemote pm = pms3.getMbo(i);
                println("\n>>>>>循环检查中，总共 " + pms3.count() + " 个，当前第 " + (i + 1) + " 个，pmnum：" + pm.getString("pmnum"));
                try {
                    setNextdate(pm.getString("pmnum"), getNextdate(pm));
                } catch (SQLException e) {
                    println(getErrorInfoFromException(e));
                    e.printStackTrace();
                }
            }
        }
        pms3.close();

        //当天合格的，正常创建工单
        MboSetRemote pms1 = this.mxserver.getMboSet("pm", this.userInfo);
        String pmwhere =
                " worktype='预维护工单' and status='活动' and trunc(nextdate)=trunc(sysdate) " + "and nextdate is not null and siteid='" + this.siteid + "'";
        pms1.setWhere(pmwhere);
        pms1.setOrderBy("PMUID desc");
        pms1.reset();
        int count1 = pms1.count();
        println("\n>>>>>当天活动并合格的预防性维护共 " + count1 + " 个");
        if (!pms1.isEmpty()) {
            for (int j = 1; j <= count1; j++) {
                MboRemote pm = pms1.getMbo(0);
                println("\n>>>>>循环检查中，总共 " + count1 + " 个，当前第 " + j + " 个，pmnum：" + pm.getString("pmnum"));
                println("\n>>>>>当前集合pms剩余 " + pms1.count() + " 个");
                try {
                    createWO(pm);
                    if (k == 1) {
                        if ("".equals(pmwrong)) {
                            pmwrong = "'" + pm.getString("pmnum") + "'";
                        } else {
                            pmwrong = pmwrong + ",'" + pm.getString("pmnum") + "'";
                        }
                        println("\n>>>>>请注意，任务执行出错，出错 pmnum如下:\n" + pmwrong);
                        break;
                    }
                    setNextdate(pm.getString("pmnum"), getNextdate(pm));
                } catch (SQLException e) {
                    if (getCronTaskLogger().isErrorEnabled()) {
                        getCronTaskLogger().error(e);
                    }
                }
            }

        }
        pms1.close();

        println("\n----- checkNextdate end -----");
    }

    private void createWO(MboRemote pm) throws RemoteException, MXException {
        println("\n>>>>>生成 workorder begin..." + pm.getString("pmnum"));
        try {
            m++;
            ((PM) pm).generateWork(false, 0);
        } catch (Exception e) {
            println(getErrorInfoFromException(e));
            e.printStackTrace();
            println("\n>>>>>请注意，工单创建失败..." + pm.getString("pmnum"));
            k = 1;
            return;
        }
        k = 0;

        println("\n>>>>>生成 workorder ok..." + pm.getString("pmnum"));
        MboSetRemote woSet = this.mxserver.getMboSet("workorder", this.userInfo);
        woSet.setWhere("pmnum='" + pm.getString("pmnum") + "' and siteid='" + pm.getString("siteid")
                + "' and worktype='预维护工单' and trunc(targstartdate)=trunc(sysdate)");

        woSet.setOrderBy("workorderid desc");
        woSet.reset();
        if (!woSet.isEmpty()) {
            String location = pm.getString("LOCATION");
            String s_profession = pm.getString("s_profession");
            String SUPERVISOR = pm.getString("SUPERVISOR");
            String TEAMNAME = pm.getString("TEAMNAME");
            String LEAD = pm.getString("LEAD");
            String wonum = woSet.getMbo(0).getString("wonum");
            println("\n>>>>>生成 workorder is ok工单已创建，开始设值..." + pm.getString("pmnum") + "---" + wonum);

            woSet.getMbo(0).setValue("location", location, 11L);
            woSet.getMbo(0).setValue("createperson", SUPERVISOR, 11L);
            woSet.getMbo(0).setValue("LEAD", LEAD, 11L);
            woSet.getMbo(0).setValue("TEAMNUM", TEAMNAME, 11L);
            woSet.getMbo(0).setValue("PROFESSION", s_profession, 11L);

            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            cal.add(Calendar.DATE, 3);
            Date date2 = cal.getTime();
            woSet.getMbo(0).setValue("SCHEDSTART", date, 11L);
            woSet.getMbo(0).setValue("SCHEDFINISH", date2, 11L);
            woSet.save();

            println("\n>>>>>生成 workorder is ok，设值结束..." + pm.getString("pmnum") + "---" + wonum);
            WorkFlowServiceRemote wfs = (WorkFlowServiceRemote) MXServer.getMXServer().lookup("WORKFLOW");
            println("\n>>>>>生成 workorder is ok，获取工作流..." + pm.getString("pmnum") + "---" + wonum);
            try {
                wfs.initiateWorkflow("WO", woSet.getMbo(0));
                n++;
            } catch (Exception e) {
                println("\n>>>>>请注意，发送工作流失败...返回..." + pm.getString("pmnum") + "---" + wonum);
                println(getErrorInfoFromException(e));
                woSet.close();
                return;
            }

            println("\n>>>>>initiateWorkflow workorder is ok，工作流成功发送..." + pm.getString("pmnum") + "---" + wonum);

        }
        woSet.close();
    }

    private Calendar getNextdate(MboRemote pm) throws RemoteException, MXException {
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(this.today.getTime());
        if (pm.getString("frequnit").equals("固定日期")) {
            if (!pm.getString("fixedday").isEmpty()) {
                nextday = getNextdate_fixedday(pm);
            } else {
                println(">>>>>error - don't config fixedday.");
                nextday = null;
            }
        } else if (pm.getString("frequnit").equals("固定星期")) {
            if (!pm.getString("fixedday").isEmpty()) {
                nextday = getNextdate_weekday(pm);
            } else {
                println("\n>>>>>error - doesn't config fixedweekday");
                nextday = null;
            }
        } else if (pm.getString("frequnit").equals("天")) {
            if (!pm.getString("FREQUENCY").isEmpty()) {
                nextday = getNextdate_days(pm);
            } else {
                println("\n>>>>>error - doesn't config fixedweekday");
                nextday = null;
            }
        } else if (pm.getString("frequnit").equals("周")) {
            if (!pm.getString("FREQUENCY").isEmpty()) {
                nextday = getNextdate_week(pm);
            } else {
                println("\n>>>>>error - doesn't config fixedweekday");
                nextday = null;
            }
        } else if (pm.getString("frequnit").equals("月")) {
            if (!pm.getString("FREQUENCY").isEmpty()) {
                nextday = getNextdate_month(pm);
            } else {
                println("\n>>>>>error - doesn't config fixedweekday");
                nextday = null;
            }
        } else if (pm.getString("frequnit").equals("年")) {
            if (!pm.getString("FREQUENCY").isEmpty()) {
                nextday = getNextdate_year(pm);
            } else {
                println("\n>>>>>error - doesn't config fixedweekday");
                nextday = null;
            }
        }
        println("\n>>>>>nextdate=" + ((nextday == null) ? "" : this.dtfmt1.format(nextday.getTime())));
        return nextday;
    }

    private Calendar getNextdate_fixedday(MboRemote pm) throws RemoteException, MXException {
        //指定日期为空，不更新下一个执行日期
        if (pm.isNull("fixedday")) {
            return null;
        }
        if (pm.isNull("nextdate")) {
            return newNextdate_fixedday(pm, this.today);
        }
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(pm.getDate("nextdate"));
        do {
            nextday = newNextdate_fixedday(pm, nextday);
            try {
                nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday.getTime())));
            } catch (ParseException e) {
                println(getErrorInfoFromException(e));
                e.printStackTrace();
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    private Calendar newNextdate_fixedday(MboRemote pm, Calendar curdate) throws RemoteException, MXException {
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(curdate.getTime());

        boolean flag = false;

        StringTokenizer st = new StringTokenizer(pm.getString("fixedday"), ",");
        while (st.hasMoreTokens()) {
            String day = st.nextToken();
            if (Integer.valueOf(day).intValue() > curdate.get(5)) {
                flag = true;
            }
            if (!flag) {
                continue;
            }
            nextday.set(5, Integer.valueOf(day).intValue());
            break;
        }

        if (!flag) {
            nextday.add(Calendar.MONTH, 1);//下一个月
            st = new StringTokenizer(pm.getString("fixedday"), ",");
            String defday = st.nextToken();
            nextday.set(5, Integer.valueOf(defday).intValue());
        }
        println("\n>>>>>newNextdate_fixedday=" + this.dtfmt1.format(nextday.getTime()));
        return nextday;
    }

    private Calendar getNextdate_weekday(MboRemote pm) throws RemoteException, MXException {
        //指定日期为空，不更新下一个执行日期
        if (pm.isNull("fixedday")) {
            return null;
        }
        if (pm.isNull("nextdate")) {
            return newNextdate_weekday(pm, this.today);
        }
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(pm.getDate("nextdate"));
        do {
            nextday = newNextdate_weekday(pm, nextday);
            try {
                nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday.getTime())));
            } catch (ParseException e) {
                println(getErrorInfoFromException(e));
                e.printStackTrace();
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    private Calendar newNextdate_weekday(MboRemote pm, Calendar curdate) throws RemoteException, MXException {
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(curdate.getTime());

        boolean flag = false;

        StringTokenizer st = new StringTokenizer(pm.getString("fixedday"), ",");
        while (st.hasMoreTokens()) {
            String weekday = st.nextToken();
            if (Integer.valueOf(weekday).intValue() > nextday.get(7) - 1) {
                flag = true;
            }
            if (flag) {
                if (Integer.valueOf(weekday).intValue() < 7) {
                    nextday.set(7, Integer.valueOf(weekday).intValue() + 1);
                    break;
                }
                nextday.add(5, 7);
                nextday.set(7, 1);

                break;
            }
        }
        if (!flag) {
            nextday.add(Calendar.DAY_OF_MONTH, 7);//下一周
            st = new StringTokenizer(pm.getString("fixedday"), ",");
            String weekday = st.nextToken();
            if (Integer.valueOf(weekday).intValue() < 7) {
                nextday.set(7, Integer.valueOf(weekday).intValue() + 1);
            } else {
                nextday.add(5, 7);
                nextday.set(7, 1);
            }
        }
        println("\n>>>>>newNextdate_weekday=" + this.dtfmt1.format(nextday.getTime()));
        return nextday;
    }

    private Calendar getNextdate_days(MboRemote pm) throws RemoteException, MXException {
        if (pm.isNull("nextdate")) {
            if (pm.getInt("frequency") == 1) {
                return this.today;
            }
            return null;
        }
        if (pm.isNull("frequency") || pm.getInt("frequency") <= 0) {
            return null;
        }
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(pm.getDate("nextdate"));
        do {
            nextday.add(5, pm.getInt("frequency"));
            try {
                nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday.getTime())));
            } catch (ParseException e1) {
                println(e1.getMessage());
                e1.printStackTrace();
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    private Calendar getNextdate_week(MboRemote pm) throws RemoteException, MXException {
        if (pm.isNull("nextdate")) {
            return null;
        }
        if (pm.isNull("frequency") || pm.getInt("frequency") <= 0) {
            return null;
        }
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(pm.getDate("nextdate"));
        do {
            nextday.add(5, pm.getInt("frequency") * 7);
            try {
                nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday.getTime())));
            } catch (ParseException e) {
                println(getErrorInfoFromException(e));
                e.printStackTrace();
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    private Calendar getNextdate_month(MboRemote pm) throws RemoteException, MXException {
        if (pm.isNull("nextdate")) {
            return null;
        }
        if (pm.isNull("frequency") || pm.getInt("frequency") <= 0) {
            return null;
        }
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(pm.getDate("nextdate"));
        do {
            nextday.add(2, pm.getInt("frequency"));
            try {
                nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday.getTime())));
            } catch (ParseException e) {
                println(getErrorInfoFromException(e));
                e.printStackTrace();
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    private Calendar getNextdate_year(MboRemote pm) throws RemoteException, MXException {
        if (pm.isNull("nextdate")) {
            return null;
        }
        if (pm.isNull("frequency") || pm.getInt("frequency") <= 0) {
            return null;
        }
        Calendar nextday = Calendar.getInstance();
        nextday.setTime(pm.getDate("nextdate"));
        do {
            nextday.add(1, pm.getInt("frequency"));
            try {
                nextday.setTime(this.dtfmt1.parse(this.dtfmt1.format(nextday.getTime())));
            } catch (ParseException e) {
                println(getErrorInfoFromException(e));
                e.printStackTrace();
            }
        } while (nextday.getTime().getTime() <= this.today.getTime().getTime());
        return nextday;
    }

    private void setNextdate(String pmnum, Calendar nextdate) throws RemoteException, MXException, SQLException {
        this.stmt = null;
        if (nextdate == null) {
            println("\n>>>>>deactivate pm " + pmnum);
            this.stmt = this.con.prepareCall("update pm set status='不活动' where pmnum='" + pmnum + "' and siteid='" + this.siteid + "'");
        } else {
            println("\n>>>>>setNextdate=" + this.dtfmt1.format(nextdate.getTime()));
            this.stmt = this.con.prepareCall(sql);
            this.stmt.setString(1, this.dtfmt1.format(nextdate.getTime()));
            this.stmt.setString(2, pmnum);
            this.stmt.setString(3, this.siteid);
        }
        this.stmt.execute();
        this.con.commit();
    }

    private void readConfig() throws RemoteException, MXException {
        this.siteid = getParamAsString("SITEID");
    }

    @Override
    public CrontaskParamInfo[] getParameters() throws MXException, RemoteException {
        try {
            String[] names = {"siteid"};
            String[] defs = {"修改成对应的siteid"};
            String[][] descriptions = {{"cronprepr", "CronParamSiteid"}};
            CrontaskParamInfo[] ret = new CrontaskParamInfo[names.length];
            for (int i = 0; i < names.length; ++i) {
                ret[i] = new CrontaskParamInfo();
                ret[i].setName(names[i]);
                ret[i].setDefault(defs[i]);
                ret[i].setDescription(descriptions[i][0], descriptions[i][1]);
            }
            return ret;
        } catch (Exception e) {
            if (getCronTaskLogger().isErrorEnabled()) {
                getCronTaskLogger().error(e);
            }
        }
        return null;
    }

    /**
     * 把输出语句和异常写入list中
     */
    private void println(String str) {
        if (this.debug) {
            logList.add(str);
        }
    }

    /**
     * 输出异常信息
     **/
    private static String getErrorInfoFromException(Throwable e) {
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return "\r\n" + sw.toString() + "\r\n";
        } catch (Exception localException) {
            localException.printStackTrace();
        }
        return "bad getErrorInfoFromException";
    }
}