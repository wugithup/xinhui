package com.shuto.mam.app.yfx;
/**  
com.shuto.mam.app.Fldasset.Fldperson 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月30日 下午8:37:56
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.Mbo;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class Fldperson extends MAXTableDomain
{
  public Fldperson(MboValue mbv)
  {
    super(mbv);
    setRelationship("PERSON", "");
    String[] strFrom = { "PERSONID" };
    String[] strTo = { "PERSONID" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    
	  Mbo mbo = getMboValue().getMbo();
	  String siteid = mbo.getString("siteid");
	  setListCriteria("status='活动' and locationsite='"+siteid+"'");
    return super.getList();
  }
}
