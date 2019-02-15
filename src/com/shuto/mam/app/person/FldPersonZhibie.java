package com.shuto.mam.app.person;

import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**   
 * @Title: FldPersonZhibie.java 
 * @Package com.shuto.mam.app.person 
 * @Description: TODO(运行记事) 
 * @author wuqi   
 * @date 2017-5-9 上午10:08:18 
 * @version V1.0   
 */


public class FldPersonZhibie extends MAXTableDomain
{
  public FldPersonZhibie(MboValue mbv)
  {
    super(mbv);
    String thisAtt = getMboValue().getName();
    setRelationship("SHIFT", "1=1");
    String[] strTo = { thisAtt };
    String[] strFrom = { "shiftnum" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    String personid = getMboSet().getUserInfo().getPersonId();
    MboSetRemote personSet = getMboValue().getMbo().getMboSet("$PERSON", "PERSON", "personid='" + personid + "'");
    if (personSet.getMbo(0) != null) {
      setListCriteria("shifttype='班值' and orgid = '" + personSet.getMbo(0).getString("locationorg") + "' and siteid = '" + personSet.getMbo(0).getString("locationsite") + "'");
      setListOrderBy("shiftid");
    }
    return super.getList();
  }
}