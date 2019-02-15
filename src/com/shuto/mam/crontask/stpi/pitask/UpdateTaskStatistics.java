package com.shuto.mam.crontask.stpi.pitask;

import java.math.BigDecimal;
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;

public class UpdateTaskStatistics
{
	/**
	 * 
	 * @param taskid
	 * @param checkCount 检查点
	 * @param ignoreCount 过滤点
	 */
  public void updateTaskStatistics(Object taskid,int checkCount,int ignoreCount)
  {
    try
    {
      MXServer server = MXServer.getMXServer();

      MboSetRemote taskSet = server.getMboSet("ST_PI_TASK", server.getSystemUserInfo());

      taskSet.setWhere("ST_PI_TASKID = '" + taskid + "'");
      if (!taskSet.isEmpty()) {
        MboRemote taskMbo = taskSet.getMbo(0);

        //应到区域
        int norm_area = taskMbo.getInt("norm_area");
        //总点
        int totalCount = taskMbo.getInt("norm_point");

        MboSetRemote areasdSet = taskMbo.getMboSet("ST_PI_TASK_AREA_SD");
        //实到区域
        int real_area = areasdSet.count();
        //到位率
        double in_place_rate =  getRate(real_area ,norm_area);

        //应该检点
        int yingjiandian = totalCount - ignoreCount;//总点-过滤点
        //漏检点
        int missed_point = totalCount - checkCount - ignoreCount;// 漏检点= 总点- 检查点-过滤点
        MboSetRemote itemycSet = taskMbo.getMboSet("ST_PI_TASK_ITEM_YC");
        //异常点数量
        int abnormal_point = itemycSet.count();
        //点检率
        double pi_rate = 100.00;
        //漏检率
        double missed_rate = 0.00;
        if(yingjiandian>0){
        	 pi_rate =  getRate(checkCount , yingjiandian);
             missed_rate = getRate(missed_point , yingjiandian);
        }
        
        taskMbo.setValue("real_area", real_area, 11L);
        taskMbo.setValue("in_place_rate", in_place_rate, 11L);
        taskMbo.setValue("real_point", checkCount, 11L);
        taskMbo.setValue("pi_rate", pi_rate, 11L);
        taskMbo.setValue("ignore_point", ignoreCount, 11L);
        taskMbo.setValue("missed_point", missed_point, 11L);
        taskMbo.setValue("missed_rate", missed_rate, 11L);
        taskMbo.setValue("abnormal_point", abnormal_point, 11L);
        taskSet.save();
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    } catch (MXException e) {
      e.printStackTrace();
    }
  }
  
  public double getRate(double ret1,double ret2) {
		BigDecimal bg1 = new BigDecimal(ret1);
		BigDecimal bg2 = new BigDecimal(ret2);
		BigDecimal bg = bg1.divide(bg2,4,BigDecimal.ROUND_HALF_UP);
		bg = bg.multiply(new BigDecimal(100));
		double result = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
		return result;
	}
  
}