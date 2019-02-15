package com.shuto.mam.webclient.beans.asset.assetyd;

import java.rmi.RemoteException;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 逻辑（定值）申请           LJDZ
 com.shuto.mam.webclient.beans.asset.assetyd.SbydZyCfgDataBean 湖南
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年6月21日 下午11:13:07
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class SbydZyCfgDataBean extends DataBean
{
  protected MboSetRemote getMboSetRemote()
    throws MXException, RemoteException
  {
    DataBean databean = this.app.getDataBean();
    MboRemote mainMbo = this.app.getAppBean().getMbo();
    String ydnum = mainMbo.getString("ticketid");
    MboRemote Mbo = databean.getMbo(databean.getCurrentRow());

    MboSetRemote wos = Mbo.getMboSet(
      "$person", 
      "person", 
      "locationsite='" + Mbo.getString("siteid") + "' and status='活动' and   PERSONID NOT IN (SELECT PERSONID  FROM ydhqzy  WHERE  YDNUM = '" + ydnum + "') ");
    return wos;
  }

  public int execute() throws MXException, RemoteException
  {
    super.execute();
    MboRemote mainMbo = this.app.getAppBean().getMbo();

    MboSetRemote prowos = mainMbo.getMboSet("ydnum_ydhqzy");

    MboSetRemote selectLines = getMboSet();

    selectLines.resetWithSelection();

    MboRemote prowo = null;
    for (int i = 0; i < selectLines.count(); ++i) {
      MboRemote wombo = selectLines.getMbo(i);
      prowo = prowos.add();

      prowo.setValue("ydnum", mainMbo.getString("ticketid"), 11L);
      prowo.setValue("profession", wombo.getString("profession"), 11L);
      prowo.setValue("personid", wombo.getString("personid"), 11L);
    }
    prowos.save();
    this.app.getAppBean().save();
    return 1;
  }
}