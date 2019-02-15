package com.shuto.mam.webclient.beans.tz.operation.qjcz;

import com.ibm.icu.text.SimpleDateFormat;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class qjczDataBean extends DataBean
{
  public int addrow()
    throws MXException
  {
    super.addrow();
    try {
      MboRemote localMboRemote1 = this.app.getAppBean().getMbo();
      int i = localMboRemote1.getInt("year");
      MboSetRemote localMboSetRemote = localMboRemote1.getMboSet("TZ_QJCZMX");
      MboRemote localMboRemote2 = localMboSetRemote.getMbo();
      localMboRemote2.setValue("TZ_QJCZNUM", localMboRemote1.getString("TZ_QJCZNUM"));
      SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
      Object localObject;
      if (getMboSet().count() > 1) {
        localObject = getMbo(getMboSet().count() - 2);
        Date localDate = ((MboRemote)localObject).getDate("czdate");
        Calendar localCalendar = Calendar.getInstance();
        localCalendar.setTime(localDate);
        int j = localCalendar.get(1);
        int k = localCalendar.get(2) + 1;
        int l = localCalendar.get(5);
        if (l == 4) {
          localDate = localSimpleDateFormat.parse(j + "-" + k + "-14");
          localMboRemote2.setValue("czdate", localDate);
        }
        else if (l == 14) {
          localDate = localSimpleDateFormat.parse(j + "-" + k + "-24");
          localMboRemote2.setValue("czdate", localDate);
        }
        else if (l == 24) {
          ++k;
          localDate = localSimpleDateFormat.parse(j + "-" + k + "-4");
          localMboRemote2.setValue("czdate", localDate);
        }
      }
      else {
        localObject = localSimpleDateFormat.parse(i + "-1-4");
        localMboRemote2.setValue("czdate", (Date)localObject);
      }
    } catch (RemoteException localRemoteException) {
      localRemoteException.printStackTrace();
    }
    catch (ParseException localParseException) {
      localParseException.printStackTrace();
    }
    return 1;
  }
}