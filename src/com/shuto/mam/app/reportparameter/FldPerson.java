package com.shuto.mam.app.reportparameter;

import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;


/** 
* @author       lzq liuzq@shuoto.cn
* @description  查找人员值别相关的域
* @date         2017-7-27 下午10:28:48
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class FldPerson extends MAXTableDomain{

	public FldPerson(MboValue mbv) {
		super(mbv);
		String thisAtt = getMboValue().getName();
	    setRelationship("ALNDOMAIN", "1=1");
	    String[] strTo = { thisAtt };
	    String[] strFrom = { "VALUE" };
	    setLookupKeyMapInOrder(strTo, strFrom);
	}
	@Override
	public MboSetRemote getList() throws MXException, RemoteException {
		MboRemote mainMbo = getMboValue().getMbo();
	    String siteid = mainMbo.getString("H_SITEID");
	    setListCriteria("DOMAINID='PERSONZB' and SITEID='"+siteid+"'");	
		return super.getList();
	}
}
