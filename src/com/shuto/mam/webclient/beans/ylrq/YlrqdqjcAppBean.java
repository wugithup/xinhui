package com.shuto.mam.webclient.beans.ylrq;
/**  
com.shuto.mam.webclient.beans.ylrq.YlrqdqjcAppBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月23日 上午10:37:06
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;

import com.shuto.mam.webclient.beans.base.CAppBean;

public class YlrqdqjcAppBean extends CAppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();
    try
    {
      MboRemote mbo = this.app.getAppBean().getMbo();
      String mainnum = mbo.getString("ylrqdqjcnum");

      MboSetRemote ylrqdqjclineset = mbo.getMboSet("ylrqdqjcline");
      MboSetRemote ylrqwhset = mbo.getMboSet("$ylrqwh", "ylrqwh", "1=1");
      MboRemote ylrqdqjclineMbo = null;
      MboSetRemote mxSet = null;
      MboSetRemote ylrqwhlineset = null;
      String ylrqwhnum = null;

      if (!ylrqwhset.isEmpty()) {
        for (int a = 0; a < ylrqwhset.count(); a++) {
          ylrqdqjclineMbo = ylrqdqjclineset.add();

          ylrqwhnum = ylrqwhset.getMbo(a).getString("ylrqwhnum");
          ylrqdqjclineMbo.setValue("jcbw", ylrqwhset.getMbo(a).getString("jcbw"), 2L);

          ylrqdqjclineMbo.setValue("ylrqwhnum", ylrqwhnum, 2L);
          ylrqdqjclineMbo.setValue("ylrqdqjcnum", mainnum, 2L);
          mxSet = ylrqdqjclineMbo.getMboSet("ylrqdqjcmx");
          ylrqwhlineset = mbo.getMboSet("$ylrqwhline", "ylrqwhline", "ylrqwhnum='" + ylrqwhnum + "'");
          for (int i = 0; i < ylrqwhlineset.count(); i++) {
            MboRemote mxmbo = mxSet.add();
            mxmbo.setValue("jccontent", ylrqwhlineset.getMbo(i).getString("jccontent"), 2L);
            mxmbo.setValue("ylrqdqjclinenum", ylrqdqjclineMbo.getString("ylrqdqjclinenum"), 2L);
          }
        }
      }
      ylrqwhlineset.close();
      ylrqwhset.close();
    } catch (RemoteException e) {
      e.printStackTrace();
    }

    return 1;
  }
}
