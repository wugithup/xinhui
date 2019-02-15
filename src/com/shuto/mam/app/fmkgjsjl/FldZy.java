package com.shuto.mam.app.fmkgjsjl;
/**  
com.shuto.mam.app.fmkgjsjl.FldZy 河北
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月23日 上午11:02:07
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldZy extends MAXTableDomain
{
  public FldZy(MboValue mbv)
  {
    super(mbv);
    setRelationship("ALNDOMAIN", "");
    setLookupKeyMapInOrder(new String[] { "zy" }, 
      new String[] { "value" });
  }

  public void action() throws MXException, RemoteException {
    super.action();
  }

  public void init() throws MXException, RemoteException {
    super.init();
  }

  public void validate() throws MXException, RemoteException {
    super.validate();
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    MboRemote cmbo = getMboValue().getMbo();
    setListCriteria("domainid='CLASSTEAM'");
    return super.getList();
  }
}
