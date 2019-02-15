package com.shuto.mam.webclient.beans.asset;

import java.rmi.RemoteException;
import java.util.Vector;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 
com.shuto.mam.webclient.beans.asset.SelecthqpersonDataBean 河北分公司（曹妃甸）
* @author       songdd  songdd@shuoto.cn
* @date         2017年5月5日 下午3:19:06
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class SelecthqpersonDataBean extends DataBean
{
  MboRemote currMbo = null;
  MboSetRemote lines = null;
  MboRemote currLine = null;

  public MboSetRemote getMboSetRemote() throws RemoteException, MXException {
    MboSetRemote retpersons = super.getMboSetRemote();

    retpersons.setWhere("PERSONGROUP = 'ASSPROD_HQ'");
    retpersons.reset();
    return retpersons;
  }

  public int execute() throws MXException, RemoteException {
    super.execute();

    Vector selecteLines = getMboSet().getSelection();
    String ASSPRONUM = this.app.getAppBean().getMbo().getString("TZ_ASSPRONUM");
    for (int i = 0; i < selecteLines.size(); ++i) {
      MboRemote PERSONGROUPTEAM = (MboRemote)selecteLines.elementAt(i);

      MboSetRemote COMPAYZBSetRemote = this.app.getAppBean().getMbo().getMboSet("ASSPROZB");

      MboRemote COMPAYZBRemote = COMPAYZBSetRemote.addAtEnd();

      COMPAYZBRemote.setValue("personid", PERSONGROUPTEAM.getString("RESPPARTYGROUP"), 11L);
      COMPAYZBRemote.setValue("department", PERSONGROUPTEAM.getString("RESPPARTY_PERSONS.DEPARTMENT"), 11L);
      COMPAYZBRemote.setValue("ASSPRONUM", ASSPRONUM, 11L);
    }
    this.app.getAppBean().reloadTable();
    this.app.getAppBean().refreshTable();

    return 1;
  }
}