package com.shuto.mam.app.operation.opticket;
/**  
com.shuto.mam.app.operation.opticket.FldPerson 江苏
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月22日 上午10:55:30
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.app.person.FldPersonID;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldPerson extends FldPersonID
{
  public FldPerson(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);

    setRelationship("person", "1=1");
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    MboRemote mbo = getMboValue().getMbo();
    String siteid = "";
    siteid = mbo.getString("siteid");
    setListCriteria("locationsite ='" + siteid + "'");
    return super.getList();
  }
}
