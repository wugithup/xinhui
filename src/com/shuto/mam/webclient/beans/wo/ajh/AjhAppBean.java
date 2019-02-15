package com.shuto.mam.webclient.beans.wo.ajh;

import java.rmi.RemoteException;
import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.beans.DataBean;
import psdi.webclient.system.controller.AppInstance;

public class AjhAppBean extends AppBean
{
  public void CREATESR()
    throws RemoteException, MXException
  {
    MboRemote localMboRemote1 = this.app.getAppBean().getMbo();
    if (!localMboRemote1.isNull("TICKETID")) {
      throw new MXApplicationException("", "该安健环已经关联缺陷,不能再进行关联");
    }
    MboSetRemote localMboSetRemote = localMboRemote1.getMboSet("SR");
    localMboSetRemote.setApp("SR");
    MboRemote localMboRemote2 = localMboSetRemote.add();
    localMboRemote2.setValue("SOURCE", "缺陷", 2L);
    localMboRemote2.setValue("DESCRIPTION", localMboRemote1.getString("TIANNAME"), 2L);
    localMboRemote2.setValue("S_SRFXR", localMboRemote1.getString("ENTRY"), 2L);
    //localMboRemote2.setValue("S_AJHPERSON", localMboRemote1.getUserInfo().getPersonId(), 2L);
    if (!"其它".equals(localMboRemote1.getString("ZY"))) {
      localMboRemote2.setValue("s_pROFESSION", localMboRemote1.getString("ZY"), 2L);
    }

    setValue("TICKETID", localMboRemote2.getString("TICKETID"), 2L);
  }
}