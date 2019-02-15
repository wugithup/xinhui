package com.shuto.mam.app.asset.assettfy;

import java.rmi.RemoteException;

import psdi.app.location.FldLocation;
import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.MboValue;
import psdi.util.MXException;

/**  
com.shuto.mam.app.asset.assettfy.FldBHTTLocation1 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年9月12日 下午6:08:56
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
public class FldBHTTLocation1 extends FldLocation
{
  public FldBHTTLocation1(MboValue paramMboValue)
    throws MXException, RemoteException
  {
    super(paramMboValue);
  }

  public void action()
    throws MXException, RemoteException
  {
    super.action();

    String str = getMboValue().getString();

    Mbo localMbo = getMboValue().getMbo();
    
    String siteid=localMbo.getString("siteid");
    MboSetRemote localMboSetRemote1 = localMbo.getMboSet("$locations", "locations", "location = '" + str + "' and siteid='"+siteid+"'");

    MboRemote localMboRemote1 = localMboSetRemote1.getMbo(0);

    MboSetRemote localMboSetRemote2 = localMboRemote1.getMboSet("asset");

    if (!localMboSetRemote2.isEmpty()) {
      MboRemote localMboRemote2 = localMboSetRemote2.getMbo(0);
      localMbo.setValue("assetnum", localMboRemote2.getString("assetnum"));
    }

    localMboSetRemote1.close();
  }

  public MboSetRemote getList() throws MXException, RemoteException {
    Mbo localMbo = getMboValue().getMbo();
    String siteid=localMbo.getString("siteid");
//    String str = localMbo.getString("OPAMDOPKGTYPE");
//    if (str.equals("投入")) {
    	setListCriteria("TYPE in ('操作中','操作') and siteid = '" + siteid + "'");
//    }
    return super.getList();
  }
}