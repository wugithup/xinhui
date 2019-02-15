package com.shuto.mam.webclient.beans.jsjd;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;

/**  
com.shuto.mam.webclient.beans.jsjd.QjjsjdAppBean xx大区 XX项目
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-13 下午4:13:55
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class QjjsjdAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    int i = super.INSERT();
    MboRemote localMboRemote1 = getMbo();
    String str1 = localMboRemote1.getString("JSJDNUM");
    String str2 = localMboRemote1.getString("siteid");
    MboSetRemote localMboSetRemote1 = localMboRemote1.getMboSet("#JSJDLINE", "JSJDLINE", " ISTEMPLATE='1'  and  type ='高压电机巡检表' and  siteid='" + str2 + "'   ");

    localMboSetRemote1.setOrderBy("SN desc");
    int j = localMboSetRemote1.count();
    MboSetRemote localMboSetRemote2 = localMboRemote1.getMboSet("JSJDLINE12");
    if (!localMboSetRemote1.isEmpty())
    {
    	MboRemote localObject;
      for (int k = 0; k < j; ++k) {
        localObject = localMboSetRemote2.add();
        ((MboRemote)localObject).setValue("JSJDNUM", str1, 2L);
        ((MboRemote)localObject).setValue("sn", localMboSetRemote1.getMbo(k).getString("sn"), 2L);

        ((MboRemote)localObject).setValue("type", localMboSetRemote1.getMbo(k).getString("type"), 2L);

        ((MboRemote)localObject).setValue("aln01", localMboSetRemote1.getMbo(k).getString("aln01"), 2L);

        ((MboRemote)localObject).setValue("jizu", localMboSetRemote1.getMbo(k).getString("jizu"), 2L);

        ((MboRemote)localObject).setValue("kks", localMboSetRemote1.getMbo(k).getString("kks"), 2L);
      }

    }

    localMboSetRemote1.close();
    localMboSetRemote2.close();

    Object localObject = localMboRemote1.getMboSet("#JSJDLINE123", "JSJDLINE", " ISTEMPLATE='1'  and  type ='常低压电机检查记录' and  siteid='" + str2 + "'   ");

    ((MboSetRemote)localObject).setOrderBy("SN desc");
    int k = ((MboSetRemote)localObject).count();
    MboSetRemote localMboSetRemote3 = localMboRemote1.getMboSet("JSJDLINE13");
    if (!((MboSetRemote)localObject).isEmpty())
    {
      for (int l = 0; l < k; ++l) {
        MboRemote localMboRemote2 = localMboSetRemote3.add();
        localMboRemote2.setValue("JSJDNUM", str1, 2L);
        localMboRemote2.setValue("sn", ((MboSetRemote)localObject).getMbo(l).getString("sn"), 2L);

        localMboRemote2.setValue("type", ((MboSetRemote)localObject).getMbo(l).getString("type"), 2L);

        localMboRemote2.setValue("aln01", ((MboSetRemote)localObject).getMbo(l).getString("aln01"), 2L);

        localMboRemote2.setValue("jizu", ((MboSetRemote)localObject).getMbo(l).getString("jizu"), 2L);
      }

    }

    ((MboSetRemote)localObject).close();

    this.app.getAppBean().refreshTable();
    this.app.getAppBean().reloadTable();
    return i;
  }

  public int SAVE() throws MXException, RemoteException
  {
    return super.SAVE();
  }
}