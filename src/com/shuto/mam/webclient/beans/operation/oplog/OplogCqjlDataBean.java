package com.shuto.mam.webclient.beans.operation.oplog;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

import com.shuto.mam.app.operation.oplog.OpLog;

/**   
 * @Title: OplogCqjlAppBean.java 
 * @Package com.shuto.mam.webclient.beans.operation.oplog 
 * @Description: TODO(出勤记录) 
 * @author wuqi   
 * @date 2017-5-11 下午02:34:19 
 * @version V1.0   
 */
public class OplogCqjlDataBean extends DataBean
{
//	  public int toggledetailstate(boolean open)
//	    throws MXException
//	  {
//	    return super.toggledetailstate(open);
//	  }
//
//	  public int toggleselectrow() throws MXException, RemoteException
//	  {
//	    return super.toggleselectrow();
//	  }
	@Override
	  public int addrow()
	    throws MXException
	  {
	    super.addrow();
	    try {
	    	MboRemote oplog =  app.getAppBean().getMbo();
	    	MboSetRemote oplogpersoncqset = oplog.getMboSet("oplognum_oplogpersoncq");
	    	MboRemote oplogpersoncq = oplogpersoncqset.getMbo();
	    	oplogpersoncq.setValue("OPLOGNUM", oplog.getString("OPLOGNUM"));
	    	oplogpersoncqset.close();
	    }
	    catch (RemoteException e)
	    {
	      e.printStackTrace();
	    }
	     return 1;
	  }

	  
	}