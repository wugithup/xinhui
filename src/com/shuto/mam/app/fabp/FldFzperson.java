package com.shuto.mam.app.fabp;
/**  
com.shuto.mam.app.fabp.FldFzperson 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月16日 下午4:06:08
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldFzperson extends MAXTableDomain
{
  public FldFzperson(MboValue mbv)
  {
    super(mbv);
    setRelationship("PERSON", "");
    String[] strFrom = { "PERSONID" };
    String[] strTo = { "FZPERSON" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
	  Mbo mbo = getMboValue().getMbo();
	  String siteid=mbo.getString("siteid");
	  setListCriteria("department='DEPT02' and status='活动' and locationsite='"+siteid+"'");
    return super.getList();
  }
}