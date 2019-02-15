package com.shuto.mam.app.recycling;
/**  
com.shuto.mam.app.recycling.FldPerson 华南
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月10日 下午12:02:11
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.security.UserInfo;
import psdi.util.MXException;

public class FldPerson extends MAXTableDomain
{
  public FldPerson(MboValue mbovalue)
    throws MXException, RemoteException
  {
    super(mbovalue);
    setRelationship("person", "");
    String[] strFrom = { "personid" };

    String[] strTo = { getMboValue().getAttributeName() };
    setLookupKeyMapInOrder(strTo, strFrom);
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    MboRemote mbo = getMboValue().getMbo();
    String siteid=mbo.getString("siteid");
    setListCriteria(" locationsite = '" + siteid + "' and status='活动'");
    return super.getList();
  }
}