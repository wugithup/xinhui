package com.shuto.mam.webclient.beans.tz.kgzcgl.gdzc;

import java.rmi.RemoteException;
import java.text.DecimalFormat;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class GdzcAppBean extends AppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    return super.SAVE();
  }

  public synchronized void save()
    throws MXException
  {
    try
    {
      super.save();

      MboRemote localMboRemote1 = this.app.getAppBean().getMbo();
      MboSetRemote localMboSetRemote = localMboRemote1.getMboSet("NEWSTATUS");
      if (!localMboSetRemote.isEmpty()) {
        MboRemote localMboRemote2 = localMboSetRemote.getMbo(0);
        localMboRemote1.setValue("LOCATIONNUM", localMboRemote2.getString("LOCATIONNUM"), 11L);
        localMboRemote1.setValue("STATUS", localMboRemote2.getString("STATUS"), 11L);
        localMboRemote1.setValue("S_DEPARTMENTSID", localMboRemote2.getString("S_DEPARTMENTSID"), 11L);
        localMboRemote1.setValue("USEPERSON", localMboRemote2.getString("USEPERSON"), 11L);
        for (int i = 0; i < localMboSetRemote.count(); ++i)
          localMboSetRemote.getMbo(i).setValue("isnew", "0");
      }
    }
    catch (RemoteException localRemoteException) {
      localRemoteException.printStackTrace();
    }

    super.save();
  }

  public int DUPLICATE()
    throws MXException, RemoteException
  {
    super.DUPLICATE();
    MboRemote localMboRemote = this.app.getAppBean().getMbo();
    String str1 = localMboRemote.getString("ASSETNUM");
    String str2 = "";

    String[] arrayOfString = str1.split("-");
    if (arrayOfString.length > 1)
    {
      String localObject1 = "";
      for (int i = 0; i < arrayOfString.length - 1; ++i) {
        localObject1 = (String)localObject1 + arrayOfString[i];
      }

      MboSetRemote localObject2 = localMboRemote.getMboSet("&ZCTZ", "GDZC", "assetnum like '" + (String)localObject1 + "%'");

      int j = ((MboSetRemote)localObject2).count() + 1;
      DecimalFormat localDecimalFormat = new DecimalFormat("000");
      String str3 = localDecimalFormat.format(j);

      str2 = (String)localObject1 + "-" + str3;

      if (localObject2 != null) {
        ((MboSetRemote)localObject2).close();
      }
    }

    localMboRemote.setValue("assetnum", str2);
    localMboRemote.setValue("status", "活动");
    Object localObject1 = localMboRemote.getMboSet("STATUSLIST");
    Object localObject2 = ((MboSetRemote)localObject1).add();
    ((MboRemote)localObject2).setValue("GDZCSTATUSNUM", ((MboSetRemote)localObject1).count());
    ((MboRemote)localObject2).setValue("status", "活动");
    ((MboRemote)localObject2).setValue("locationnum", localMboRemote.getString("locationnum"));
    ((MboRemote)localObject2).setValue("S_DEPARTMENTSID", localMboRemote.getString("S_DEPARTMENTSID"));
    ((MboRemote)localObject2).setValue("USEPERSON", localMboRemote.getString("USEPERSON"));
    ((MboRemote)localObject2).setValue("assetnum", localMboRemote.getString("assetnum"));
    ((MboRemote)localObject2).setValue("gdzcid", localMboRemote.getString("gdzcid").replaceAll(",", ""));

    this.app.getAppBean().reloadTable();
    return 1;
  }
}