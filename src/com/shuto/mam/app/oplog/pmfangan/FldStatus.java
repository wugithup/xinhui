package com.shuto.mam.app.oplog.pmfangan;

import java.rmi.RemoteException;

import java.util.Calendar;
import psdi.mbo.MboValue;
import psdi.mbo.MboValueAdapter;
import psdi.util.MXException;
/**
 * 混配煤方案
 com.shuto.mam.app.oplog.pmfangan.FldStatus 河北分公司
 * @author       liwc   liwc@shuoto.cn
 * @date         2017年5月4日 下午8:42:19
 * @Copyright:   2017 Shuto版权所有
 * @version      V1.0 
 */
public class FldStatus extends MboValueAdapter
{
  public FldStatus()
  {
  }

  public FldStatus(MboValue mbv)
  {
    super(mbv); }

  public void action() throws MXException, RemoteException {
    Calendar c = Calendar.getInstance();
    String usernm = getMboValue().getMbo().getUserInfo().getPersonId();
    String status = getMboValue("STATUS").getString();

    if (status.equals("TRUN"))
    {
      getMboValue("HQPERSON").setValue(usernm);
      getMboValue("HQDATE").setValue(c.getTime());
    }

    if (status.equals("已批准"))
    {
      getMboValue("PZPERSON").setValue(usernm);
      getMboValue("PZDATE").setValue(c.getTime());
    }

    super.action();
  }
}