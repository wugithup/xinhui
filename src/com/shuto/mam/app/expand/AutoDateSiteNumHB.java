package com.shuto.mam.app.expand;
/**  
com.shuto.mam.app.expand.AutoDateSiteNumHB 华中
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年7月29日 下午4:03:04
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.util.Calendar;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.SqlFormat;
import psdi.server.MXServer;
import psdi.util.MXException;

public class AutoDateSiteNumHB
{
  private MboSetRemote msr = null;

  public AutoDateSiteNumHB(MboSetRemote paramMboSetRemote)
  {
    this.msr = paramMboSetRemote;
  }

  public int getNextAutoDateSiteNum(String paramString1, String paramString2, String paramString3, String paramString4, Date paramDate) throws RemoteException, MXException {
    int i = 1;
    Calendar localCalendar = Calendar.getInstance();
    localCalendar.setTime(paramDate);
    MboSetRemote localMboSetRemote = MXServer.getMXServer().getMboSet("AUTODATESITENUM", this.msr.getUserInfo());
    SqlFormat localSqlFormat = new SqlFormat(" SITEID=:1  and ORGID=:2 and OWNERTABLE=:3  and OWNERATTRIBUTENAME=:4 and YEAR=:5 and MONTH=:6");

    localSqlFormat.setObject(1, "AUTODATESITENUM", "SITEID", paramString1);
    localSqlFormat.setObject(2, "AUTODATESITENUM", "ORGID", paramString2);
    localSqlFormat.setObject(3, "AUTODATESITENUM", "OWNERTABLE", paramString3);
    localSqlFormat.setObject(4, "AUTODATESITENUM", "OWNERATTRIBUTENAME", paramString4);
    localSqlFormat.setInt(5, localCalendar.get(1));
    localSqlFormat.setInt(6, localCalendar.get(2) + 1);
    localMboSetRemote.setWhere(localSqlFormat.format());
    localMboSetRemote.reset();
    MboRemote localMboRemote;
    if (localMboSetRemote.count() == 0) {
      localMboRemote = localMboSetRemote.add();
      localMboRemote.setValue("SITEID", paramString1);
      localMboRemote.setValue("ORGID", paramString2);
      localMboRemote.setValue("OWNERTABLE", paramString3);
      localMboRemote.setValue("OWNERATTRIBUTENAME", paramString4);
      localMboRemote.setValue("YEAR", localCalendar.get(1));
      localMboRemote.setValue("MONTH", localCalendar.get(2) + 1);
      localMboRemote.setValue("NUM", i);
    } else {
      localMboRemote = localMboSetRemote.getMbo(0);
      i = localMboRemote.getInt("NUM");
      i++; localMboRemote.setValue("NUM", i);
    }
    localMboSetRemote.save();
    localMboSetRemote.close();
    return i;
  }
}