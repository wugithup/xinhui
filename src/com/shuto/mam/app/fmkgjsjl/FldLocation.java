package com.shuto.mam.app.fmkgjsjl;
/**  
com.shuto.mam.app.fmkgjsjl.FldLocation 河北
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月23日 上午10:57:25
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldLocation extends MAXTableDomain
{
  public FldLocation(MboValue mbv)
  {
    super(mbv);
    setRelationship("LOCATIONS", "");
    setLookupKeyMapInOrder(new String[] { "location" }, new String[] { "location" });
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    setListCriteria("1=1");
    return super.getList();
  }
  public void action() throws MXException, RemoteException {
    super.action();
  }
}
