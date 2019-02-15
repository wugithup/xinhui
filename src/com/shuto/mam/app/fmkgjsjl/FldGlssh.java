package com.shuto.mam.app.fmkgjsjl;
/**  
com.shuto.mam.app.fmkgjsjl.FldGlssh 河北
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月23日 上午10:55:37
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MAXTableDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

public class FldGlssh extends MAXTableDomain
{
  public FldGlssh(MboValue mbv)
  {
    super(mbv);
    setRelationship("st_ysx", "");
    setLookupKeyMapInOrder(new String[] { "gls" }, new String[] { "xh" });
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    MboRemote mbo = getMboValue().getMbo();
    setListCriteria("1=1");
    return super.getList();
  }
  public void action() throws MXException, RemoteException {
    super.action();
  }
}