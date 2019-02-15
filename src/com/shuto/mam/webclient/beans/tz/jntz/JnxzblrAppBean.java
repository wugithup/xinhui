package com.shuto.mam.webclient.beans.tz.jntz;

import java.rmi.RemoteException;

import com.shuto.mam.webclient.beans.base.CAppBean;

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
public class JnxzblrAppBean extends CAppBean
{
  public int XZB()
    throws MXException, RemoteException
  {
    MboRemote Mbo = getMbo();
    String applyid = Mbo.getString("JNXZBNUM");
    MboSetRemote jnzbwhs = Mbo.getMboSet("&JN_ZBWH", "JN_ZBWH", "type='小指标竞赛'");
    if (!jnzbwhs.isEmpty()) {
      MboRemote jnzbwh = jnzbwhs.getMbo(0);
      MboSetRemote jnzbwhlines = jnzbwh.getMboSet("JN_ZBWHLINE");
      jnzbwhlines.setOrderBy("SN");
      if (!jnzbwhlines.isEmpty()) {
        MboSetRemote jnxzblines = Mbo.getMboSet("JN_XZBLINE");
        if (jnxzblines.isEmpty()) {
          for (int i = 0; i < jnzbwhlines.count(); ++i) {
            MboRemote jnzbwhmbo = jnzbwhlines.getMbo(i);
            MboRemote jnxzbmbo = jnxzblines.add();
            jnxzbmbo.setValue("jnxzbnum", applyid, 11L);
            jnxzbmbo.setValue("sn", new Double(((MboSet)jnxzbmbo.getThisMboSet()).max("sn")).intValue() + 1, 11L);
            jnxzbmbo.setValue("zbmc", jnzbwhmbo.getString("zbmc"), 11L);
          }

          super.save();
        }
      }

    }

    return 1;
  }
}