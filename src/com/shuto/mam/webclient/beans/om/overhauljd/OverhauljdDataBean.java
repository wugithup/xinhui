package com.shuto.mam.webclient.beans.om.overhauljd;
/**  
com.shuto.mam.webclient.beans.om.overhauljd.OverhauljdDataBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月14日 上午10:32:46
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class OverhauljdDataBean extends DataBean
{
  public int addrow()
    throws MXException
  {
    try
    {
      MboRemote Mbo = this.app.getAppBean().getMbo();
      String overhauljdnum = Mbo.getString("TZ_OVERHAULJDNUM");
      String status = Mbo.getString("STATUS");
      if ("新建".equals(status)) {
        super.addrow();
        getMbo().setValue("OVERHAULJDNUM", overhauljdnum, 11L);
      }
    } catch (RemoteException e) {
      e.printStackTrace();
    }

    return 1;
  }

  public int toggledeleterow() throws MXException
  {
    try {
      MboRemote Mbo = this.app.getAppBean().getMbo();
      String status = Mbo.getString("STATUS");
      if ("新建".equals(status))
        super.toggledeleterow();
    }
    catch (RemoteException e) {
      e.printStackTrace();
    }
    return 1;
  }
}
