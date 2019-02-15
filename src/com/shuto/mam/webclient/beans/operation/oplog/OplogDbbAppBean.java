package com.shuto.mam.webclient.beans.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MboSetRemote;
import psdi.server.MXServer;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

import com.shuto.mam.app.operation.oplog.OpLog;

/**   
 * @Title: OplogDbbAppBean.java 
 * @Package com.shuto.mam.webclient.beans.operation.oplog 
 * @Description: TODO(运行倒班表) 
 * @author wuqi   
 * @date 2017-5-11 上午09:54:00 
 * @version V1.0   
 */
public class OplogDbbAppBean extends AppBean
{
	  public OplogDbbAppBean()
	    throws RemoteException, MXException
	  {
//	    setOrderBy("oplognum desc");
	  }

	  public void CSDSJ() throws RemoteException, MXException{
//		  try {
//			MboSetRemote set = MXServer.getMXServer().getMboSet(
//					"OPLOGCFG", this.clientSession.getUserInfo());//OPLOGCFG,OPLOGASSETINFO
//			for (int i = 0; i < 500; i++) {
//				set.add(11L);
//				System.out.println(i);
//			}
//		} catch (Exception e) {
//			System.out.println("MBO有问题！！");
//		}
	  }
	}