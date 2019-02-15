package com.shuto.mam.webclient.beans.hnht;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;

/**
* @author       lzq liuzq@shuoto.cn
* @Description  华南合同
* @date         2017-6-7 上午11:12:31
* @Copyright:   2017 Shuto版权所有
* @version      V1.0
 */
public class SelDepartmentDateBean extends DataBean
{
  protected MboSetRemote getMboSetRemote()
    throws MXException, RemoteException
  {
    MboRemote mainmbo = this.app.getAppBean().getMbo();
    String appname = mainmbo.getThisMboSet().getApp();

    String personid = getMXSession().getUserInfo().getPersonId();
    MboSetRemote pers = mainmbo.getMboSet("$person", "person", "personid='" + personid + "'");

    String where = "parent is not null and isexternal=0 and ispost=0 and iscrew=0 and siteid='" + 
      getMXSession().getUserInfo().getInsertSite() + "' " + 
      "and department<>'" + pers.getMbo(0).getString("topdepartment") + "' " + 
      "and department not in (select department from htappr where app='" + appname + "' and htnum='" + mainmbo.getString("htnum") + "')";

    MboSetRemote thisSet = super.getMboSetRemote();
    thisSet.setRelationship(where);
    return thisSet;
  }

  public int execute() throws MXException, RemoteException {
    super.execute();

    MboRemote mainMbo = this.app.getAppBean().getMbo();
    MboSetRemote hqSet = mainMbo.getMboSet("HTAPPR");

    MboSetRemote selectLines = getMboSet();

    MboRemote mbo = null;
    MboRemote hqMbo = null;

    selectLines.resetWithSelection();
    for (int i = 0; i < selectLines.count(); ++i) {
      mbo = selectLines.getMbo(i);
      hqMbo = hqSet.add(2L);
      hqMbo.setValue("htnum", mainMbo.getString("htnum"), 2L);
      hqMbo.setValue("DEPARTMENT", mbo.getString("DEPARTMENT"));
      hqMbo.setValue("siteid", mbo.getString("siteid"));
      hqMbo.setValue("app", mainMbo.getThisMboSet().getApp());
    }
    this.app.getAppBean().save();
    this.app.getAppBean().reloadTable();
    this.app.getAppBean().refreshTable();
    return 1;
  }
}