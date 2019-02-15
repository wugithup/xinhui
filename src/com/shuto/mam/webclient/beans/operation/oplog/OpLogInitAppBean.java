/*
 * Copyright (c) 2018. Shuto版权所有
 */

package com.shuto.mam.webclient.beans.operation.oplog;

import com.shuto.mam.app.operation.oplog.OpLog;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import java.rmi.RemoteException;

/**
 * @Title: OpLogInitAppBean.java
 * @Package com.shuto.mam.webclient.beans.operation.oplog
 * @Description: TODO(运行日志维护保存)
 * @author wuqi
 * @date 2017-5-10 上午10:48:15
 * @version V1.0
 */
public class OpLogInitAppBean extends AppBean
{
  public OpLogInitAppBean()
    throws RemoteException, MXException
  {
    setOrderBy("oplognum desc");
  }

  public int SAVE()
    throws MXException, RemoteException
  {
	  OpLog op = (OpLog)getMbo();
	  String NewOplogNum = op.getString("OPLOGNUM");
		String DESIGNPERSON = op.getString("DBRPERSON");
		String OPLOGTYPE = op.getString("OPLOGTYPE");
		String SITEID = op.getString("SITEID");
		String ORGID = op.getString("ORGID");
    if (getMbo().isNew())
    {
    	try {
			//带入设备工况、重要参数
            op.assetAndPoint(OPLOGTYPE, NewOplogNum, DESIGNPERSON, SITEID,
					ORGID);
		} catch (Exception e) {
            System.out.println("===带入设备工况、重要参数OpLogInitAppBean.SAVE().assetAndPoint()出错！");
		}
//		try {
//			//出勤记录带入
//			op.getNewPersonGroup();
//		} catch (Exception e) {
//			System.out.println("===出勤记录带入OpLogInitAppBean.SAVE().getNewPersonGroup()出错！");
//		}
    }
    return super.SAVE();
  }
}