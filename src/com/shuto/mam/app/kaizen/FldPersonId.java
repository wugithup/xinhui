package com.shuto.mam.app.kaizen;
/**  
com.shuto.mam.app.kaizen.FldPersonId 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年10月24日 上午11:16:43
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPersonId extends MAXTableDomain
{
  public FldPersonId(MboValue mbv)
  {
    super(mbv);
    String thisAtrr = getMboValue().getAttributeName();
    setRelationship("person", "1=1");
    String[] strTo = { thisAtrr };
    String[] strFrom = { "personid" };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList()
    throws MXException, RemoteException
  {
    String userinfo = getMboValue().getMbo().getUserInfo().getPersonId();
    MboSetRemote msf = getMboValue().getMbo().getMboSet("$person", "person", "personid='" + userinfo + "'");
    String siteid = msf.getMbo(0).getString("locationsite");
    setListCriteria("locationsite='" + siteid + "'");
    return super.getList();
  }

  public void action()
    throws MXException, RemoteException
  {
    super.action();
    String personid = getMboValue().getAttributeName();
    MboSetRemote msf = getMboValue().getMbo().getMboSet("$person", "person", "personid='" + personid + "'");
    String departmentnum = msf.getMbo(0).getString("DEPARTMENT");
    getMboValue("RTP_DEPT").setValue(departmentnum, 11L);
  }
}
