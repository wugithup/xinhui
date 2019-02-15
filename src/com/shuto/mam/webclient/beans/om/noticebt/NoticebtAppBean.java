package com.shuto.mam.webclient.beans.om.noticebt;
/**  
com.shuto.mam.webclient.beans.om.noticebt.NoticebtAppBean 阜阳台账
 * @author       zhaowei  zhaowei@shuoto.cn
 * @date         2017年8月15日 上午11:16:08
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0  
 */
import java.rmi.RemoteException;
import java.util.Date;

import com.shuto.mam.webclient.beans.base.CAppBean;

import psdi.mbo.MboRemote;
import psdi.mbo.MboSetRemote;
import psdi.util.MXException;
import psdi.workflow.DirectorInput;
import psdi.workflow.WorkflowDirector;

public class NoticebtAppBean extends CAppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();
    getMbo().setValue("TYPE", "T");
    String str1 = getMbo().getUserInfo().getPersonId();
    MboSetRemote localMboSetRemote = getMbo().getMboSet("$person", "person", "personid = '" + str1 + "'");

    MboRemote localMboRemote = localMboSetRemote.getMbo(0);
    String str2 = localMboRemote.getString("DEPARTMENT");
    getMbo().setValue("DEPARTMENT", str2, 11L);
    return 1;
  }

  public int SAVE() throws MXException, RemoteException {
    super.SAVE();
    return 1;
  }

  public void ZF() throws RemoteException, MXException {
    getMbo().setValue("status", "作废", 11L);
    super.SAVE();
  }

  public int ROUTEWF() throws MXException, RemoteException
  {
    String str1 = getMbo().getUserInfo().getPersonId();
    String str2 = getMbo().getString("CREATEBY");
    String str3 = getMbo().getString("STATUS");
    String str4 = getMbo().getString("SY");

    if (("关闭".equals(str3)) && (!str1.equals(str2))) {
      return 1;
    }
    WorkflowDirector localWorkflowDirector = this.clientSession.getWorkflowDirector();
    localWorkflowDirector.preventAutoInit();
    SAVE();
    Date localDate = new Date();
    if ("新建".equals(str3)) {
      getMbo().setValue("SITUATION", getMbo().getString("SY"), 11L);
    }

    if (("关闭".equals(str3)) && (str1.equals(str2))) {
      getMbo().setValue("DATEXG", localDate, 11L);
    }

    if ("待接收".equals(str3)) {
      if ("Y".equals(str4))
        getMbo().setValue("SITUATION", "N", 11L);
      else
        getMbo().setValue("SITUATION", "Y", 11L);
    }
    if ("待接收".equals(str3)) {
      MboSetRemote localMboSetRemote = getMbo().getMboSet("NOTICEBILLPERSON");
      MboRemote localMboRemote = null;
      String str5 = "";
      if (!localMboSetRemote.isEmpty()) {
        for (int i = 0; i < localMboSetRemote.count(); i++) {
          localMboRemote = localMboSetRemote.getMbo(i);
          str5 = localMboRemote.getString("PERSONID");
          if (str5.equals(str1)) {
            if ("Y".equals(str4)) {
              localMboRemote.setValue("STATUS", "已接收", 11L);
              localMboRemote.setValue("TIME1", localDate, 11L);
            } else {
              localMboRemote.setValue("STATUS2", "已接收", 11L);
              localMboRemote.setValue("TIME2", localDate, 11L);
            }
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
