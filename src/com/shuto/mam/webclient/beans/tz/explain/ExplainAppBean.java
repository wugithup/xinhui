package com.shuto.mam.webclient.beans.tz.explain;

import java.rmi.RemoteException;
import psdi.util.MXException;
import psdi.webclient.system.beans.AppBean;
import psdi.workflow.DirectorInput;
import psdi.workflow.WorkflowDirector;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class ExplainAppBean extends AppBean
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
    director.setProcessName("EXPLAIN");
    director.startInput(this.clientSession.getCurrentApp().getId(), getMbo(), DirectorInput.workflow);
    getWorkflowDirections(director);
    return 1;
  }
}