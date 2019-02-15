package com.shuto.mam.webclient.beans.workplan;

import java.rmi.RemoteException;
import psdi.mbo.MboSetRemote;
import psdi.util.MXApplicationException;
import psdi.util.MXException;
import psdi.webclient.system.beans.DataBean;
/**
 * 
 * @author: lgw
 * @date 创建时间:2017年3月28日
 * @since:阜阳台账
 */
public class WorkPlanlineChangeStatusBean extends DataBean
{
  public int execute()
    throws RemoteException, MXException
  {
    String status = getDefaultValue("STATUS");
    if (status.equals("")) {
      throw new MXApplicationException("workplan", "ChangeStatus001");
    }
    getMbo().setValue("status", status);

    MboSetRemote parents = getMbo().getMboSet("PARENTWROKPLANLINE");
    if (parents.count() > 0) {
      parents.getMbo(0).setFlag(7L, false);
      parents.getMbo(0).setValue("status", status);
    }
    return super.execute();
  }
}