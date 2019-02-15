package com.shuto.mam.webclient.beans.om.skill;

import java.rmi.RemoteException;

import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.workflow.DirectorInput;
import psdi.workflow.WorkflowDirector;
/**  
com.shuto.mam.webclient.beans.tz.om.skill.SkillAppBean 华东大区 阜阳项目 台账
* @author       lzq liuzq@shuoto.cn
* @date         2017-4-18 上午10:24:01
* @Copyright:   2017 Shuto版权所有
* @version      V1.0  
*/
public class SkillAppBean extends AppBean
{
  public int INSERT()
    throws MXException, RemoteException
  {
    super.INSERT();
    String appName = getMbo().getThisMboSet().getApp();
    getMbo().setValue("APP", appName, 11L);
    return 1;
  }

  public int SAVE() throws MXException, RemoteException {
    return super.SAVE();
  }

  public int ROUTEWF() throws MXException, RemoteException {
    WorkflowDirector director = this.clientSession.getWorkflowDirector();
    director.preventAutoInit();
    SAVE();
    director.allowAutoInit();
    director.setProcessName(getMbo().getThisMboSet().getApp());
    director.startInput(this.clientSession.getCurrentApp().getId(), getMbo(), DirectorInput.workflow);
    getWorkflowDirections(director);
    return 1;
  }
}