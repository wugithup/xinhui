package com.shuto.mam.webclient.beans.jsjd;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**  
com.shuto.mam.webclient.beans.jsjd.GlMmjAppBean xx大区 XX项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-13 下午4:12:04
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class GlMmjAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();
    MboRemote localMboRemote1 = getMbo();
    String str1 = localMboRemote1.getString("JSJDNUM");
    String str2 = localMboRemote1.getString("siteid");
    MboSetRemote localMboSetRemote1 = localMboRemote1.getMboSet("#JSJDLINE", "JSJDLINE", " ISTEMPLATE='1'  and  type ='锅炉磨煤机振动记录表' and  siteid='" + str2 + "'   ");

    localMboSetRemote1.setOrderBy("SN desc");
    int j = localMboSetRemote1.count();
    MboSetRemote localMboSetRemote2 = localMboRemote1.getMboSet("JSJDLINE20");
    if (!localMboSetRemote1.isEmpty())
    {
      for (int k = 0; k < j; ++k) {
        MboRemote localMboRemote2 = localMboSetRemote2.add();
        localMboRemote2.setValue("JSJDNUM", str1, 2L);
        localMboRemote2.setValue("sn", localMboSetRemote1.getMbo(k).getString("sn"), 2L);

        localMboRemote2.setValue("type", localMboSetRemote1.getMbo(k).getString("type"), 2L);

        localMboRemote2.setValue("aln01", localMboSetRemote1.getMbo(k).getString("aln01"), 2L);

        localMboRemote2.setValue("momeiji", localMboSetRemote1.getMbo(k).getString("momeiji"), 2L);

        localMboRemote2.setValue("kks", localMboSetRemote1.getMbo(k).getString("kks"), 2L);
      }

    }

    localMboSetRemote1.close();
    localMboSetRemote2.close();

    this.app.getAppBean().refreshTable();
    this.app.getAppBean().reloadTable();
    return i;
  }

  public int SAVE() throws MXException, RemoteException
  {
    return super.SAVE();
  }
}