package com.shuto.mam.webclient.beans.tz.oplog;
/**  
com.shuto.mam.webclient.beans.tz.oplog.TxytjAppBean 阜阳
TZ_TXYTJ 
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年6月12日 下午3:51:05
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.io.PrintStream;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class TxytjAppBean extends CAppBean
{
  public int SAVE()
    throws MXException, RemoteException
  {
    MboRemote Mbo = this.app.getAppBean().getMbo();

    MboRemote mainMbo = this.app.getAppBean().getMbo();

    Date rbDate = mainMbo.getDate("LRSJ");

    if (rbDate != null)
    {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

      String rbDateStr = sdf.format(rbDate);

      MboSetRemote set = mainMbo.getMboSet("$REPORTSDATA", "REPORTSDATA", 
        " to_char(MKDATE,'yyyy-mm-dd') = '" + rbDateStr + 
        "' and  REPORTSID=25 ");

      if (!set.isEmpty()) {
        MboRemote reportMbo = set.getMbo(0);

        MboSetRemote reportListData = reportMbo
          .getMboSet("REPORTSZBDATA");

        reportListData.setWhere("reportszbid  in  (90,98 ,100,92) ");
        reportListData.setOrderBy(" reportszbid asc");

        double txdl = 0.0D;

        double txdl2 = 0.0D;

        if (!reportListData.isEmpty()) {
          for (int x = 0; x < reportListData.count(); x++) {
            MboRemote dataMbo = reportListData.getMbo(x);
            int reportszbid = dataMbo.getInt("reportszbid");

            double value = dataMbo.getDouble("value");
            switch (reportszbid)
            {
            case 90:
              mainMbo.setValue("ZDL", value, 2L);
              break;
            case 92:
              mainMbo.setValue("SWDL1", value, 2L);

              txdl = value - mainMbo.getDouble("BHGDL1");
              break;
            case 98:
              mainMbo.setValue("ZDL1", value, 2L);
              break;
            case 100:
              mainMbo.setValue("SWDL2", value, 2L);

              txdl2 = value - mainMbo.getDouble("BHGDL2");
            }

          }

        }

        mainMbo.setValue("TXDL", txdl, 11L);

        mainMbo.setValue("TXDL1", txdl2, 11L);
      }
      else {
        throw new MXApplicationException("提示 ", " 所选日期没有找到相关的电量日报表！");
      }

      set.close();
    }

    double yaxh = Mbo.getDouble("YAXH");
    double dcs = Mbo.getDouble("DCS");
    double dcs1 = Mbo.getDouble("DCS1");
    double zdl = Mbo.getDouble("ZDL");
    double zdl1 = Mbo.getDouble("ZDL1");

    double BHGDL1 = Mbo.getDouble("BHGDL1");
    double BHGDL2 = Mbo.getDouble("BHGDL2");

    if (((dcs == 0.0D) && (dcs1 == 0.0D)) || (zdl == 0.0D))
      Mbo.setValue("YADH", 0, 2L);
    else {
      Mbo.setValue("YADH", yaxh * dcs / (dcs + dcs1) / zdl * 1000.0D, 2L);
    }

    if (((dcs == 0.0D) && (dcs1 == 0.0D)) || (zdl1 == 0.0D))
      Mbo.setValue("YADH1", 0, 2L);
    else {
      Mbo.setValue("YADH1", yaxh * dcs / (dcs + dcs1) / zdl1 * 1000.0D, 2L);
    }

    if (zdl + zdl1 - BHGDL1 - BHGDL2 == 0.0D) {
      System.out.println("(zdl + zdl1 - BHGDL1 - BHGDL2) == 0");
      Mbo.setValue("YADH3", 0, 2L);
    } else {
      System.out.println(yaxh * 10000.0D / (zdl + zdl1 - BHGDL1 - BHGDL2) + 
        "   999999");
      Mbo.setValue("YADH3", 
        yaxh * 10000.0D / (zdl + zdl1 - BHGDL1 - BHGDL2), 2L);
    }
    return super.SAVE();
  }

  public void TJ() throws RemoteException, MXException {
    getMbo().setValue("STATUS", "已关闭", 11L);
    super.SAVE();
  }
}
