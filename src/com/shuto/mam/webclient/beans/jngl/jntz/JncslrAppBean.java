package com.shuto.mam.webclient.beans.jngl.jntz;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSet;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**  
com.shuto.mam.webclient.beans.jngl.jntz.JncslrAppBean 华东大区 阜阳项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-14 下午3:22:58
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class JncslrAppBean extends AppBean
{
  public int CSDB()
    throws MXException, RemoteException
  {
    MboRemote Mbo = getMbo();
    String applyid = Mbo.getString("JNCSDBNUM");
    MboSetRemote jnzbwhs = Mbo.getMboSet("&JN_ZBWH", "JN_ZBWH", "type='对标参数'");
    if (!jnzbwhs.isEmpty()) {
      MboRemote jnzbwh = jnzbwhs.getMbo(0);
      MboSetRemote jnzbwhlines = jnzbwh.getMboSet("JN_ZBWHLINE");
      jnzbwhlines.setOrderBy("SN");
      if (!jnzbwhlines.isEmpty()) {
        MboSetRemote jncsdblines = Mbo.getMboSet("JN_CSDBLINE");
        if (jncsdblines.isEmpty()) {
          for (int i = 0; i < jnzbwhlines.count(); ++i) {
            MboRemote jnzbwhmbo = jnzbwhlines.getMbo(i);
            MboRemote jncsdbmbo = jncsdblines.add();
            jncsdbmbo.setValue("jncsdbnum", applyid, 11L);
            jncsdbmbo.setValue("sn", new Double(((MboSet)jncsdbmbo.getThisMboSet()).max("sn")).intValue() + 1, 11L);
            jncsdbmbo.setValue("zbmc", jnzbwhmbo.getString("zbmc"), 11L);
            jncsdbmbo.setValue("dw", jnzbwhmbo.getString("dw"), 11L);
          }
          super.save();
        }
      }

    }

    return 1;
  }
}