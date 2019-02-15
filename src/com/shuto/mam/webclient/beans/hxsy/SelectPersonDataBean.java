package com.shuto.mam.webclient.beans.hxsy;

import java.rmi.RemoteException;
import java.util.Vector;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:控股台账
 */
public class SelectPersonDataBean extends DataBean
{
  public MboSetRemote getMboSetRemote()
    throws RemoteException, MXException
  {
    super.getMboSetRemote();
    MboRemote localMboRemote = this.app.getAppBean().getMbo();
    MboSetRemote localMboSetRemote = localMboRemote.getMboSet("$person", "person", " (locationsite = '菏泽电厂' and post in ('1004', '1005') and  s_departmentsid in ('1001', '1002', '1003'))  or (personid in (select RESPPARTYGROUP  from PERSONGROUPTEAM where Persongroup = 'HZ_RCRP2023'))");

    return localMboSetRemote;
  }

  public int execute() throws MXException, RemoteException {
    super.execute();

    Vector localVector = getMboSet().getSelection();
    String str = this.app.getAppBean().getMbo().getString("tz_xhsno");

    for (int i = 0; i < localVector.size(); ++i) {
      MboRemote localMboRemote1 = (MboRemote)localVector.elementAt(i);

      MboSetRemote localMboSetRemote = this.app.getAppBean().getMbo().getMboSet("XHSCY");

      MboRemote localMboRemote2 = localMboSetRemote.addAtEnd();

      localMboRemote2.setValue("woby", localMboRemote1.getString("personid"), 11L);

      localMboRemote2.setValue("tz_xhsno", str, 11L);
      localMboRemote2.setValue("siteid", this.app.getAppBean().getMbo().getString("siteid"));
      localMboRemote2.setValue("orgid", this.app.getAppBean().getMbo().getString("orgid"));
    }

    this.app.getAppBean().reloadTable();
    this.app.getAppBean().refreshTable();

    return 1;
  }
}