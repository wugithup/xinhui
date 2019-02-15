package com.shuto.mam.app.om.gwm;
/**  
com.shuto.mam.app.om.gwm.FldDemolitionperson 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月15日 下午5:24:39
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldDemolitionperson extends MAXTableDomain
{
  public FldDemolitionperson(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    String thisAtt = getMboValue().getName();
    setRelationship("PERSON", "");
    String[] strFrom = { "PERSONID" };
    String[] strTo = { thisAtt };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
	  
	  Mbo mbo = getMboValue().getMbo();
	  String siteid = mbo.getString("siteid");
	  setListCriteria("status = '活动' AND LOCATIONSITE='"+siteid+"' and (department = 'DEPT03' or department = 'DEPT22' or department = 'DEPT36')");
    return super.getList();
  }
}
