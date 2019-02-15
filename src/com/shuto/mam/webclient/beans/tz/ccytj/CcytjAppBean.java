package com.shuto.mam.webclient.beans.tz.ccytj;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class CcytjAppBean extends CAppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    MboRemote mainMbo = this.app.getAppBean().getMbo();

    Date rbDate = mainMbo.getDate("RBSJ");

    if (rbDate != null)
    {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

      String rbDateStr = sdf.format(rbDate);

      MboSetRemote set = mainMbo.getMboSet("$REPORTSDATA", "REPORTSDATA", " to_char(MKDATE,'yyyy-mm-dd') = '" + rbDateStr + "' and  REPORTSID=25 ");

      if (!set.isEmpty()) {
        MboRemote reportMbo = set.getMbo(0);

        MboSetRemote reportListData = reportMbo
          .getMboSet("REPORTSZBDATA");

        reportListData.setWhere("reportszbid  in  (90,98 ,100,92) ");
        reportListData.setOrderBy(" reportszbid asc");

        double txdl = 0.0D;

        double txdl2 = 0.0D;

        if (!reportListData.isEmpty()) {
          for (int x = 0; x < reportListData.count(); ++x) {
            MboRemote dataMbo = reportListData.getMbo(x);
            int reportszbid = dataMbo.getInt("reportszbid");

            double value = dataMbo.getDouble("value");
            switch (reportszbid)
            {
            case 90:
              mainMbo.setValue("dl1", value, 2L);
              break;
            case 92:
              mainMbo.setValue("SWDL1", value, 2L);

              txdl = value - mainMbo.getDouble("BHGDL2");
              break;
            case 98:
              mainMbo.setValue("dl2", value, 2L);
              break;
            case 100:
              mainMbo.setValue("SWDL2", value, 2L);

              txdl2 = value - mainMbo.getDouble("BHGDL1");
            }

          }

        }

        mainMbo.setValue("TXDL1", txdl, 11L);

        mainMbo.setValue("TXDL2", txdl2, 11L);
      }
      else {
        throw new MXApplicationException("提示 ", " 所选日期没有找到相关的电量日报表！");
      }

      set.close();
    }

    return super.SAVE();
  }
}