package com.shuto.mam.app.reportparameter;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.mbo.SqlFormat;
import psdi.util.MXApplicationException;
import psdi.util.MXException;

public class FldProfession extends MAXTableDomain{

	public FldProfession(MboValue mbv) {
		super(mbv);
	    setRelationship("profession", "1=1");
	    setListCriteria("1=1");
	    String[] target = { getMboValue().getName() };
	    String[] source = { "professionnum" };
	    setLookupKeyMapInOrder(target, source);
	}
	
	public MboSetRemote getList() throws MXException, RemoteException {
	    	MboRemote mainMbo = getMboValue().getMbo();
		    String siteid = mainMbo.getString("H_SITEID");
		    String orgid = mainMbo.getString("ORGID");
		    if(siteid.isEmpty()){	    	
		    	setListCriteria("ORGID = '" + orgid + "' and STATUS='活动'");
		    }else {
		    	setListCriteria("SITEID = '" + siteid + "' and STATUS='活动'");
		    }
	    return super.getList();
	}
	public void action() throws MXException, RemoteException {
		    super.action();
		  }

	public void validate()
		throws MXException, RemoteException
		  {
		    super.validate();
		if (getMboValue().isNull()) {
		      return;
		    }
		   MboSetRemote list = getList();
		   SqlFormat sql = new SqlFormat("PROFESSIONNUM=:1");
		   sql.setObject(1, "PROFESSION", "PROFESSIONNUM", getMboValue().getString());
		   String appWhere = list.getAppWhere();
		   list.setAppWhere(sql.format());
		   list.reset();
		   int count = list.count();
		   list.setAppWhere(appWhere);
		   list.reset();
		   if (count == 0)
		throw new MXApplicationException("该值在选择列表中不存在", "");
		  }
}
