package com.shuto.mam.app.crontask.pm;

import java.rmi.RemoteException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.StringTokenizer;

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

/**
 * 预防性维护工作
 * @author zhenglb@shuto.cn
 * @date 2017-07-20
 *
 */
public class CronPMGenWO
  extends SimpleCronTask
{
  public static final String WORKTYPE ="预维护工单";
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
  /**
   * yyyy-MM-dd
   */
  private SimpleDateFormat dtfmt1 = new SimpleDateFormat("yyyy-MM-dd");
  /**
   * yyyy-MM-dd HH:mm:ss
   */
  private SimpleDateFormat dtfmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
  private Calendar today = null;
  private Connection con = null;
  private CallableStatement stmt = null;
  private ConnectionKey key = null;
  private String sql = "update pm set nextdate=to_date(?, 'yyyy-MM-dd') where pmnum=?";
  private boolean debug = true;
	
  
  public void init()
    throws MXException
  {
    try
    {
      super.init();
      readConfig();
      this.mxserver = MXServer.getMXServer();
      this.userInfo = getRunasUserInfo();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void start() {
		try {
			readConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
  
  public void cronAction()
  {
    println("\n>>>>>>>>>> 定时任务 CronPMGenWO 执行 (" + this.dtfmt2.format(new Date()) + ") >>>>>>>>>>");
    try
    {
    	//格式化日期
      this.today = Calendar.getInstance();
      this.today.setTime(this.dtfmt1.parse(this.dtfmt1.format(new Date())));
      println(">>>>>>>>>today=" + this.dtfmt1.format(this.today.getTime()) + "\n");
      
      checkNextdate();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    println("\n>>>>>>>>>> 定时任务 CronPMGenWO 结束 (" + this.dtfmt2.format(new Date()) + ") >>>>>>>>>>\n");
  }
  
  private void checkNextdate()
    throws RemoteException, MXException
  {
    println("------- checkNextdate -------");
    
    MboSetRemote pms = this.mxserver.getMboSet("pm", this.userInfo);
    pms.setWhere(" worktype = '"+WORKTYPE+"' and status='"+ACTIVE+"' and trunc(nextdate) <= trunc(sysdate) and siteid='"+this.siteid+"'");

    pms.setOrderBy("PMUID desc");
    pms.reset();
    
    if (!pms.isEmpty()) {
    	for (int i = 0; i < pms.count(); ++i) {
    		MboRemote pm = pms.getMbo(i);
    		try
    	      {
    	        if (pm.isNull("nextdate"))
    	        {
    	          System.out.println(">>>>>nextdate is null, set not active");
    	          setNextdate(pm.getString("pmnum"), getNextdate(pm));
    	        }
    	        else
    	        {
    	        	/**
    	        	 * 到期执行日期
    	        	 */
    	          Calendar nextday = Calendar.getInstance();
    	          nextday.setTime(pm.getDate("nextdate"));
    	          if (nextday.getTime().before(this.today.getTime()))
    	          {
    	            System.out.println(">>>>>nextdate is expired, set a new nextdate");
    	            setNextdate(pm.getString("pmnum"), getNextdate(pm));
    	          }
    	          else if (this.dtfmt1.format(nextday.getTime()).equals(this.dtfmt1.format(this.today.getTime())))
    	          {
    	        	  System.out.println(">>>>>>nextdate is OK, start create workorder.....");
    	            createWO(pm);
    	            setNextdate(pm.getString("pmnum"), getNextdate(pm));
    	          }
    	        }
    	      }
    	      catch (Exception e)
    	      {
    	        e.printStackTrace();
    	      }
    	}
    }
    pms.close();
    println("\n----- checkNextdate end -----\n");
  }
  
  private void createWO(MboRemote pm)
		    throws RemoteException, MXException
		  {
	  		System.out.println(">>>>>generating workorder once...");
	  		((PM)pm).generateWork(false, 0);
			//已经生成的工单
		    MboSetRemote woSet = this.mxserver.getMboSet("workorder", this.userInfo);
		    woSet.setWhere("pmnum='" + pm.getString("pmnum") + "' and siteid='" + pm.getString("siteid") +
		      "' and targstartdate=to_date('" + this.dtfmt1.format(pm.getDate("nextdate")) + "','yyyy-MM-dd')");
		    woSet.setOrderBy("workorderid desc");
		    
		      if (!woSet.isEmpty()){
		    	  
		    	  String location = pm.getString("LOCATION");//位置
		    	  String s_profession = pm.getString("s_profession");// 专业
				  String SUPERVISOR = pm.getString("SUPERVISOR");// 主管人
				  String TEAMNAME = pm.getString("TEAMNAME");// 检修班组
				  String LEAD = pm.getString("LEAD");// 负责人

					
				  woSet.getMbo(0).setValue("location", location, 11L);
					woSet.getMbo(0).setValue("createperson", SUPERVISOR, 11L);// 创建人设置为主管人
					woSet.getMbo(0).setValue("LEAD", LEAD, 11L);// 负责人
					woSet.getMbo(0).setValue("TEAMNUM", TEAMNAME, 11L);// 检修班组
					woSet.getMbo(0).setValue("PROFESSION", s_profession, 11L);// 专业

					Date date = new Date();
					woSet.getMbo(0).setValue("SCHEDSTART", date, 11L);//计划开始时间
					woSet.getMbo(0).setValue("SCHEDFINISH", date, 11L);//计划结束时间
					woSet.save();

					WorkFlowServiceRemote wfs = (WorkFlowServiceRemote) MXServer.getMXServer().lookup("WORKFLOW");
					//初始化流程发起
					wfs.initiateWorkflow("WO", woSet.getMbo(0));
		      }
		      woSet.close();
		  }
  
  /**
   * 下一个日期
   * @param pm
   * @return
   * @throws RemoteException
   * @throws MXException
   */
  private Calendar getNextdate(MboRemote pm)
    throws RemoteException, MXException
  {
    Calendar nextday = Calendar.getInstance();
    nextday.setTime(this.today.getTime());
    if (pm.getString("frequnit").equals("固定日期"))
    {
      if (!pm.getString("fixedday").isEmpty())
      {
        nextday = getNextdate_fixedday(pm);
      }
      else
      {
        println(">>>>>error - don't config fixedday.");
        nextday = null;
      }
    }
    else if (pm.getString("frequnit").equals("固定星期"))
    {
      if (!pm.getString("fixedday").isEmpty())
      {
        nextday = getNextdate_weekday(pm);
      }
      else
      {
        println(">>>>>error - doesn't config fixedweekday\n");
        nextday = null;
      }
    }
    else if (pm.getString("frequnit").equals("天")) {
      nextday = getNextdate_days(pm);
    } else if (pm.getString("frequnit").equals("周")) {
      nextday = getNextdate_week(pm);
    } else if (pm.getString("frequnit").equals("月")) {
      nextday = getNextdate_month(pm);
    } else if (pm.getString("frequnit").equals("年")) {
      nextday = getNextdate_year(pm);
    }
    println(">>>>>nextdate=" + (nextday == null ? "" : this.dtfmt1.format(nextday.getTime())));
    return nextday;
  }
  
  private Calendar getNextdate_fixedday(MboRemote pm)
    throws RemoteException, MXException
  {
    if (pm.isNull("nextdate")) {
      return newNextdate_fixedday(pm, this.today);
    }
    Calendar nextday = Calendar.getInstance();
    nextday.setTime(pm.getDate("nextdate"));
    do
    {
      nextday = newNextdate_fixedday(pm, nextday);
    } while (nextday.getTime().before(this.today.getTime()));
    return nextday;
  }
  
  private Calendar newNextdate_fixedday(MboRemote pm, Calendar curdate)
    throws RemoteException, MXException
  {
    Calendar nextday = Calendar.getInstance();
    nextday.setTime(curdate.getTime());
    
    boolean flag = false;
    
    StringTokenizer st = new StringTokenizer(pm.getString("fixedday"), ",");
    while (st.hasMoreTokens())
    {
      String day = st.nextToken();
      if (Integer.valueOf(day).intValue() > curdate.get(5)) {
        flag = true;
      }
      if (flag)
      {
        nextday.set(5, Integer.valueOf(day).intValue());
        break;
      }
    }
    if (!flag)
    {
      nextday.add(2, pm.getInt("frequency"));
      st = new StringTokenizer(pm.getString("fixedday"), ",");
      String defday = st.nextToken();
      nextday.set(5, Integer.valueOf(defday).intValue());
    }
    System.out.println(">>>>>newNextdate_fixedday=" + this.dtfmt1.format(nextday.getTime()));
    return nextday;
  }
  
  private Calendar getNextdate_weekday(MboRemote pm)
    throws RemoteException, MXException
  {
    if (pm.isNull("nextdate")) {
      return newNextdate_weekday(pm, this.today);
    }
    Calendar nextday = Calendar.getInstance();
    nextday.setTime(pm.getDate("nextdate"));
    do
    {
      nextday = newNextdate_weekday(pm, nextday);
    } while (nextday.getTime().before(this.today.getTime()));
    return nextday;
  }
  
  private Calendar newNextdate_weekday(MboRemote pm, Calendar curdate)
    throws RemoteException, MXException
  {
    Calendar nextday = Calendar.getInstance();
    nextday.setTime(curdate.getTime());
    
    boolean flag = false;
    
    StringTokenizer st = new StringTokenizer(pm.getString("fixedday"), ",");
    while (st.hasMoreTokens()) {
		String weekday = st.nextToken();
		if (Integer.valueOf(weekday).intValue() > nextday.get(Calendar.DAY_OF_WEEK) - 1) {
			flag = true;
		}
		if (flag) {
			if (Integer.valueOf(weekday).intValue() < 7) {//周一至周六
				nextday.set(7, Integer.valueOf(weekday).intValue() + 1);
				break;
			}
			nextday.add(Calendar.DAY_OF_MONTH, 7);//下周
			nextday.set(Calendar.DAY_OF_WEEK, 1);//周日

			break;
		}
	}
    if (!flag)
    {
      nextday.add(5, pm.getInt("frequency") * 7);
      st = new StringTokenizer(pm.getString("fixedday"), ",");
      String weekday = st.nextToken();
		if (Integer.valueOf(weekday).intValue() < 7) {//周一至周六
			nextday.set(Calendar.DAY_OF_WEEK, Integer.valueOf(weekday).intValue() + 1);
		} else {
			nextday.add(Calendar.DAY_OF_MONTH, 7);//下周
			nextday.set(Calendar.DAY_OF_WEEK, 1);//周日
		}
	}
	System.out.println(">>>>>newNextdate_weekday="+ this.dtfmt1.format(nextday.getTime()));
	return nextday;
  }
  
  /**
   * 下一个执行日期  频率天
   * @param pm
   * @return
   * @throws RemoteException
   * @throws MXException
   */
  private Calendar getNextdate_days(MboRemote pm)
    throws RemoteException, MXException
  {
    if (pm.isNull("nextdate"))
    {
      //如果一天一次，下一个执行日期没有设置，默认设置为今天
      if (pm.getInt("frequency") == 1) {
        return this.today;
      }
      return null;
    }
    Calendar nextday = Calendar.getInstance();
    nextday.setTime(pm.getDate("nextdate"));
    do
    {
      nextday.add(5, pm.getInt("frequency"));
    } while (nextday.getTime().before(this.today.getTime()));
    return nextday;
  }
  
  /**
   * 下一个执行日期  频率周
   * @param pm
   * @return
   * @throws RemoteException
   * @throws MXException
   */
  private Calendar getNextdate_week(MboRemote pm)
    throws RemoteException, MXException
  {
    if (pm.isNull("nextdate")) {
      return null;
    }
    Calendar nextday = Calendar.getInstance();
    nextday.setTime(pm.getDate("nextdate"));
    do
    {
    	nextday.add(5, pm.getInt("frequency") * 7);
    } while (nextday.getTime().before(this.today.getTime()));
    return nextday;
  }
  
  /**
   * 下一个执行日期  频率月
   * @param pm
   * @return
   * @throws RemoteException
   * @throws MXException
   */
  private Calendar getNextdate_month(MboRemote pm)
    throws RemoteException, MXException
  {
    if (pm.isNull("nextdate")) {
      return null;
    }
    Calendar nextday = Calendar.getInstance();
    nextday.setTime(pm.getDate("nextdate"));
    do
    {
      nextday.add(2, pm.getInt("frequency"));
    } while (nextday.getTime().before(this.today.getTime()));
    return nextday;
  }
  
  /**
   * 下一个执行日期  频率年
   * @param pm
   * @return
   * @throws RemoteException
   * @throws MXException
   */
  private Calendar getNextdate_year(MboRemote pm)
    throws RemoteException, MXException
  {
    if (pm.isNull("nextdate")) {
      return null;
    }
    Calendar nextday = Calendar.getInstance();
    nextday.setTime(pm.getDate("nextdate"));
    do
    {
      nextday.add(1, pm.getInt("frequency"));
    } while (nextday.getTime().before(this.today.getTime()));
    return nextday;
  }
  
  /**
   * 设置下一个日期
   * @param pmnum
   * @param nextdate
   * @throws RemoteException
   * @throws MXException
   */
  private void setNextdate(String pmnum, Calendar nextdate)
    throws RemoteException, MXException
  {
    try
    {
      this.key = MXServer.getMXServer().getDBManager().getSystemConnectionKey();
      this.con = MXServer.getMXServer().getDBManager().getConnection(this.key);
      this.stmt = this.con.prepareCall(this.sql);
      if (nextdate == null)
      {
        println(">>>>>deactivate pm " + pmnum);
        this.stmt = this.con.prepareCall("update pm set status='不活动' where pmnum='" + pmnum + "'");
      }
      else
      {
        println(">>>>>setNextdate=" + this.dtfmt1.format(nextdate.getTime()));
        this.stmt.setString(1, this.dtfmt1.format(nextdate.getTime()));
        this.stmt.setString(2, pmnum);
      }
      this.stmt.execute();
    }
    catch (Exception e)
    {
      e.printStackTrace();
      if (this.stmt != null) {
        try
        {
          this.stmt.close();
        }
        catch (SQLException e3)
        {
          e3.printStackTrace();
        }
      }
      if (this.con != null) {
        try
        {
          MXServer.getMXServer().getDBManager().freeConnection(this.key);
        }
        catch (Exception e1)
        {
          e1.printStackTrace();
        }
      }
    }
    finally
    {
      if (this.stmt != null) {
        try
        {
          this.stmt.close();
        }
        catch (SQLException e)
        {
          e.printStackTrace();
        }
      }
      if (this.con != null) {
        try
        {
          MXServer.getMXServer().getDBManager().freeConnection(this.key);
        }
        catch (Exception e)
        {
          e.printStackTrace();
        }
      }
    }
  }
  
  /**
	 * <p>
	 * 取得任务参数：<br>
	 * siteid ：地点（每个任务实例只负责计算一个地点，简化逻辑，且减少错误影响面）<br>
	 */
	private void readConfig() throws RemoteException, MXException {
		this.siteid = getParamAsString("SITEID");
	}
	
	public CrontaskParamInfo[] getParameters() throws MXException,
			RemoteException {
		try {
			String[] names = { "siteid" };
			String[] defs = { "修改成对应的siteid" };
			String[][] descriptions = { { "cronprepr", "CronParamSiteid" } };
			CrontaskParamInfo[] ret = new CrontaskParamInfo[names.length];
			for (int i = 0; i < names.length; ++i) {
				ret[i] = new CrontaskParamInfo();
				ret[i].setName(names[i]);
				ret[i].setDefault(defs[i]);
				ret[i].setDescription(descriptions[i][0], descriptions[i][1]);
			}
			return ret;
		} catch (Exception e) {
			if (getCronTaskLogger().isErrorEnabled())
				getCronTaskLogger().error(e);
		}
		return null;
	}

	private void println(String str) {
		if (this.debug) {
			System.out.println(str);
		}
	}
}
