package com.shuto.mam.app.oplog.mfhy;

import java.rmi.RemoteException;

import psdi.mbo.Mbo;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
/**
 * 煤粉细度化验报告
 com.shuto.mam.app.oplog.mfhy.MFHY 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月5日 下午3:55:06
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class MFHY extends Mbo
  implements MFHYRemote
{
  public MFHY(MboSet ms)
    throws RemoteException
  {
    super(ms);
  }

  protected void save()
    throws MXException, RemoteException
  {
    if (isNew()) {
      MboSetRemote MFHYSET = getMboSet("MFHY");
      MboSetRemote TLHYSET = getMboSet("TLHY");

      MboSetRemote ST_MFHYZB = getMboSet("ST_MFHYZB");
      String APP = getString("APP");
      String SID = getString("SID");
      String JIZHU = getString("JIZHU");
      int i;
      if ((APP.equals("MFHY")) && 
        (MFHYSET.count() > 0)) {
        for (i = 0; i < MFHYSET.count(); ++i) {
          MboRemote mbo = ST_MFHYZB.add();
          mbo.setValue("ZDNAME", MFHYSET.getMbo(i).getString("NAME"), 11L);
          mbo.setValue("BIAOZHUN", MFHYSET.getMbo(i).getString("BIAOZHUN"), 11L);
          mbo.setValue("PXH", MFHYSET.getMbo(i).getString("PXH"), 11L);
          mbo.setValue("JIZHU", JIZHU, 11L);
          mbo.setValue("ZBSID", SID, 11L);
        }

      }

      if ((APP.equals("TLHY")) && 
        (TLHYSET.count() > 0)) {
        for (i = 0; i < TLHYSET.count(); ++i) {
          MboRemote mbo2 = ST_MFHYZB.add();
          mbo2.setValue("ZDNAME", TLHYSET.getMbo(i).getString("NAME"), 11L);
          mbo2.setValue("BIAOZHUN", TLHYSET.getMbo(i).getString("BIAOZHUN"), 11L);
          mbo2.setValue("PXH", TLHYSET.getMbo(i).getString("PXH"), 11L);
          mbo2.setValue("JIZHU", JIZHU, 11L);
          mbo2.setValue("ZBSID", SID, 11L);
        }
      }
    }

    super.save();
  }
}