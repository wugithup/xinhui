package com.shuto.mam.app.expand;

import java.rmi.RemoteException;
import java.util.HashMap;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.MXException;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年4月21日
 * @since:控股台账
 */
public class SiteLinkShort
{
  private static HashMap<String, String> sls = null;
  MboSetRemote msr = null;

  public SiteLinkShort(MboSetRemote paramMboSetRemote) {
    this.msr = paramMboSetRemote;
  }

  public String getSiteEnShort(String paramString)
    throws RemoteException, MXException
  {
    if (sls == null) {
      initSiteLinkShort();
    }
    return (String)sls.get(paramString);
  }

  public void initSiteLinkShort()
    throws RemoteException, MXException
  {
    sls = new HashMap();
    MboSetRemote localMboSetRemote = MXServer.getMXServer().getMboSet("ALNDOMAIN", this.msr.getUserInfo());
    SqlFormat localSqlFormat = new SqlFormat("domainid=:1");
    localSqlFormat.setObject(1, "ALNDOMAIN", "DOMAINID", "SITELINKSHORT");
    localMboSetRemote.setWhere(localSqlFormat.format());
    for (int i = 0; i < localMboSetRemote.count(); ++i) {
      MboRemote localMboRemote = localMboSetRemote.getMbo(i);
      String str1 = localMboRemote.getString("VALUE");
      String str2 = localMboRemote.getString("DESCRIPTION");
      sls.put(str1, str2);
    }
    localMboSetRemote.close();
  }
}