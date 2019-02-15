package com.shuto.mam.app.hb.jx.sr;
/**  
com.shuto.mam.app.hb.jx.sr.FldSrprofess 河北
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年5月23日 上午10:59:24
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.io.PrintStream;
import java.rmi.RemoteException;
import psdi.mbo.ALNDomain;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.security.UserInfo;
import psdi.util.MXException;

public class FldSrprofess extends ALNDomain
{
  public FldSrprofess(MboValue mbovalue)
    throws MXException
  {
    super(mbovalue);
    setDomainId("SRPROFESS");
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    MboSetRemote Set = super.getList();
    String Where = "";
    MboRemote Mbo = getMboValue().getMbo();
    if (Mbo != null) {
      String siteid = Mbo.getUserInfo().getInsertSite();
      if (!"HB".equals(siteid)) {
        Where = " and description like '%" + siteid + "%'";
      }
    }
    String SetWhere = Set.getRelationship();
    Set.setRelationship(SetWhere + Where);
    System.out.println(SetWhere + Where);
    return Set;
  }
}
