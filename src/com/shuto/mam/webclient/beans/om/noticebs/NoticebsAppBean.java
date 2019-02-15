package com.shuto.mam.webclient.beans.om.noticebs;
/**  
com.shuto.mam.webclient.beans.om.noticebs.NoticebsAppBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月22日 下午4:44:22
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */

import java.rmi.RemoteException;
import java.util.Date;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.security.UserInfo;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.webclient.system.controller.AppInstance;
import psdi.webclient.system.session.WebClientSession;
import psdi.workflow.DirectorInput;
import psdi.workflow.WorkflowDirector;

public class NoticebsAppBean extends CAppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();
    getMbo().setValue("TYPE", "S");
    String str1 = getMbo().getUserInfo().getPersonId();
    MboSetRemote localMboSetRemote = getMbo().getMboSet("$person", "person", "personid = '" + str1 + "'");

    MboRemote localMboRemote = localMboSetRemote.getMbo(0);
    String str2 = localMboRemote.getString("DEPARTMENT");
    getMbo().setValue("DEPARTMENT", str2, 11L);
    return 1;
  }

  public void ZF() throws RemoteException, MXException {
    getMbo().setValue("status", "作废", 11L);
    super.SAVE();
  }

  public int SAVE() throws MXException, RemoteException
  {
    String str1 = getMbo().getString("JX");
    String str2 = getMbo().getString("AMOUNT");
    if (("".equals(str1)) && ("".equals(str2))) {
      throw new MXApplicationException("提示 ", " 绩效分与金额请至少填写一项！");
    }
    super.SAVE();
    return 1;
  }

  public int ROUTEWF() throws MXException, RemoteException {
    String str1 = getMbo().getUserInfo().getPersonId();
    String str2 = getMbo().getString("STATUS");
    Date localDate = new Date();

    WorkflowDirector localWorkflowDirector = this.clientSession.getWorkflowDirector();
    localWorkflowDirector.preventAutoInit();
    SAVE();

    if ("待接收".equals(str2)) {
      MboSetRemote localMboSetRemote = getMbo().getMboSet("NOTICEBILLPERSON");
      MboRemote localMboRemote = null;
      String str3 = "";
      if (!localMboSetRemote.isEmpty()) {
        for (int i = 0; i < localMboSetRemote.count(); i++) {
          localMboRemote = localMboSetRemote.getMbo(i);
          str3 = localMboRemote.getString("PERSONID");
          if (str3.equals(str1)) {
            localMboRemote.setValue("STATUS", "已接收", 11L);
            localMboRemote.setValue("TIME1", localDate, 11L);
          }
        }
      }
    }

    SAVE();

    localWorkflowDirector.allowAutoInit();
    localWorkflowDirector.setProcessName(getMbo().getThisMboSet().getApp());
    localWorkflowDirector.startInput(this.clientSession.getCurrentApp().getId(), getMbo(), DirectorInput.workflow);

    getWorkflowDirections(localWorkflowDirector);
    return 1;
  }
}
