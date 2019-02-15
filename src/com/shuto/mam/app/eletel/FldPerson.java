package com.shuto.mam.app.eletel;
/**  
com.shuto.mam.app.eletel.FldPerson 江苏
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月24日 下午5:33:48
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPerson extends MAXTableDomain
{
  public FldPerson(MboValue paramMboValue)
  {
    super(paramMboValue);

    String str = getMboValue().getName();

    setRelationship("person", "1=1");

    String[] arrayOfString1 = { str };

    String[] arrayOfString2 = { "personid" };

    setLookupKeyMapInOrder(arrayOfString1, arrayOfString2);
  }

  public MboSetRemote getList() throws MXException, RemoteException
  {
    String str = getMboValue().getMbo().getString("siteid");
    setListCriteria("department='发电部' and locationsite='" + str + "'");
    return super.getList();
  }
}
