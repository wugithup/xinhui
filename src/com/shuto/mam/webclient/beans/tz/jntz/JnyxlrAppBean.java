package com.shuto.mam.webclient.beans.tz.jntz;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class JnyxlrAppBean extends AppBean
{
  public int YXZB()
    throws MXException, RemoteException
  {
    MboRemote Mbo = getMbo();
    String applyid = Mbo.getString("JNYXZBNUM");
    MboSetRemote jnzbwhs = Mbo.getMboSet("&JN_ZBWH", "JN_ZBWH", "type='运行指标'");
    if (!jnzbwhs.isEmpty()) {
      MboRemote jnzbwh = jnzbwhs.getMbo(0);
      MboSetRemote jnzbwhlines = jnzbwh.getMboSet("JN_ZBWHLINE");
      jnzbwhlines.setOrderBy("SN");
      if (!jnzbwhlines.isEmpty()) {
        MboSetRemote jnyxzblines = Mbo.getMboSet("JN_YXZBLINE");
        if (jnyxzblines.isEmpty()) {
          for (int i = 0; i < jnzbwhlines.count(); ++i) {
            MboRemote jnzbwhmbo = jnzbwhlines.getMbo(i);
            MboRemote jnyxzbmbo = jnyxzblines.add();
            jnyxzbmbo.setValue("jnyxzbnum", applyid, 11L);
            jnyxzbmbo.setValue("sn", new Double(((MboSet)jnyxzbmbo.getThisMboSet()).max("sn")).intValue() + 1, 11L);
            jnyxzbmbo.setValue("zbmc", jnzbwhmbo.getString("zbmc"), 11L);
            jnyxzbmbo.setValue("dw", jnzbwhmbo.getString("dw"), 11L);
          }

          super.save();
        }
      }

    }

    return 1;
  }
}