package com.shuto.mam.app.operation.oplogperson;

import java.rmi.RemoteException;

import psdi.app.person.FldPersonID;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**   
 * @Title: FldCqjlPerson.java 
 * @Package com.shuto.mam.app.operation.oplogperson 
 * @Description: TODO(出勤记录人员只能选择运行日志配置里存在的人员) 
 * @author wuqi   
 * @date 2017-5-11 下午03:34:03 
 * @version V1.0   
 */
public class FldCqjlPerson  extends FldPersonID
{
	  public FldCqjlPerson(MboValue mbovalue) throws MXException
	  {
	    super(mbovalue);
//	    setRelationship("OPLOGPERSON", "");
//	    String[] strFrom = { "PERSONID" };
//	    String[] strTo = { "PERSONID" };
//	    setLookupKeyMapInOrder(strTo, strFrom);
	  }

	  public MboSetRemote getList()
	    throws MXException, RemoteException
	  {
		  MboRemote oplog = getMboValue().getMbo().getOwner();
		  String siteid =oplog.getString("SITEID");
		  String oplogType =oplog.getString("OPLOGTYPE");
		  String oplogNum =oplog.getString("OPLOGNUM");
		  String appname = oplog.getThisMboSet().getApp();
		  if("OPLOG".equalsIgnoreCase(appname)){
			    setListCriteria(" personid in (select personid from OPLOGPERSON where  siteid ='"+siteid+"' and OPLOGTYPE='"+oplogType+"') " +
			    		" and personid not in (select personid from oplogpersoncq where  siteid ='"+siteid+"' and oplognum='"+oplogNum+"') ");
		  }
		  
	    return super.getList();
	  }
	}