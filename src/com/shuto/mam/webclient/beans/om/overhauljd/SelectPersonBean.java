package com.shuto.mam.webclient.beans.om.overhauljd;
/**  
com.shuto.mam.webclient.beans.om.overhauljd.SelectPersonBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月14日 上午10:40:17
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.util.Vector;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.mbo.NonPersistentMboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.session.WebClientSession;

public class SelectPersonBean extends DataBean
{
  public MboSetRemote getMboSetRemote()
    throws MXException, RemoteException
  {
    MboRemote Mbo = this.app.getAppBean().getMbo();
    String siteid=Mbo.getString("siteid");
    MboSetRemote personSet = Mbo.getMboSet("$person", "PERSON", "status = '活动' and locationsite='"+siteid+"' and department is not null");
    return personSet;
  }
  public int execute() throws RemoteException, MXException {
    if (this.mboSetRemote != null)
      if ((this.mboSetRemote instanceof NonPersistentMboSetRemote)) {
        ((NonPersistentMboSetRemote)this.mboSetRemote).execute();
        this.clientSession.addMXWarnings(this.mboSetRemote.getWarnings());
        if (this.parent != null)
          return this.parent.execute();
        this.app.getAppBean().save();
      }
      else if ((this.parent != null) && (this.parentRelationship != null)) {
        return this.parent.execute();
      }
    Vector personLineSet = getMboSet().getSelection();
    MboRemote personLineMbo = null;
    String personid = "";
    MboRemote Mbo = this.app.getAppBean().getMbo();
    String overhauljdnum = Mbo.getString("TZ_OVERHAULJDNUM");
    MboSetRemote overhauljdpersonSet = Mbo.getMboSet("TZ_OVERHAULJDPERSON");
    MboRemote overhauljdpersonMbo = null;
    for (int i = 0; i < personLineSet.size(); i++) {
      personLineMbo = (MboRemote)personLineSet.elementAt(i);
      personid = personLineMbo.getString("PERSONID");
      overhauljdpersonMbo = overhauljdpersonSet.add();
      overhauljdpersonMbo.setValue("OVERHAULJDNUM", overhauljdnum);
      overhauljdpersonMbo.setValue("PERSONID", personid);
    }
    this.app.getAppBean().reloadTable();
    return 1;
  }
}
